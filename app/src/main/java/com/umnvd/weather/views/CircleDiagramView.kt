package com.umnvd.weather.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.umnvd.weather.R
import kotlin.math.*

class CircleDiagramView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.circleDiagramStyle,
    defStyleRes: Int = R.style.CircleDiagramDefaultStyle,
) : View(context, attrs, defStyleAttr, defStyleRes) {

    var value: Int = 0
        set(value) {
            field = min(max(value, MIN_VALUE), MAX_VALUE)
            invalidate()
        }

    private var filledColor: Int = DEFAULT_FILLED_COLOR
    private var blankColor: Int = DEFAULT_BLANK_COLOR
    private var textColor: Int = DEFAULT_TEXT_COLOR
    private var clipColor: Int = DEFAULT_CLIP_COLOR

    private var arcWidth: Float = AUTO_SIZE_VALUE
    private var textSize: Float = AUTO_SIZE_VALUE

    private val filledPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val blankPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val clipPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val diagramBounds = RectF()
    private val clipCircle = Path()
    private val textBounds = Rect()

    init {
        initAttributes(attrs, defStyleAttr, defStyleRes)
        initPaints()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val minHeight = suggestedMinimumHeight + paddingTop + paddingBottom

        val desiredDiagramSize = TypedValue
            .applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DESIRED_DIAGRAM_SIZE_DP,
                resources.displayMetrics
            ).roundToInt()

        val desiredWidth = max(minWidth, desiredDiagramSize + paddingLeft + paddingRight)
        val desiredHeight = max(minHeight, desiredDiagramSize + paddingTop + paddingBottom)

        setMeasuredDimension(
            resolveSize(desiredWidth, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        updateDiagramSize()
        updateArcWidth()
        updateTextSize()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = diagramBounds.centerX()
        val centerY = diagramBounds.centerY()

        val filledEndAngle = getFilledEndAngle(value)
        val blankStartAngle = START_ANGLE + filledEndAngle
        val blankEndAngle = MAX_ANGLE - filledEndAngle

        canvas.drawArc(diagramBounds, START_ANGLE, filledEndAngle, true, filledPaint)
        canvas.drawArc(diagramBounds, blankStartAngle, blankEndAngle, true, blankPaint)
        canvas.drawPath(clipCircle, clipPaint)

        val textCenterY = centerY + textBounds.height() / 2
        canvas.drawText(getValueWithPercent(value), centerX, textCenterY, textPaint)
    }

    private fun initAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.CircleDiagramView, defStyleAttr, defStyleRes
        )

        value = typedArray.getInt(R.styleable.CircleDiagramView_value, MIN_VALUE)
        filledColor = typedArray.getColor(R.styleable.CircleDiagramView_filledColor, DEFAULT_FILLED_COLOR)
        blankColor = typedArray.getColor(R.styleable.CircleDiagramView_blankColor, DEFAULT_BLANK_COLOR)
        textColor = typedArray.getColor(R.styleable.CircleDiagramView_textColor, DEFAULT_TEXT_COLOR)
        clipColor = typedArray.getColor(R.styleable.CircleDiagramView_clipColor, DEFAULT_CLIP_COLOR)

        // Calculated automatically depending on the size of the diagram
        arcWidth = typedArray.getDimensionPixelSize(
            R.styleable.CircleDiagramView_arcWidth,
            AUTO_SIZE_VALUE.toInt()
        ).toFloat()

        textSize = typedArray.getDimensionPixelSize(
            R.styleable.CircleDiagramView_textSize,
            AUTO_SIZE_VALUE.toInt()
        ).toFloat()

        typedArray.recycle()
    }

    private fun initPaints() {
        filledPaint.apply {
            color = filledColor
            style = Paint.Style.FILL
        }
        blankPaint.apply {
            color = blankColor
            style = Paint.Style.FILL
        }
        textPaint.apply {
            color = textColor
            textAlign = Paint.Align.CENTER
        }
        clipPaint.apply {
            color = clipColor
            style = Paint.Style.FILL
        }
    }

    private fun updateDiagramSize() {
        val safeWidth = width - paddingLeft - paddingRight
        val safeHeight = height - paddingTop - paddingBottom
        val diagramSize = min(safeWidth, safeHeight).toFloat()

        diagramBounds.apply {
            left = paddingLeft + (safeWidth - diagramSize) / 2f
            top = paddingTop + (safeHeight - diagramSize) / 2f
            right = left + diagramSize
            bottom = top + diagramSize
        }
    }

    private fun updateArcWidth() {
        val arcMinWidth = diagramBounds.width() * ARC_MIN_WIDTH_FACTOR
        val arcMaxWidth = diagramBounds.width() * ARC_MAX_WIDTH_FACTOR

        arcWidth = if (arcWidth == AUTO_SIZE_VALUE) {
            arcMaxWidth
        } else {
            min(max(arcMinWidth, arcWidth), arcMaxWidth)
        }

        clipCircle.apply {
            val centerX = diagramBounds.centerX()
            val centerY = diagramBounds.centerY()
            val radius = (diagramBounds.width() / 2f) - arcWidth

            reset()
            addCircle(centerX, centerY, radius, Path.Direction.CW)
        }
    }

    private fun updateTextSize() {
        val textMinWidth = (diagramBounds.width() - arcWidth * 2) * TEXT_MIN_WIDTH_FACTOR
        val textMaxWidth = (diagramBounds.width() - arcWidth * 2) * TEXT_MAX_WIDTH_FACTOR

        textSize = if (textSize == AUTO_SIZE_VALUE) {
            textPaint.textSize = TEST_TEXT_SIZE
            textPaint.getTextBounds(MAX_VALUE_TEXT, 0, MAX_VALUE_TEXT.length, textBounds)
            val testTextWidth = textBounds.width().toFloat()
            TEST_TEXT_SIZE * textMaxWidth / testTextWidth
        } else {
            textPaint.textSize = textSize
            textPaint.getTextBounds(MAX_VALUE_TEXT, 0, MAX_VALUE_TEXT.length, textBounds)
            val currentTextWidth = textBounds.width().toFloat()
            val desiredTextWidth = min(max(currentTextWidth, textMinWidth), textMaxWidth)
            textSize * desiredTextWidth / currentTextWidth
        }

        textPaint.textSize = textSize
        textPaint.getTextBounds(MAX_VALUE_TEXT, 0, MAX_VALUE_TEXT.length, textBounds)
    }

    private fun getFilledEndAngle(value: Int): Float {
        return (value / MAX_VALUE.toFloat()) * MAX_ANGLE
    }

    private fun getValueWithPercent(value: Int): String {
        return String.format("%d%%", value)
    }

    companion object {

        const val MIN_VALUE = 0
        const val MAX_VALUE = 100

        private const val START_ANGLE = 90f
        private const val MAX_ANGLE = 360f

        private const val ARC_MIN_WIDTH_FACTOR = 0.05f
        private const val ARC_MAX_WIDTH_FACTOR = 0.2f
        private const val TEXT_MIN_WIDTH_FACTOR = 0.34f
        private const val TEXT_MAX_WIDTH_FACTOR = 0.9f
        private const val TEST_TEXT_SIZE = 100f

        private const val DEFAULT_FILLED_COLOR = Color.BLACK
        private const val DEFAULT_BLANK_COLOR = Color.GRAY
        private const val DEFAULT_TEXT_COLOR = Color.BLACK
        private const val DEFAULT_CLIP_COLOR = Color.WHITE

        private const val DESIRED_DIAGRAM_SIZE_DP = 100f
        private const val AUTO_SIZE_VALUE = -1f
        private const val MAX_VALUE_TEXT = "100%"
    }
}