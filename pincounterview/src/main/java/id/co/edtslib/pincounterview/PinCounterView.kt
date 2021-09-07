package id.co.edtslib.pincounterview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import java.util.*

class PINCounterView: FrameLayout {
    enum class FormatType {
        Clock, Text
    }
    private var tvCounter: TextView? = null
    var interval = 0L
    private var startTime = 0L
    private var format = FormatType.Clock
    private var counterText: String? = null
    private var sHour: String? = null
    private var sMinute: String? = null
    private var sSecond: String? = null

    private var runnable: Runnable? = null
    var delegate: PINCounterDelegate? = null

    constructor(context: Context) : super(context) {
        init(null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val view = inflate(context, R.layout.view_pin_counter, this)

        tvCounter = view.findViewById(R.id.tvCounter)

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.PINCounterView,
                0, 0
            )

            counterText = a.getString(R.styleable.PINCounterView_pinCounterText)
            interval = a.getInt(R.styleable.PINCounterView_pinCounterInterval, 0).toLong()
            format = FormatType.values()[a.getInteger(R.styleable.PINCounterView_pinCounterFormat, 0)]

            sHour = a.getString(R.styleable.PINCounterView_pinCounterHour)
            sMinute = a.getString(R.styleable.PINCounterView_pinCounterMinute)
            sSecond = a.getString(R.styleable.PINCounterView_pinCounterSecond)

            a.recycle()
        }
    }

    fun setTextStyle(style: Int) {
        if (tvCounter != null) {
            TextViewCompat.setTextAppearance(tvCounter!!, style)
        }
    }

    fun start() {
        startTime = Date().time
        reset()
    }

    fun stop() {
        if (runnable != null) {
            tvCounter?.removeCallbacks(runnable)
            runnable = null
        }
    }

    fun reset() {
        showRemain(interval)

        timer()
    }

    private fun timer() {
        stop()

        runnable = Runnable {
            val remain = interval - ((Date().time-startTime)/1000)
            if (remain > 0) {
                showRemain(remain)
                timer()
            }
            else {
                tvCounter?.isVisible = false
                delegate?.onExpired()
            }
        }
        tvCounter?.postDelayed(runnable, 1000L)
    }

    private fun showRemain(interval: Long) {
        if (counterText != null) {
            val hour = interval / 3600
            val minute = (interval % 3600) / 60
            val second = (interval % 3600) % 60

            val remain = if (format == FormatType.Text) {
                if (hour > 0) {
                    if (minute > 0 && second > 0) {
                        String.format("%d %s %d %s %d %s", hour, sHour, minute, sMinute,
                            second, sSecond)
                    }
                    else if (minute > 0) {
                        String.format("%d %s %d %s", hour, sHour, minute, sMinute)
                    }
                    else if (second > 0) {
                        String.format("%d %s %d %s", hour, sHour, second, sSecond)
                    }
                    else {
                        String.format("%d %s", hour, sHour)
                    }
                }
                else
                    if (minute > 0) {
                        if (second > 0) {
                            String.format("%d %s %d %s", minute, sMinute, second, sSecond)
                        }
                        else {
                            String.format("%d %s", minute, sMinute)
                        }
                    }
                    else {
                        String.format("%d %s", second, sSecond)
                    }
            }
            else {
                when {
                    hour > 0 -> {
                        String.format("%02d:%02d:%02d", hour, minute, second)
                    }
                    else -> {
                        String.format("%02d:%02d", minute, second)
                    }
                }
            }

            tvCounter?.text = HtmlCompat.fromHtml(String.format(counterText!!, remain),
                HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }
}