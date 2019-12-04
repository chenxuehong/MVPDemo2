package com.jaydenxiao.common.ui.view.address;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.jaydenxiao.common.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 城市Picker
 *
 * @author LOVE
 */
public class CityPicker extends LinearLayout {
    /**
     * 滑动控件
     */
    private ScrollerNumberPicker provincePicker;
    private ScrollerNumberPicker cityPicker;
    private ScrollerNumberPicker areaPicker;
    /**
     * 选择监听
     */
    private OnSelectingListener onSelectingListener;
    /**
     * 刷新界面
     */
    private static final int REFRESH_VIEW = 0x001;
    /**
     * 临时索引 主要解决第一次重复触发的问题
     */
    // 如:第一个选择了县,并且改变了值, 这时如果在选择市,即使不改变值,只要触发,县就会初始化,此处就是解决这个问题的
    private int tempProvinceIndex = -1;
    private int temCityIndex = -1;
    private int tempCounyIndex = -1;
    /**
     * 省的集合
     */
    private CityList list;
    /**
     * 市
     */
    private Cityinfo select_province;
    /**
     * 市
     */
    private Cityinfo select_city;
    /**
     * 县
     */
    private Cityinfo select_area;

    public CityPicker(Context context) {
        super(context, null);
    }

    public CityPicker(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

    }

    public CityPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void initJson() {
        InputStream is = null;
        InputStreamReader isr = null;
        try {
            is = this.getContext().getAssets().open("arealist.json");
            isr = new InputStreamReader(is);
            Gson s = new Gson();
            list = s.fromJson(isr, CityList.class);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void initAddress() {
        select_province = list.getProvince().get(0);
        provincePicker.setData(list.getProvince());
        //市
        select_city = select_province.getChildren().get(0);
        cityPicker.setData(select_province.getChildren());
        //区
        select_area = select_city.getChildren().get(0);
        areaPicker.setData(select_city.getChildren());

        provincePicker.setDefault(0);
        cityPicker.setDefault(0);
        areaPicker.setDefault(0);
    }

    /**
     * 获取城市信息
     */
    private void getaddressinfo() {
    }

    private Cityinfo getCityInfo(List<Cityinfo> addresss, String address) {
        for (Cityinfo cityinfo : addresss) {
            if (cityinfo.getName().equals(address)) {
                return cityinfo;
            }
        }
        return null;
    }

    public void setAddress(String privince, String city, String area) {
        select_province = getCityInfo(list.getProvince(), privince);
        provincePicker.moveToPostion(select_province.getSort() - 1);
        select_city = getCityInfo(select_province.getChildren(), city);
        cityPicker.moveToPostion(select_city.getSort() - 1);
        select_area = getCityInfo(select_city.getChildren(), area);
        areaPicker.moveToPostion(select_area.getSort() - 1);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        LayoutInflater.from(getContext()).inflate(R.layout.city_picker, this);

        // 获取控件引用
        provincePicker = (ScrollerNumberPicker) findViewById(R.id.province);
        cityPicker = (ScrollerNumberPicker) findViewById(R.id.city);
        areaPicker = (ScrollerNumberPicker) findViewById(R.id.area);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initJson();
                initAddress();
            }
        }).start();

        //省

//		counyPicker.setDefault(0);
        // 设置省控件的监听器

        provincePicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {

            @Override
            public void endSelect(int id, String text) {
                if (text.equals("") || text == null) {
                    return;
                }
                if (tempProvinceIndex != id) {
                    String selectDay = cityPicker.getSelectedText();
                    if (selectDay == null || selectDay.equals("")) {
                        return;
                    }
                    if (temCityIndex < 0) {
                        temCityIndex = 0;
                    }
                    if (tempCounyIndex < 0) {
                        tempCounyIndex = 0;
                    }

                    // 设置市的数据内容

                    select_province = getCityInfo(list.getProvince(), text);
                    select_city = select_province.getChildren().get(0);
                    select_area = select_city.getChildren().get(0);

                    cityPicker.setData(select_province.getChildren());
                    cityPicker.setDefault(0);

                    areaPicker.setData(select_city.getChildren());
                    areaPicker.setDefault(0);
                    // 设置县的数据内容
                }
                tempProvinceIndex = id;
                Message message = new Message();
                message.what = REFRESH_VIEW;
                handler.sendMessage(message);
            }

            @Override
            public void selecting(int id, String text) {
            }
        });
        // 设置市控件的监听器
        cityPicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {

            @Override
            public void endSelect(int id, String text) {
                if (text.equals("") || text == null) {
                    return;
                }
                if (temCityIndex != id) {
                    String selectDay = provincePicker.getSelectedText();
                    if (selectDay == null || selectDay.equals("")) {
                        return;
                    }
                    if (tempCounyIndex < 0) {
                        tempCounyIndex = 0;
                    }
                    if (tempProvinceIndex < 0) {
                        tempProvinceIndex = 0;
                    }

                    select_city = getCityInfo(select_province.getChildren(), text);
                    areaPicker.setData(select_city.getChildren());
                    areaPicker.moveToPostion(0);
//                    select_area = getCityInfo(select_city.getChildren(), text);
                }
                temCityIndex = id;
                Message message = new Message();
                message.what = REFRESH_VIEW;
                handler.sendMessage(message);
            }

            @Override
            public void selecting(int id, String text) {

            }
        });
        areaPicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {

            @Override
            public void endSelect(int id, String text) {
                if (text.equals("") || text == null) {
                    return;
                }
//                if (tempCounyIndex != id) {
                    String selectDay = cityPicker.getSelectedText();
                    if (selectDay == null || selectDay.equals("")) {
                        return;
                    }
                    if (temCityIndex < 0) {
                        temCityIndex = 0;
                    }
                    if (tempProvinceIndex < 0) {
                        tempProvinceIndex = 0;
                    }
                    select_area = getCityInfo(select_city.getChildren(), text);

//                }
                temCityIndex = id;
                Message message = new Message();
                message.what = REFRESH_VIEW;
                handler.sendMessage(message);
            }

            @Override
            public void selecting(int id, String text) {

            }
        });
    }

    // 这是用来更新界面，和绑定监听器值的
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_VIEW:
                    if (onSelectingListener != null)
                        onSelectingListener.selected(true, select_province.getName(),
                                select_city.getName(), select_area.getName(), select_area.getAreaCode());
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 绑定监听器
     *
     * @param onSelectingListener 控件的监听器接口
     */
    public void setOnSelectingListener(OnSelectingListener onSelectingListener) {
        this.onSelectingListener = onSelectingListener;
    }

    /**
     * 得到所选择的省的名称
     *
     * @return 省的名称
     */
    public String getprovince_name() {
        return select_province.getName();
    }

    /**
     * 得到所选择的市的名称
     *
     * @return 市的名称
     */
    public String getcity_name() {
        return select_city.getName();
    }

    /**
     * 得到所选择的县的名称
     *
     * @return 省县的名称
     */
    public String getcouny_name() {
        return select_area.getName();
    }

    /**
     * 得到所选择的城市的的天气查询代码
     *
     * @return 城市的的天气查询代码
     */
    public String getcity_code() {
        return select_area.getAreaCode();
    }


    /**
     * 监听器接口
     *
     * @author LOVE
     */
    public interface OnSelectingListener {

        /**
         * @param selected      是否选择该控件？？？
         * @param province_name 省的名称
         * @param city_name     市的名称
         * @param couny_name    县的名称
         * @param city_code     城市天气代码
         */
        public void selected(boolean selected, String province_name,
                             String city_name, String couny_name, String city_code);
    }
}
