package com.jaydenxiao.common.injects

import android.animation.*
import android.os.Build
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator

/**
 * Created by 13198 on 2018/9/23.
 * @desc 动画
 * @author cxh
 *
 */


/**
 * Created by dengyulin on 2018/4/27.
 */
/**
 *@AccelerateDecelerateInterpolator        动画开始与结束的地方速率改变比较慢，在中间的时候加速。
 *@AccelerateInterpolator                  动画开始的地方速率改变比较慢，然后开始加速。
 *@AnticipateInterpolator                  开始的时候向后然后向前甩。
 *@AnticipateOvershootInterpolator         开始的时候向后然后向前甩一定值后返回最后的值。
 *@BounceInterpolator                      动画结束的时候弹起。
 *@CycleInterpolator                       动画循环播放特定的次数，速率改变沿着正弦曲线。
 *@DecelerateInterpolator                  在动画开始的地方快然后慢。
 *@LinearInterpolator                      以常量速率改变。
 *@OvershootInterpolator                   向前甩一定值后再回到原来位置。
 *@PathInterpolator                        新增的，就是可以定义路径坐标，然后可以按照路径坐标来跑动；注意其坐标并不是 XY，而是单方向，也就是我可以从0~1，然后弹回0.5 然后又弹到0.7 有到0.3，直到最后时间结束。
 * */
fun <T> View.animation(i1: T, i2: T, time: Long = 250, delay: Long = 0, rCount: Int = 0, rModel: Int = ValueAnimator.RESTART, interpola: TimeInterpolator = DecelerateInterpolator(), end: () -> Unit = {}, call: (value: T) -> Unit): ValueAnimator? {
    val anim = when (i1) {
        is Int -> ValueAnimator.ofInt(i1 as Int, i2 as Int)
        is Float -> ValueAnimator.ofFloat(i1 as Float, i2 as Float)
        else -> return null
    }
    anim.apply {
        duration = time
        startDelay = delay
        repeatCount = rCount
        repeatMode = rModel
        interpolator = interpola
        addUpdateListener {
            call.invoke(it.animatedValue as T)
        }
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                end.invoke()
            }
        })

    }.start()
    return anim
}

fun View.animationArgb(startColor: Int, endColor: Int, time: Long = 250, delay: Long = 0, rCount: Int = 0, rModel: Int = ValueAnimator.RESTART, interpola: TimeInterpolator = LinearInterpolator(), call: (value: Int) -> Unit) {
    (if (Build.VERSION.SDK_INT < 21)
        ValueAnimator.ofObject(TextArgbEvaluator(), startColor, endColor)
    else
        ValueAnimator.ofArgb(startColor, endColor)).apply {
        duration = time
        startDelay = delay
        repeatCount = rCount
        repeatMode = rModel
        interpolator = interpola
        addUpdateListener {
            call.invoke(it.animatedValue as Int)
        }
    }
}

class TextArgbEvaluator : TypeEvaluator<Int> {
    override fun evaluate(fraction: Float, startValue: Int, endValue: Int): Int {
        val startInt = startValue
        val startA = startInt shr 24 and 0xff
        val startR = startInt shr 16 and 0xff
        val startG = startInt shr 8 and 0xff
        val startB = startInt and 0xff

        val endInt = endValue
        val endA = endInt shr 24 and 0xff
        val endR = endInt shr 16 and 0xff
        val endG = endInt shr 8 and 0xff
        val endB = endInt and 0xff

        return startA + (fraction * (endA - startA)).toInt() shl 24 or
                (startR + (fraction * (endR - startR)).toInt() shl 16) or
                (startG + (fraction * (endG - startG)).toInt() shl 8) or
                startB + (fraction * (endB - startB)).toInt()
    }

}

//1.0 - 0.0
fun View.animationAlpha(alpha: Float, toAlpha: Float, time: Long = 250, delay: Long = 0, end: () -> Unit = {}) {
    animation(alpha, toAlpha, time, delay, end = end) {
        setAlpha(it)
    }
}

fun View.move(x: Float = 0f, y: Float = 0f, x1: Float = 0f, y1: Float = 0f, z: Float = 0f, z1: Float = 0f, time: Long = 250, delay: Long = 0, interpola: TimeInterpolator = AccelerateDecelerateInterpolator(), end: () -> Unit = {}) {
    if (x != x1) {
        animation(x, x1, time, delay, interpola = interpola, end = end, call = { translationX = it })
    }
    if (y != y1) {
        animation(y, y1, time, delay, interpola = interpola, end = end, call = { translationY = it })
    }
    if (z != z1) {
        animation(z, z1, time, delay, interpola = interpola, end = end, call = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                translationZ = it
                elevation = it
            }
        })
    }

}

fun View.rotation(angle: Float = 0f, toAngle: Float = 0f, s: Int = 0, pX: Float = 0f, pY: Float = 0f, time: Long = 250, delay: Long = 0, rCount: Int = 0, rModel: Int = ValueAnimator.RESTART, interpola: TimeInterpolator = LinearInterpolator()) {
    if (angle != toAngle) {
        if (pX != 0f) pivotX = pX
        if (pY != 0f) pivotY = pY
        animation(angle, toAngle, time, delay, rCount, rModel, interpola) { value ->
            when (s) {
                0 -> rotation = value
                1 -> rotationX = value
                2 -> rotationY = value
            }

        }
    }
}

fun View.scale(x: Float = 0F, y: Float = 0F, x1: Float = 0F, y1: Float = 0F, pX: Float = 0f, pY: Float = 0f, time: Long = 250, delay: Long = 0) {
    if (pX != 0f) pivotX = pX
    if (pY != 0f) pivotY = pY
    if (x != x1) {
        animation(x, x1, time, delay, interpola = LinearInterpolator()) {
            scaleX = it
        }
    }
    if (y != y1) {
        animation(x, x1, time, delay, interpola = LinearInterpolator()) {
            scaleY = it
        }
    }
}
