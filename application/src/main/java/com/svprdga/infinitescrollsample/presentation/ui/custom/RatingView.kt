package com.svprdga.infinitescrollsample.presentation.ui.custom

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewPropertyAnimator
import android.widget.RelativeLayout
import com.svprdga.infinitescrollsample.R
import kotlinx.android.synthetic.main.custom_rating_view.view.*

private const val TIME_ROTATE_DURATION = 100L
private const val ANGLE_ROTATION = 20f

class RatingView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    // ****************************************** VARS ***************************************** //

    private var angle = 0F

    // ************************************** CONSTRUCTORS ************************************* //

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_rating_view, this, true)
    }

    // ************************************* PUBLIC METHODS ************************************ //

    /**
     * Make the view to do its particular animation.
     */
    fun doAnimation() {
        doAngleAnimation(ANGLE_ROTATION) {
            doAngleAnimation(-ANGLE_ROTATION) {
                doAngleAnimation(ANGLE_ROTATION) {
                    doAngleAnimation(-ANGLE_ROTATION) {
                        doAngleAnimation(ANGLE_ROTATION) {
                            doAngleAnimation(-ANGLE_ROTATION) {
                                doAngleAnimation(-angle) {

                                }

                            }
                        }
                    }
                }
            }
        }.start()
    }

    // ************************************ PRIVATE METHODS ************************************ //

    private fun doAngleAnimation(angle: Float, func: () -> Unit): ViewPropertyAnimator {

        this.angle += angle

        return imageView.animate()
            .rotation(angle)
            .setDuration(TIME_ROTATE_DURATION)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                    // Not needed.
                }
                override fun onAnimationEnd(p0: Animator?) {
                    func()
                }

                override fun onAnimationCancel(p0: Animator?) {
                    // Not needed.
                }
                override fun onAnimationRepeat(p0: Animator?) {
                    // Not needed.
                }
            })
    }

}
