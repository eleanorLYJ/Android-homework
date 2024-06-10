package tw.edu.ncku.iim.rsliu.setcard

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class SetCardView : View {
    public var number:Int = 1
        set(value) {
            if (value in 1..3) {
                field = value
                invalidate()
            }
        }

    public var shape = SetCard.Shape.OVAL
        set(value) {
            field = value
            invalidate()
        }

    public var color = Color.BLUE
        set(value) {
            field = value
            invalidate()
        }

    public var shading = SetCard.Shading.EMPTY
        set(value) {
            field = value
            invalidate()
        }

    companion object SetCardConstants {
        const val CARD_STANDARD_HEIGHT =  240.0f
        const val CORNER_RADIUS = 12.0f
        const val SYMBOL_WIDTH_SCALE_FACTOR = 0.6f
        const val SYMBOL_HEIGHT_SCALE_FACTOR = 0.125f
        const val STRIP_DISTANCE_SCALE_FACTOR = 0.05f
    }

    private val cornerScaleFactor: Float
        get() {
            return height / CARD_STANDARD_HEIGHT
        }

    private val cornerRadius: Float
        get() {
            return CORNER_RADIUS * cornerScaleFactor
        }

    private val mPaint = Paint().apply { isAntiAlias = true }
    private val mTextPaint = Paint().apply { isAntiAlias = true }
    private fun init(context: Context, attrs: AttributeSet? = null) {
        mPaint.setAntiAlias(true);
        mTextPaint.setAntiAlias(true);

    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context, attrs,defStyle) {
        init(context, attrs)
    }

    private fun drawShapeWithVerticalOffset(canvas: Canvas, voffset: Float) {
        val path = Path()
        val width = width * SYMBOL_WIDTH_SCALE_FACTOR
        val height = height * SYMBOL_HEIGHT_SCALE_FACTOR

        when (shape) {
            SetCard.Shape.OVAL -> {
                // Todo: Draw OVAL Shape here
                // Draw Oval using RectF
                val oval = RectF(
                    (width / 2) - width * 0.15f, // Left
                    (height / 2) - (height * 0.2f) + voffset, // Top - 20% lower from center
                    (width / 2) + width, // Right
                    (height / 2) + height + voffset // Bottom
                )
                canvas.drawOval(oval, mPaint)
            }

            SetCard.Shape.DIAMOND -> {
                // Todo: Draw DIAMOND shape here
                val center = PointF((getWidth() / 2).toFloat(), getHeight() / 2 + voffset)
                val halfWidth = width / 2f
                val halfHeight = height / 2f
                path.moveTo(center.x, center.y + halfHeight) // Top point
                path.lineTo(center.x + halfWidth, center.y) // Right point
                path.lineTo(center.x, center.y - halfHeight) // Bottom point
                path.lineTo(center.x - halfWidth, center.y) // Left point
                path.close() // Close the path to create a filled diamond
                canvas.drawPath(path, mPaint)
            }

            SetCard.Shape.WORM -> {
                // WORM
                val center = PointF((getWidth() / 2).toFloat(), getHeight() / 2 + voffset)
                path.moveTo(center.x - width / 2, center.y + height / 2)

                val cp1 = PointF(center.x - width / 4, center.y - height * 1.5f)
                val cp2 = PointF(center.x + width / 4, center.y)
                val dst = PointF(center.x + width / 2, center.y - height / 2)
                path.cubicTo(cp1.x, cp1.y, cp2.x, cp2.y, dst.x, dst.y)

                cp1.x = center.x + width / 2
                cp1.y = center.y + height * 2
                cp2.x = center.x - width / 2
                cp2.y = center.y

                dst.x = center.x - width / 2
                dst.y = center.y + height / 2

                path.cubicTo(cp1.x, cp1.y, cp2.x, cp2.y, dst.x, dst.y)
                canvas.drawPath(path, mPaint)
            }
        }
        drawShadingInPath(canvas, path);
    }

    private fun drawShapes(canvas: Canvas) {
        mPaint.color = color
        when (number) {
            1,3 -> {
                drawShapeWithVerticalOffset(canvas, 0f)
                if (number == 3) {
                    // Todo: draw two additional shapes
                    drawShapeWithVerticalOffset(canvas, 20f) // Positive offset
                    drawShapeWithVerticalOffset(canvas, -40f) // Negative offset
                }
            }
            2 -> {
                // Todo: draw two shapes with positive and negative offsets
                drawShapeWithVerticalOffset(canvas, 30f) // Positive offset
                drawShapeWithVerticalOffset(canvas, -60f) // Negative offset
            }
        }
    }

    private fun drawShadingInPath(canvas: Canvas, path: Path) {
        canvas.save()

        when (shading) {
            SetCard.Shading.SOLID -> {
                mPaint.style = Paint.Style.FILL
                canvas.drawPath(path, mPaint)
            }
            SetCard.Shading.STRIP -> {
                canvas.clipPath(path)
                val stripPath = Path()
                val stripDistance = width * STRIP_DISTANCE_SCALE_FACTOR
                var x = 0f
                while (x < width) {
                    stripPath.moveTo(x, 0f)
                    stripPath.lineTo(x, height.toFloat())
                    x += stripDistance
                }
                canvas.drawPath(stripPath, mPaint)
            }
            SetCard.Shading.EMPTY -> {
                mPaint.style = Paint.Style.STROKE
                canvas.drawPath(path, mPaint)
            }
        }

        canvas.restore()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val path = Path()
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        path.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW)
        // Intersect the current clip with the specified path
        canvas.clipPath(path)
        // fill
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.WHITE
        canvas.drawPath(path, mPaint)
        // border
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 3.0f
        mPaint.color = Color.BLACK
        canvas.drawPath(path, mPaint)

        drawShapes(canvas)
    }
}
