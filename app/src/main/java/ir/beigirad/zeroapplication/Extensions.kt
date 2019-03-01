package ir.beigirad.zeroapplication

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import ir.beigirad.nearly.R
import java.util.*
import java.util.regex.Pattern

fun ViewGroup.inflate(resource: Int): View {
    return LayoutInflater.from(this.context).inflate(resource, this, false)
}

fun ImageView.loadUrl(url: String?) {
    Glide.with(this)
        .applyDefaultRequestOptions(RequestOptions().placeholder(R.mipmap.ic_launcher))
        .load(url)
        .into(this)
}

fun Context.toast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.toast(textRes: Int) {
    Toast.makeText(this, textRes, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(text: CharSequence) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(textRes: Int) {
    Toast.makeText(context, textRes, Toast.LENGTH_SHORT).show()
}

fun Context.snack(
    view: View,
    text: String,
    time: Int = Snackbar.LENGTH_SHORT,
    listener: (() -> Unit)? = null
) {
    val snackbar = Snackbar.make(view, text, time)
    snackbar.setAction(R.string.try_again) {
        listener?.invoke()
    }
    snackbar.setActionTextColor(resources.getColor(R.color.icons))
    snackbar.show()
}

fun Fragment.snack(
    view: View,
    text: String,
    time: Int = Snackbar.LENGTH_SHORT,
    listener: (() -> Unit)? = null
) {
    context?.snack(view, text, time, listener)
}

fun Context.displayMetrics(): DisplayMetrics {
    val displayMetrics = DisplayMetrics()
    (this as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics
}

fun Context.isTablet(): Boolean {
    return resources.configuration.screenLayout and
            Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
}

fun TextView.strictThrough() {
    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

fun <T : FrameLayout> T.isViewVisible(view: View): Boolean {
    val scrollBounds = Rect()
    getDrawingRect(scrollBounds)

    val _top = view.y
    val _bottom = _top + view.height

    return scrollBounds.top < _top && scrollBounds.bottom > _bottom
}

inline fun <T : Fragment> T.withArgs(argBuilder: Bundle.() -> Unit) =
    this.apply { arguments = Bundle().apply(argBuilder) }

fun <T> List<T>.random(): T? = if (this.isNotEmpty()) get(Random().nextInt(size)) else null

fun TabLayout.addTabs(vararg titles: String) {
    titles.forEachIndexed { index, it -> addTab(newTab().setText(it).setTag(index)) }
}

fun String.indexOfAll(arg: String?): List<Pair<Int, Int>> {
    val pos = ArrayList<Pair<Int, Int>>()
    val matcher = Pattern.compile(arg).matcher(this)
    while (matcher.find()) {
        pos.add(Pair(matcher.start(), matcher.end()))
    }
    return pos
}

fun View.visiable() {
    visibility = View.VISIBLE
}

fun View.invisiable() {
    visibility = View.INVISIBLE
}

val String.containDigit: Boolean
    get() {
        iterator().forEach {
            if (!it.isDigit())
                return false
        }
        return true
    }

val String.isMobileNumberFormat: Boolean
    get() = matches(Regex("09\\w{9}"))

val String.toPriceFormat: String
    get() = String.format("%,d", toIntOrNull() ?: 0)
