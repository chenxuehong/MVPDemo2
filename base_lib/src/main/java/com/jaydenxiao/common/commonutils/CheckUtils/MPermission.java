package com.jaydenxiao.common.commonutils.CheckUtils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 13198 on 2018/7/9.
 * 在Android M 版本下的动态态权限的申请封装
 */
/*
    使用例子：
         // 申请的权限
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("sp", MODE_PRIVATE);
        findViewById(R.id.btn_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int size = sp.getAll().size();
                if (size < permissions.length) {
                    MPermission.with(MainActivity.this)
                            .setPermission(permissions)
                            .requestPermission();
                }
            }
        });


    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @MPermission.PermissionOK
    public void valdateSuccess() {
        Log.i("tag", "权限通过");
        for (int i = 0, j = permissions.length; i < j; i++) {

            sp.edit().putString("" + i, permissions[i]).apply();
        }
    }

    @MPermission.PermissionFail
    public void valdateFail() {
        Log.i("tag", "没有相应权限");
    }
 */
public class MPermission {
    private static final int PERMISSION_CODE = 0x100;
    private Object obj;
    private static Method methodOK, methodFail;
    private String[] permissions;//权限数组

    private MPermission(Object obj) {
        this.obj = obj;
        methodOK = null;
        methodFail = null;
        findMethod(obj);
    }

    private void findMethod(Object o) {
        Method[] methods = o.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(PermissionOK.class)) {
                methodOK = method;
                if (methodFail != null) {
                    break;
                }
            } else if (method.isAnnotationPresent(PermissionFail.class)) {
                methodFail = method;
                if (methodOK != null) {
                    break;
                }
            }
        }
    }

    public static MPermission with(Object obj) {
        return new MPermission(obj);
    }

    public MPermission setPermission(String... permissions) {
        if (permissions != null && permissions.length == 0) {
            throw new RuntimeException("必须填写需要申请的权限");
        }
        this.permissions = permissions;
        return this;
    }

    public void requestPermission() {

        int index = hasPermission(permissions);
        if (index != -1) {
            //当检查时发现系统不存在这个权限的时候，需要判断当前系统版本是否>=23
            if (Build.VERSION.SDK_INT >= 23) {

                requestPermissionApi23();
            } else {
                //此处模仿官方API中的方法 进行回调
                //API23一下的版本直接返回失败
                int[] grantResults = new int[permissions.length];
                for (int i = 0; i < grantResults.length; i++) {
                    if (i < index) {
                        grantResults[i] = PackageManager.PERMISSION_GRANTED;
                    } else {
                        grantResults[i] = PackageManager.PERMISSION_DENIED;
                    }
                }
                requestPermissionApi(grantResults);
            }
        } else {
            if (methodOK != null) {
                try {
                    methodOK.setAccessible(true);
                    methodOK.invoke(obj, new Object[]{});
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissionApi23() {

        new AlertDialog.Builder(getContext())
                .setTitle("title")
                .setMessage("msg")
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (obj instanceof Activity) {
                            ((Activity) obj).requestPermissions(permissions, PERMISSION_CODE);
                        } else if (obj instanceof Fragment) {
                            ((Fragment) obj).requestPermissions(permissions, PERMISSION_CODE);
                        }
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }

    private void requestPermissionApi(int[] grantResults) {

        if (obj instanceof ActivityCompat.OnRequestPermissionsResultCallback) {
            ((ActivityCompat.OnRequestPermissionsResultCallback) obj).onRequestPermissionsResult(PERMISSION_CODE, permissions, grantResults);
        } else if (obj instanceof Fragment) {
            ((Fragment) obj).onRequestPermissionsResult(PERMISSION_CODE, permissions, grantResults);
        }
    }

    private int hasPermission(String[] permissions) {
        int index = -1;
        for (int i = 0, j = permissions.length; i < j; i++) {

            if (ActivityCompat.checkSelfPermission(getContext(), permissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Nullable
    private Context getContext() {
        Context context = null;
        if (obj instanceof Activity) {
            context = (Activity) obj;
        } else if (obj instanceof Fragment) {
            context = ((Fragment) obj).getContext();
        }
        return context;
    }

    public static void onRequestPermissionsResult(Object o, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length == permissions.length) {
                for (int grant : grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        if (methodFail != null) {
                            try {
                                methodFail.invoke(o, new Object[]{});
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                        return;
                    }
                }
                //权限都允许了,
                if (methodOK != null) {
                    try {
                        methodOK.setAccessible(true);
                        methodOK.invoke(o, new Object[]{});
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (methodFail != null) {
                    try {
                        methodFail.invoke(o, new Object[]{});
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface PermissionFail {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface PermissionOK {
    }

}
