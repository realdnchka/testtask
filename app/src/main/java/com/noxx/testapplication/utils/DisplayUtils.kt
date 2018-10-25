package com.noxx.testapplication.utils

import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.TypedValue
import android.view.WindowManager
import com.noxx.testapplication.R
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Azarev on 24.01.2017.
 */

object DisplayUtils {

    private val sNextGeneratedId = AtomicInteger(1)

    fun dp2px(context: Context, dipValue: Float): Int {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics).toInt()
    }

    fun getScreenWidth(context: Context): Int {
        val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    fun getScreenHeight(context: Context): Int {
        val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.y
    }

    fun isTablet(context: Context): Boolean {
        return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    fun generateViewId(): Int {
        while (true) {
            val result = sNextGeneratedId.get()
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            var newValue = result + 1
            if (newValue > 0x00FFFFFF) newValue = 1 // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result
            }
        }
    }

    fun getDrawableWithTint(context: Context, resource_id: Int): Drawable? {
        var d = ContextCompat.getDrawable(context, resource_id)
        if (d != null) {
            d = DrawableCompat.wrap(d)
            DrawableCompat.setTint(d.mutate(), ContextCompat.getColor(context, R.color.colorPrimary))
            return d
        } else {
            return null
        }
    }

    fun getSelectableBackground(context: Context): Int {
        val outValue = TypedValue()
        context.theme.resolveAttribute(R.attr.selectableItemBackgroundBorderless, outValue, true)
        return outValue.resourceId
    }

}
