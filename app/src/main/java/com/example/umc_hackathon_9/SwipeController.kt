package com.example.umc_hackathon_9

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.MotionEvent
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class SwipeController(
    private val context: Context,
    private val buttonWidthPx: Float = 200f, // 버튼 하나 너비(대략)
    private val onArchive: (pos: Int) -> Unit,
    private val onDone: (pos: Int) -> Unit,
    private val onDelete: (pos: Int) -> Unit
) : ItemTouchHelper.Callback() {

    private var currentItemViewHolder: RecyclerView.ViewHolder? = null
    private var swipeBack = false

    private val buttonRects = mutableListOf<Pair<RectF, () -> Unit>>() // 클릭영역 + 액션

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // 버튼 탭으로만 액션 처리. 여기서는 아무것도 안 함.
    }

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (swipeBack) {
            swipeBack = false
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    fun attachToRecyclerView(recyclerView: RecyclerView) {
        val helper = ItemTouchHelper(this)
        helper.attachToRecyclerView(recyclerView)

        recyclerView.setOnTouchListener { _, event ->
            if (currentItemViewHolder == null) return@setOnTouchListener false

            if (event.action == MotionEvent.ACTION_UP) {
                val x = event.x
                val y = event.y
                val clicked = buttonRects.firstOrNull { (rect, _) -> rect.contains(x, y) }
                if (clicked != null) {
                    clicked.second.invoke()
                    swipeBack = true
                    recyclerView.adapter?.notifyItemChanged(currentItemViewHolder!!.bindingAdapterPosition)
                    currentItemViewHolder = null
                    buttonRects.clear()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState != ItemTouchHelper.ACTION_STATE_SWIPE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        currentItemViewHolder = viewHolder
        buttonRects.clear()

        val itemView = viewHolder.itemView
        val pos = viewHolder.bindingAdapterPosition
        if (pos == RecyclerView.NO_POSITION) return

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        val clampedDX = when {
            dX < 0 -> dX.coerceAtLeast(-buttonWidthPx * 2) // LEFT: 2 buttons
            else -> dX.coerceAtMost(buttonWidthPx)         // RIGHT: 1 button
        }

        super.onChildDraw(c, recyclerView, viewHolder, clampedDX, dY, actionState, isCurrentlyActive)

        val top = itemView.top.toFloat()
        val bottom = itemView.bottom.toFloat()

        if (clampedDX < 0) {
            // LEFT swipe: [체크][삭제] (오른쪽에서 튀어나옴)
            val right = itemView.right.toFloat()

            val doneRect = RectF(right - buttonWidthPx * 2, top, right - buttonWidthPx, bottom)
            val delRect  = RectF(right - buttonWidthPx, top, right, bottom)

            // 배경
            paint.color = Color.parseColor("#DBF1F0")
            c.drawRect(doneRect, paint)

            paint.color = Color.parseColor("#ECCACA")
            c.drawRect(delRect, paint)

            // ✅ 흰색 구분 박스(세로 라인)
            val dividerW = dp(10f)
            val dividerRect = RectF(
                doneRect.right - dividerW / 2f,
                top,
                doneRect.right + dividerW / 2f,
                bottom
            )
            paint.color = Color.WHITE
            c.drawRect(dividerRect, paint)

            // 아이콘 (왼쪽=check_circle, 오른쪽=delete)
            drawCenterIcon(
                canvas = c,
                rect = doneRect,
                drawableRes = R.drawable.check_circle,
                tint = Color.parseColor("#525252"),
                sizePx = dp(20f)
            )
            drawCenterIcon(
                canvas = c,
                rect = delRect,
                drawableRes = R.drawable.delete,
                tint = Color.parseColor("#525252"),
                sizePx = dp(17f)
            )

            buttonRects.add(doneRect to { onDone(pos) })
            buttonRects.add(delRect to { onDelete(pos) })

        } else if (clampedDX > 0) {
            // RIGHT swipe: [보관] (왼쪽에서 튀어나옴)
            val left = itemView.left.toFloat()
            val archiveRect = RectF(left, top, left + buttonWidthPx, bottom)

            paint.color = Color.parseColor("#BFD5F8")
            c.drawRect(archiveRect, paint)

            // 아이콘 = inbox
            drawCenterIcon(
                canvas = c,
                rect = archiveRect,
                drawableRes = R.drawable.inbox,
                tint = Color.parseColor("#525252"),
                sizePx = dp(21f)
            )

            buttonRects.add(archiveRect to { onArchive(pos) })
        }
    }

    private fun drawCenterIcon(
        canvas: Canvas,
        rect: RectF,
        drawableRes: Int,
        tint: Int,
        sizePx: Int
    ) {
        val d = AppCompatResources.getDrawable(context, drawableRes) ?: return
        val wrapped: Drawable = DrawableCompat.wrap(d).mutate()
        DrawableCompat.setTint(wrapped, tint)

        val cx = rect.centerX()
        val cy = rect.centerY()

        val half = (sizePx / 2f)
        val left = (cx - half).roundToInt()
        val top = (cy - half).roundToInt()
        val right = (cx + half).roundToInt()
        val bottom = (cy + half).roundToInt()

        wrapped.setBounds(left, top, right, bottom)
        wrapped.draw(canvas)
    }

    private fun dp(value: Float): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            context.resources.displayMetrics
        ).roundToInt()
}
