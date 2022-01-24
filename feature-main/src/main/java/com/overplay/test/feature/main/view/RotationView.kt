package com.overplay.test.feature.main.view

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withSave
import com.overplay.test.common.ui.ext.fractionAtValue
import com.overplay.test.common.ui.ext.getAttrColor
import com.overplay.test.common.ui.ext.invalidateDelegate
import com.overplay.test.common.ui.ext.requestLayoutDelegate
import com.overplay.test.feature.main.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.max
import kotlin.math.roundToInt

class RotationView : View {

    private val gaugeBounds = RectF()
    private val activeTextPaddedBounds = RectF()

    private val activeTextPaint = TextPaint(PAINT_FLAGS).apply {
        style = Paint.Style.FILL_AND_STROKE
    }
    private val inactiveTextPaint = TextPaint(PAINT_FLAGS).apply {
        style = Paint.Style.FILL_AND_STROKE
    }
    private val linePaint = Paint(PAINT_FLAGS).apply {
        style = Paint.Style.STROKE
    }
    private val tickPaint = Paint(PAINT_FLAGS).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    var activeTextSize by requestLayoutDelegate(10.0F) { _, _, newValue ->
        activeTextPaint.textSize = newValue
    }

    var activeTextColor by invalidateDelegate(Color.LTGRAY) { _, _, newValue ->
        activeTextPaint.color = newValue
    }

    var inactiveTextSize by requestLayoutDelegate(10.0F) { _, _, newValue ->
        inactiveTextPaint.textSize = newValue
    }

    var inactiveTextClipOffset by invalidateDelegate(0.0F)

    var inactiveTextColor by invalidateDelegate(Color.LTGRAY) { _, _, newValue ->
        inactiveTextPaint.color = newValue
    }

    var textOffsetY by requestLayoutDelegate(10.0F)

    var lineColor by invalidateDelegate(Color.GRAY) { _, _, newValue ->
        linePaint.color = newValue
    }

    var tickColor by invalidateDelegate(Color.LTGRAY) { _, _, newValue ->
        tickPaint.color = newValue
    }

    var lineWidth by requestLayoutDelegate(1.0F) { _, _, newValue ->
        linePaint.strokeWidth = newValue
    }

    var tickWidth by requestLayoutDelegate(1.0F) { _, _, newValue ->
        tickPaint.strokeWidth = newValue
    }

    var tickHeight by requestLayoutDelegate(10.0F)

    var minValue by requestLayoutDelegate(-90.0)
    var maxValue by requestLayoutDelegate(90.0)
    var value = 0.0
        set(value) {
            if (field == value) return
            val currentFormattedValue = textAdapter.getTextFormatted(field)
            val newFormattedValue = textAdapter.getTextFormatted(value)
            field = value
            if (newFormattedValue != currentFormattedValue) {
                invalidate()
            }
        }

    var textAdapter: TextAdapter by requestLayoutDelegate(DefaultTextAdapter())

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet? = null
    ) {

        setWillNotDraw(false)

        // Default Values
        val density = resources.displayMetrics.density
        activeTextSize = density * 14.0F
        inactiveTextSize = density * 12.0F
        inactiveTextClipOffset = density * 2.0F
        textOffsetY = density * 4.0F
        activeTextColor = context.getAttrColor(R.attr._color_txt)
        inactiveTextColor = context.getAttrColor(R.attr._color_txt_secondary)
        lineColor = context.getAttrColor(R.attr._color_secondary)
        tickColor = context.getAttrColor(R.attr._color_secondary_variant)
        lineWidth = density * 3.0F
        tickWidth = density * 2.0F
        tickHeight = density * 12.0F
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val newHeightMeasureSpec = when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.AT_MOST -> {
                MeasureSpec.makeMeasureSpec(
                    (max(
                        activeTextSize,
                        inactiveTextSize,
                    ) + textOffsetY + tickHeight + 0.5F).roundToInt(),
                    MeasureSpec.EXACTLY
                )
            }

            else -> heightMeasureSpec
        }

        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)

        gaugeBounds.set(
            paddingLeft.toFloat(),
            measuredHeight - paddingBottom - tickHeight,
            (measuredWidth - paddingRight).toFloat(),
            (measuredHeight - paddingBottom).toFloat(),
        )

        val minValueWidth = inactiveTextPaint.measureText(textAdapter.getTextFormatted(minValue))
        val maxValueWidth = inactiveTextPaint.measureText(textAdapter.getTextFormatted(maxValue))
        val activeMinValueWidth =
            activeTextPaint.measureText(textAdapter.getTextFormatted(minValue))
        val activeMaxValueWidth =
            activeTextPaint.measureText(textAdapter.getTextFormatted(maxValue))

        val maxInset = listOf(
            minValueWidth,
            maxValueWidth,
            activeMinValueWidth,
            activeMaxValueWidth,
            tickWidth,
        ).maxOrNull()!!

        gaugeBounds.inset(maxInset / 2.0F, 0.0F)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas ?: return)

        canvas.drawLine(
            gaugeBounds.left, gaugeBounds.centerY(),
            gaugeBounds.right, gaugeBounds.centerY(),
            linePaint,
        )

        canvas.drawLine(
            gaugeBounds.left, gaugeBounds.top,
            gaugeBounds.left, gaugeBounds.bottom,
            tickPaint,
        )

        canvas.drawLine(
            gaugeBounds.centerX(), gaugeBounds.top,
            gaugeBounds.centerX(), gaugeBounds.bottom,
            tickPaint,
        )

        canvas.drawLine(
            gaugeBounds.right, gaugeBounds.top,
            gaugeBounds.right, gaugeBounds.bottom,
            tickPaint,
        )

        val currentX =
            (gaugeBounds.left + fractionAtValue(minValue, maxValue, value) * gaugeBounds.width())
                .coerceIn(gaugeBounds.left.toDouble(), gaugeBounds.right.toDouble())
                .toFloat()

        canvas.drawLine(
            currentX, gaugeBounds.top,
            currentX, gaugeBounds.bottom,
            tickPaint,
        )

        val valueWidth = activeTextPaint.measureText(textAdapter.getTextFormatted(value))

        activeTextPaddedBounds.set(
            currentX - valueWidth / 2.0F,
            gaugeBounds.top - textOffsetY - activeTextSize,
            currentX + valueWidth / 2.0F,
            gaugeBounds.top - textOffsetY,
        )
        activeTextPaddedBounds.inset(
            -inactiveTextClipOffset,
            -inactiveTextClipOffset
        )

        canvas.withSave {
            canvas.clipRect(0, 0, width, height)
            canvas.clipRect(activeTextPaddedBounds, Region.Op.DIFFERENCE)

            val minValueWidth =
                inactiveTextPaint.measureText(textAdapter.getTextFormatted(minValue))
            val maxValueWidth =
                inactiveTextPaint.measureText(textAdapter.getTextFormatted(maxValue))
            val zeroText = textAdapter.getTextFormatted(0.0)
            val zeroValueWidth = inactiveTextPaint.measureText(zeroText)

            canvas.drawText(
                textAdapter.getTextFormatted(minValue),
                gaugeBounds.left - minValueWidth / 2.0F,
                gaugeBounds.top - textOffsetY,
                inactiveTextPaint
            )

            canvas.drawText(
                textAdapter.getTextFormatted(maxValue),
                gaugeBounds.right - maxValueWidth / 2.0F,
                gaugeBounds.top - textOffsetY,
                inactiveTextPaint
            )

            canvas.drawText(
                zeroText,
                gaugeBounds.centerX() - zeroValueWidth / 2.0F,
                gaugeBounds.top - textOffsetY,
                inactiveTextPaint
            )
        }

        canvas.drawText(
            textAdapter.getTextFormatted(value),
            currentX - valueWidth / 2.0F,
            gaugeBounds.top - textOffsetY,
            activeTextPaint
        )
    }

    fun interface TextAdapter {
        fun getTextFormatted(value: Double): String
    }

    inner class DefaultTextAdapter : TextAdapter {
        private val pattern = "#,##0.#"
        private val decimalFormat = DecimalFormat(
            pattern,
            DecimalFormatSymbols()
        )

        override fun getTextFormatted(value: Double): String {
            return value
                .coerceIn(minValue, maxValue)
                .run(decimalFormat::format)
        }
    }

    companion object {
        private const val PAINT_FLAGS =
            Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG or Paint.FILTER_BITMAP_FLAG
    }
}