package weather.android.com.util.customview

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.widget.TextView
import androidx.annotation.RequiresApi

open class LightTextView : TextView {
    constructor(context: Context) : super(context) {

        applyFont(context)
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        applyFont(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        applyFont(context)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        applyFont(context)

    }

    private fun applyFont(context: Context) {

        try {
            val typeface: Typeface = Typeface.createFromAsset(context.assets, "Roboto-Thin.ttf")
            setTypeface(typeface)
        } catch (e: Exception) {
        }

    }

}
