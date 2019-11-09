package com.svprdga.infinitescrollsample.presentation.ui.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.MotionEventCompat;

import com.svprdga.infinitescrollsample.R;
import com.svprdga.infinitescrollsample.presentation.ui.extra.Design;

public class SliderSelector extends View {

    // *************************************** CONSTANTS *************************************** //

    public static final int THEME_LIGHT                             = 0;
    public static final int THEME_DARK                              = 1;

    private static final int INVALID_POINTER_ID                     = -1;

    private static final int STATE_NORMAL                           = 0;
    private static final int STATE_MOVING                           = 1;
    private static final int STATE_RETURNING_LEFT                   = 2;
    private static final int STATE_RETURNING_RIGHT                  = 3;
    private static final int STATE_SELECTED_LEFT                    = 4;
    private static final int STATE_SELECTED_RIGHT                   = 5;

    private static final int STATEB_FADE_IN                                 = 0;
    private static final int STATEB_ARROWS                                  = 1;
    private static final int STATEB_STILL                                   = 2;
    private static final int STATEB_FADE_OUT                                = 3;
    private static final int STATEB_HIDE                                    = 4;

    // Use this value to select how much percentage of the road has to be made in order to
    // select an option. If 1, the finger needs to perform the complete movement from the origin
    // to the center of the selected side.
    public static final float SELECTION_SENSIVITY                   = 0.6f;

    // *************************************** MEASURES **************************************** //

    private static final int SIZE_RADIUS_ORIGIN_PX                  = 28;
    private static float SIZE_RADIUS_ORIGIN;
    private static final int COLOR_BACKGROUND_ORIGIN                = Color.parseColor("#ED317E");
    private static final int COLOR_TEXT_ORIGIN = Color.parseColor("#FFFFFF");
    private static final int SIZE_RADIUS_OPTION_PX                  = 21;
    private static float SIZE_RADIUS_OPTION;
    private static final int COLOR_BACKGROUND_OPTION                = Color.parseColor("#0D8AC7");

    private static final int SIZE_DISTANCE_ORIGIN_OPTION_PX         = 104;
    private static float SIZE_DISTANCE_ORIGIN_OPTION;

    private static final int SIZE_HEIGHT_BACKGROUND_SHAPE_PX        = 42;
    private static float SIZE_HEIGHT_BACKGROUND_SHAPE;

    private static final int SIZE_MARGIN_LEFT_RIGHT_BACKGROUND_SHAPE_PX = 16;
    private static float SIZE_MARGIN_LEFT_RIGHT_BACKGROUND_SHAPE;
    private static final int SIZE_MARGIN_BOTTOM_BACKGROUND_SHAPE_PX = 23;
    private static float SIZE_MARGIN_BOTTOM_BACKGROUND_SHAPE;
    private static final int SIZE_MARGIN_TOP_BACKGROUND_SHAPE_PX    = 23;
    private static float SIZE_MARGIN_TOP_BACKGROUND_SHAPE;

    private static final float DISABLED_ALPHA_AMOUNT                = 0.3f;

    private static final int COLOR_BACKGROUND_SHAPE               = Color.parseColor("#FFFFFF");
    private static final float ALPHA_MAX_BACKGROUND_SHAPE           = 0.38f;

    private static final int SIZE_FONT_TEXT_PX                      = 12;
    private static float SIZE_FONT_TEXT;
    private static final int COLOR_TEXT_DARK                        = Color.parseColor("#FFFFFF");
    private static final int COLOR_TEXT_LIGHT                       = Color.parseColor("#FFFFFF");
    private static final int COLOR_OPT_TEXT_DISABLED                = Color.parseColor("#61FFFFFF");

    /**
     * Use this constant to adjust the icons size in relation with the option circle.
     */
    private static final float SIZE_OPTION_ICONS_MODIFIER           = 0.6f;

    private static final int RETURNING_FRACTION_PX                  = 20;
    private static float RETURNING_FRACTION;

    /**
     * This is the time that the fade in/out of the background lasts, in seconds.
     */
    private static final float TIME_FADE_BACKGROUND                 = 0.1f;

    /**
     * Time in seconds which lasts the arrows travel.
     */
    private static final float TIME_ARROW_TRAVEL                    = 0.3f;

    /**
     * Framerate of the getView.
     * This param is 'ignored' in the way that the onDraw() method contains an invalidate() call,
     * paying no attention at this value, however, android seems to redraw each 60 FPS so...
     */
    @Deprecated
    private static final int FPS                                    = 60;

    private static final float ALPHA_MAX_ARROWS                     = 0.8f;

    private static final float ALPHA_MAX_TEXT_LIGHT                 = 0.54f;

    private static final float SIZE_ORIGIN_Y_OFFSET_PX              = 5f;
    private static float SIZE_ORIGIN_Y_OFFSET;

    private static final float RADIUS_ORIGIN_SHADOW_BLUR_PX         = 9f;
    private static float RADIUS_ORIGIN_SHADOW_BLUR;

    private static final int SIZE_RADIUS_ORIGIN_SHADOW_PX           = 34; // origin is 28
    private static float SIZE_RADIUS_ORIGIN_SHADOW;
    private static final int COLOR_ORIGIN_END_SHADOW            = Color.parseColor("#7F000000");
    private static final int COLOR_ORIGIN_BEG_SHADOW            = Color.parseColor("#00000000");

    private static final float BORDER_WIDTH_ORIGIN_SQUARE_PX        = 1.5f;
    private static float BORDER_WIDTH_ORIGIN_SQUARE;
    private static final float BORDER_ROUND_ORIGIN_SQUARE_PX        = 3f;
    private static float BORDER_ROUND_ORIGIN_SQUARE;

    // ****************************************** VARS ***************************************** //

    private int mWidth;
    private int mHeight;
    private Bitmap mLeftDarkOptionBitmap;
    private Bitmap mRightDarkOptionBitmap;
    private Bitmap mLeftLightOptionBitmap;
    private Bitmap mRightLightOptionBitmap;
    private Bitmap mArrowsRightBitmap;
    private Bitmap mArrowsLeftBitmap;
    private boolean mIsLeftOptionEnabled;
    private boolean mIsRightOptionEnabled;
    private Canvas mCanvas;
    private float mOriginInitX;
    private float mOriginInitY;
    private float mOptionsInitY;
    private float mLeftOptionInitX;
    private float mRightOptionInitX;
    private int mCurrentTheme = THEME_DARK;
    private long mInterval;
    private int mState = STATE_NORMAL;
    private int mActivePointerId = INVALID_POINTER_ID;
    private float mCurrentFingerX;
    boolean mHasMoved = false;
    boolean mFirstMove = true;
    private float mPreviousFingerX;
    private float mReturningFraction;
    private boolean mInvalidate = false;
    private OnOptionSelectedListener mListener;
    private boolean mOptionSelectedNotified = false;

    private Paint mOriginPaint;
    private Paint mOptionPaint;
    private Paint mOriginTextPaint;
    private Paint mLeftTextPaint;
    private Paint mRightTextPaint;
    private Paint mIconPaint;
    private Paint mBackgroundShapePaint;
    private Paint mShadowPaint;
    private Paint mOriginBorderPaint;

    private String mOriginText;
    private String mLeftOptionText;
    private String mRightOptionText;

    private int mIntervalsFadeNumber = 0; // Number of intervals to hide in/out.
    private int mCurrentIntervalFade = 1; // Current count to mFramesFadeNumber.
    private int mStateB = STATEB_HIDE;

    private float mLeftArrowCloseOptionX; // X pos of the left arrow in his maximum close to option.
    private float mRightArrowCloseOptionX;
    private float mLeftArrowCloseOriginX; // X pos of the left arrow in this closest distance to origin.
    private float mRightArrowCloseOriginX;
    private float mArrowsY;
    private int mFramesArrowTravelNumber = 0; // Number of frames to the arrows to travel.

    private float mCurrentLeftArrowX;
    private float mCurrentRightArrowX;
    private float mArrowTravelIncrement; // How much to increment to the travel of the arrow.

    private float mTextInitY;
    private float mLeftTextInitX;
    private float mRightTextInitX;
    private float mOptionsTextHalfHeight;

    private float mLeftOptionSelectableAreaLeftLimit;
    private float mLeftOptionSelectableAreaRightLimit;
    private float mRightOptionSelectableAreaLeftLimit;
    private float mRightOptionSelectableAreaRightLimit;

    private boolean mUserTouch = false;
    private Path mBackgroundShapePath;
    private boolean mStartedFromOrigin = false;
    private boolean mBackgroundDissapearNotified = false;

    // ************************************** CONSTRUCTORS ************************************* //

    /**
     * Instance a new End Session Slide Slector 2.
     * @param context
     * @param attrs
     * @param leftDarkOptIcon Specify the icon to draw on the left option in dark theme.
     * @param leftLightOptIcon Specify the icon to draw on the left option in light theme.
     * @param leftOptEnabled Enable/disable the left option.
     * @param rightDarkOptIcon Specify the icon to draw on the right option in dark theme.
     * @param rightLightOptIcon Specify the icon to draw on the right option in light theme.
     * @param rightOptEnabled Enable/disable the right option.
     * @param originText Text to put in the origin circle.
     * @param leftOptionText Text to show on the left option.
     * @param rightOptionText Text to show on the right option.
     * @param theme Specify the theme: <br/>
     *              <ul>
     *                  <li>{@link SliderSelector#THEME_LIGHT}</li>
     *                  <li>{@link SliderSelector#THEME_DARK}</li>
     *              </ul>
     */
    public SliderSelector(Context context, AttributeSet attrs, int leftDarkOptIcon,
                          int leftLightOptIcon, boolean leftOptEnabled,
                          int rightDarkOptIcon, int rightLightOptIcon,
                          boolean rightOptEnabled, String originText,
                          String leftOptionText, String rightOptionText,
                          int theme){
        super(context, attrs);

        // Calculate sizes in dps.
        DisplayMetrics dp = getResources().getDisplayMetrics();

        SIZE_RADIUS_ORIGIN = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, SIZE_RADIUS_ORIGIN_PX, dp);
        SIZE_RADIUS_OPTION = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, SIZE_RADIUS_OPTION_PX, dp);
        SIZE_HEIGHT_BACKGROUND_SHAPE = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, SIZE_HEIGHT_BACKGROUND_SHAPE_PX, dp);
        SIZE_MARGIN_LEFT_RIGHT_BACKGROUND_SHAPE = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, SIZE_MARGIN_LEFT_RIGHT_BACKGROUND_SHAPE_PX, dp);
        SIZE_FONT_TEXT = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, SIZE_FONT_TEXT_PX, dp);
        SIZE_MARGIN_BOTTOM_BACKGROUND_SHAPE = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, SIZE_MARGIN_BOTTOM_BACKGROUND_SHAPE_PX, dp);
        SIZE_MARGIN_TOP_BACKGROUND_SHAPE = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, SIZE_MARGIN_TOP_BACKGROUND_SHAPE_PX, dp);
        SIZE_DISTANCE_ORIGIN_OPTION = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, SIZE_DISTANCE_ORIGIN_OPTION_PX, dp);
        RETURNING_FRACTION = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, RETURNING_FRACTION_PX, dp);
        SIZE_ORIGIN_Y_OFFSET = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, SIZE_ORIGIN_Y_OFFSET_PX, dp);
        RADIUS_ORIGIN_SHADOW_BLUR = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, RADIUS_ORIGIN_SHADOW_BLUR_PX, dp);
        SIZE_RADIUS_ORIGIN_SHADOW = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, SIZE_RADIUS_ORIGIN_SHADOW_PX, dp);
        BORDER_WIDTH_ORIGIN_SQUARE = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, BORDER_WIDTH_ORIGIN_SQUARE_PX, dp);
        BORDER_ROUND_ORIGIN_SQUARE = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, BORDER_ROUND_ORIGIN_SQUARE_PX, dp);

        // Get drawables.
        mLeftDarkOptionBitmap = BitmapFactory.decodeResource(
                context.getResources(), leftDarkOptIcon);
        mRightDarkOptionBitmap = BitmapFactory.decodeResource(
                context.getResources(), rightDarkOptIcon);
        mLeftLightOptionBitmap = BitmapFactory.decodeResource(
                context.getResources(), leftLightOptIcon);
        mRightLightOptionBitmap = BitmapFactory.decodeResource(
                context.getResources(), rightLightOptIcon);

        mArrowsRightBitmap = BitmapFactory.decodeResource(
                context.getResources(), R.mipmap.ic_arrows);

        // Create left arrows.
        Matrix matrix = new Matrix();
        matrix.postRotate(180);
        mArrowsLeftBitmap = Bitmap.createBitmap(
                mArrowsRightBitmap, 0, 0, mArrowsRightBitmap.getWidth(),
                mArrowsRightBitmap.getHeight(), matrix, true);

        mIsLeftOptionEnabled = leftOptEnabled;
        mIsRightOptionEnabled = rightOptEnabled;
        mOriginText = originText;
        mLeftOptionText = leftOptionText;
        mRightOptionText = rightOptionText;
        mCurrentTheme = theme;

        // Configure paintings.
        mOriginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOriginPaint.setColor(COLOR_BACKGROUND_ORIGIN);

        mOptionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOptionPaint.setColor(COLOR_BACKGROUND_OPTION);

        mOriginTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOriginTextPaint.setColor(COLOR_TEXT_DARK);
        mOriginTextPaint.setTextSize(SIZE_FONT_TEXT);

        mLeftTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLeftTextPaint.setColor(COLOR_TEXT_DARK);
        mLeftTextPaint.setTextSize(SIZE_FONT_TEXT);

        mRightTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRightTextPaint.setColor(COLOR_TEXT_DARK);
        mRightTextPaint.setTextSize(SIZE_FONT_TEXT);

        mIconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mShadowPaint = new Paint();

        mBackgroundShapePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundShapePaint.setColor(COLOR_BACKGROUND_SHAPE);

        mOriginBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOriginBorderPaint.setStrokeWidth(BORDER_WIDTH_ORIGIN_SQUARE);
        mOriginBorderPaint.setStyle(Paint.Style.STROKE);
        mOriginBorderPaint.setColor(COLOR_TEXT_ORIGIN);

        // Interval between drawings based on the desired framerate.
        mInterval = (long) ((1f / FPS) * 1000f);

        // Calculate how much frames need the background shape to fade in/out.
        mIntervalsFadeNumber = Math.round((1000 * TIME_FADE_BACKGROUND) / mInterval);
        mFramesArrowTravelNumber = (int) (FPS * TIME_ARROW_TRAVEL);

    }

    // ************************************* PUBLIC METHODS ************************************ //

    @Override
    public boolean onTouchEvent(MotionEvent event){

        if (mState == STATE_SELECTED_LEFT || mState == STATE_SELECTED_RIGHT){
            return false;
        }

        final int action = MotionEventCompat.getActionMasked(event);

        switch(action){
            case MotionEvent.ACTION_DOWN:

                mHasMoved = false;
                mFirstMove = true;

                final int pointerIndex = MotionEventCompat.getActionIndex(event);
                final float x = MotionEventCompat.getX(event, pointerIndex);

                // If the click is over the origin, begin the translation.
                float leftLimit = mOriginInitX - SIZE_RADIUS_ORIGIN;
                float rightLimit = mOriginInitX + SIZE_RADIUS_ORIGIN;

                if (x > leftLimit && x < rightLimit){
                    mState = STATE_MOVING;
                    mStartedFromOrigin = true;
                }

                mActivePointerId = MotionEventCompat.getPointerId(event, 0);

                // Background stuff.
                if (mStateB == STATEB_HIDE){
                    mStateB = STATEB_FADE_IN;
                    invalidate();
                }

                notifyUserTouch(true);

                break;
            case MotionEvent.ACTION_MOVE:

                if (mState == STATE_MOVING){

                    final int pointerIndex0 =
                            MotionEventCompat.findPointerIndex(event, mActivePointerId);

                    mCurrentFingerX = MotionEventCompat.getX(event, pointerIndex0);

                    // Options enabled/disabled.
                    if (!mIsLeftOptionEnabled){
                        if (mCurrentFingerX < mOriginInitX){
                            mCurrentFingerX = mOriginInitX;
                        }
                    }

                    if (!mIsRightOptionEnabled){
                        if (mCurrentFingerX > mOriginInitX){
                            mCurrentFingerX = mOriginInitX;
                        }
                    }

                    if (mFirstMove){
                        mPreviousFingerX = mCurrentFingerX;
                        mFirstMove = false;
                    }

                    if (!mHasMoved){
                        if (mCurrentFingerX != mPreviousFingerX) mHasMoved = true;
                    } else {
                        mPreviousFingerX = mCurrentFingerX;
                    }

                    invalidate();

                }

                break;
            case MotionEvent.ACTION_UP:

                if (!mIsLeftOptionEnabled && mListener != null && mStartedFromOrigin){

                    final int pointerIndex0 =
                            MotionEventCompat.findPointerIndex(event, mActivePointerId);
                    float x0 = MotionEventCompat.getX(event, pointerIndex0);

                    if (x0 > mLeftOptionSelectableAreaLeftLimit &&
                            x0 < mLeftOptionSelectableAreaRightLimit){
                        mListener.onLeftDisabledOptionSelected();
                    }

                }

                if (!mIsRightOptionEnabled && mListener != null && mStartedFromOrigin){
                    final int pointerIndex0 =
                            MotionEventCompat.findPointerIndex(event, mActivePointerId);
                    float x0 = MotionEventCompat.getX(event, pointerIndex0);

                    if (x0 > mRightOptionSelectableAreaLeftLimit &&
                            x0 < mRightOptionSelectableAreaRightLimit){
                        mListener.onRightDisabledOptionSelected();
                    }
                }

                mActivePointerId = INVALID_POINTER_ID;
                decideCurrentState();
                invalidate();

                notifyUserTouch(false);
                mStartedFromOrigin = false;

                break;
            case MotionEvent.ACTION_CANCEL:

                mActivePointerId = INVALID_POINTER_ID;

                decideCurrentState();
                invalidate();

                mCurrentFingerX = mOriginInitX;
//                mUserTouch = false;
                notifyUserTouch(false);
                mStartedFromOrigin = false;

                break;
            case MotionEvent.ACTION_POINTER_UP:

                final int pointerIndex1 = MotionEventCompat.getActionIndex(event);
                final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex1);

                if (pointerId == mActivePointerId){
                    // This is our active pointer going up.
                    decideCurrentState();
                    mCurrentFingerX = mOriginInitX;
                    invalidate();
                    notifyUserTouch(false);
                    mStartedFromOrigin = false;
                }

                break;
        }

        return true;
    }

    // *********************************** PROTECTED METHODS *********************************** //

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);

        this.setMeasuredDimension(parentWidth, parentHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Save this object measures.
        mHeight = parentHeight;
        mWidth = parentWidth;

        // Calcule some values.
        mOriginInitX = mWidth / 2;
        mOriginInitY = SIZE_MARGIN_TOP_BACKGROUND_SHAPE + (SIZE_HEIGHT_BACKGROUND_SHAPE / 2);

        mOptionsInitY = mOriginInitY;
        mLeftOptionInitX = mOriginInitX - SIZE_DISTANCE_ORIGIN_OPTION;
        mRightOptionInitX = mOriginInitX + SIZE_DISTANCE_ORIGIN_OPTION;

        mCurrentFingerX = mOriginInitX;
        mReturningFraction = (mOriginInitX - mLeftOptionInitX) / RETURNING_FRACTION;

        mLeftArrowCloseOptionX = mLeftOptionInitX - SIZE_RADIUS_OPTION;
        mRightArrowCloseOptionX = mRightOptionInitX + SIZE_RADIUS_OPTION;
        mLeftArrowCloseOriginX = mOriginInitX - SIZE_RADIUS_ORIGIN - mArrowsRightBitmap.getWidth();
        mRightArrowCloseOriginX = mOriginInitX + SIZE_RADIUS_ORIGIN;
        mArrowsY = (SIZE_MARGIN_TOP_BACKGROUND_SHAPE + (SIZE_HEIGHT_BACKGROUND_SHAPE / 2))
                - (mArrowsRightBitmap.getHeight() / 2f);
        mCurrentLeftArrowX = mLeftArrowCloseOriginX;
        mCurrentRightArrowX = mRightArrowCloseOriginX;
        mArrowTravelIncrement =
                (mLeftArrowCloseOriginX - mLeftArrowCloseOptionX) / mFramesArrowTravelNumber;

        Rect rect = new Rect();
        mLeftTextPaint.getTextBounds(mLeftOptionText, 0, mLeftOptionText.length(), rect);
        mOptionsTextHalfHeight = rect.height() / 2f;

        mTextInitY = (SIZE_MARGIN_TOP_BACKGROUND_SHAPE + SIZE_HEIGHT_BACKGROUND_SHAPE + (
                SIZE_MARGIN_BOTTOM_BACKGROUND_SHAPE / 2)) + mOptionsTextHalfHeight;
        mLeftTextInitX = mLeftOptionInitX;
        mRightTextInitX = mRightOptionInitX;

        mLeftOptionSelectableAreaLeftLimit = 0f;
        mLeftOptionSelectableAreaRightLimit = mOriginInitX - ((mOriginInitX - mLeftOptionInitX) * SELECTION_SENSIVITY);

        mRightOptionSelectableAreaRightLimit = mWidth;
        mRightOptionSelectableAreaLeftLimit = mRightOptionInitX - ((mRightOptionInitX - mOriginInitX) * SELECTION_SENSIVITY);

        // Origin paint.
        mShadowPaint.setDither(true);

        // Background path.
        mBackgroundShapePath = new Path();
        mBackgroundShapePath.setFillType(Path.FillType.EVEN_ODD);

        float radius = SIZE_HEIGHT_BACKGROUND_SHAPE / 2f;

        RectF rectF = new RectF();
        rectF.left = SIZE_MARGIN_LEFT_RIGHT_BACKGROUND_SHAPE;
        rectF.right = mWidth - SIZE_MARGIN_LEFT_RIGHT_BACKGROUND_SHAPE;
        rectF.top = SIZE_MARGIN_TOP_BACKGROUND_SHAPE;
        rectF.bottom = SIZE_MARGIN_TOP_BACKGROUND_SHAPE + SIZE_HEIGHT_BACKGROUND_SHAPE;

        float[] array = new float[] {radius,radius,radius,radius,radius,radius,radius,radius};

        mBackgroundShapePath.addRoundRect(rectF, array, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        mCanvas = canvas;

        // Background, arrows and text stuff.
        drawArrowsAndBackground();

        // Origin & options.
        drawOriginAndOptions();

        if (mInvalidate){
            invalidate();
            mInvalidate = false;
        }

    }

    // ************************************ PRIVATE METHODS ************************************ //

    /**
     * This method handles the drawing of the origin and the options.
     */
    private void drawOriginAndOptions(){

        switch(mState){
            case STATE_NORMAL:

                drawOrigin(1f);
                drawOptions(0f);

                break;
            case STATE_SELECTED_LEFT:

                drawOrigin(0f);
                drawOptions(-1f);

                if (mListener != null && !mOptionSelectedNotified){
                    mListener.onLeftOptionSelected();
                    mOptionSelectedNotified = true;
                }

                break;
            case STATE_SELECTED_RIGHT:

                drawOrigin(0f);
                drawOptions(1f);

                if (mListener != null && !mOptionSelectedNotified){
                    mListener.onRightOptionSelected();
                    mOptionSelectedNotified = true;
                }

                break;
            case STATE_RETURNING_LEFT:

                if (mCurrentFingerX >= mOriginInitX){
                    mState = STATE_NORMAL;
                    drawOrigin(1);
                    drawOptions(0f);

                    return;
                }

                mCurrentFingerX += mReturningFraction;

                if (mCurrentFingerX == mOriginInitX){
                    mState = STATE_NORMAL;
                }

                final float returnPerc = getOptionsCompletedPercentage(mCurrentFingerX);

                drawOptions(returnPerc);
                drawOriginAccordingToOptions(returnPerc);

                mInvalidate = true;

                break;
            case STATE_RETURNING_RIGHT:

                if (mCurrentFingerX <= mOriginInitX){
                    mState = STATE_NORMAL;
                    drawOrigin(1);
                    drawOptions(0f);

                    return;
                }

                mCurrentFingerX -= mReturningFraction;

                if (mCurrentFingerX == mOriginInitX){
                    mState = STATE_NORMAL;
                }

                final float returnPerc_ = getOptionsCompletedPercentage(mCurrentFingerX);

                drawOptions(returnPerc_);
                drawOriginAccordingToOptions(returnPerc_);

                mInvalidate = true;

                break;
            case STATE_MOVING:

                final float completedPercentage =
                        getOptionsCompletedPercentage(mCurrentFingerX);

                drawOptions(completedPercentage);
                drawOriginAccordingToOptions(completedPercentage);

                break;
        }

    }

    /**
     * Same as {@link SliderSelector#drawOrigin(float)} but providing a value from -1
     * to 1, being -1 and 1 the smallest version of origin and 0 the biggest one.
     * @param percentage
     */
    private void drawOriginAccordingToOptions(float percentage){

        if (percentage == 0f){
            drawOrigin(1);
        } else if (percentage > 0f){
            drawOrigin(1 - percentage);
        } else {
            drawOrigin(1 - Math.abs(percentage));
        }

    }

    /**
     * Draw the origin.
     * @param percentage Specify how much this circle has to be drawn, from 0 to 1.
     */
    private void drawOrigin(float percentage){

        float alpha = 255 * percentage;

        // Shadow.
        float radiusShadow = SIZE_RADIUS_ORIGIN_SHADOW * percentage;

        if (radiusShadow <= 0) {
            radiusShadow = 1;
        }

        RadialGradient gradient = new RadialGradient(
                mOriginInitX,
                mOriginInitY + SIZE_ORIGIN_Y_OFFSET,
                radiusShadow,
                COLOR_ORIGIN_END_SHADOW,
                COLOR_ORIGIN_BEG_SHADOW,
                Shader.TileMode.CLAMP);

        mShadowPaint.setShader(gradient);
        mShadowPaint.setAlpha((int) alpha);

        mCanvas.drawCircle(
                mOriginInitX, mOriginInitY + SIZE_ORIGIN_Y_OFFSET, radiusShadow, mShadowPaint);

        // Circle.
        float radius = SIZE_RADIUS_ORIGIN * percentage;
        mOriginPaint.setAlpha((int) alpha);

        mCanvas.drawCircle(mOriginInitX, mOriginInitY, radius, mOriginPaint);

        int textSize = getResources()
                .getDimensionPixelSize(R.dimen.slide_selector_text_size);
        float textSizeF = (float) textSize;
        textSizeF *= percentage;
        mOriginTextPaint.setTextSize(textSizeF);

        float originTextWidth = mOriginTextPaint.measureText(mOriginText);
        float initTextX = mOriginInitX - originTextWidth / 2;

        mOriginTextPaint.setAlpha((int) alpha);
        mOriginTextPaint.setTextAlign(Paint.Align.LEFT);

        Rect rect = new Rect();
        mOriginTextPaint.getTextBounds(mOriginText, 0, mOriginText.length(), rect);

        float initTextY = mOriginInitY + rect.height() / 2;
        mOriginTextPaint.setColor(COLOR_TEXT_DARK);

        mCanvas.drawText(mOriginText, initTextX , initTextY, mOriginTextPaint);

        // White border.
        float left = mOriginInitX - (radius * 0.66f);
        float right = mOriginInitX + (radius * 0.66f);
        float top = mOriginInitY - (radius * 0.4f);
        float bottom = mOriginInitY + (radius * 0.4f);

        mOriginBorderPaint.setStrokeWidth(BORDER_WIDTH_ORIGIN_SQUARE * percentage);

        RectF rectF = new RectF(left, top, right, bottom);

        mOriginBorderPaint.setAlpha((int) alpha);

        mCanvas.drawRoundRect(
                rectF, BORDER_ROUND_ORIGIN_SQUARE, BORDER_ROUND_ORIGIN_SQUARE, mOriginBorderPaint);

    }

    /**
     * Draw the options.
     * @param percentage Specify how much this circles needs to be drawn, from -1 to 1.
     *                   Range from -1 to 0 will affect left option while right option will be
     *                   remain still, and the opposite occurs with ranges from 0 to 1.
     */
    private void drawOptions(float percentage){

        // Calculates real percentages (positive value from 0 to 1) for each circle.
        float realPercentageLeftOption;
        float realPercentageRightOption;

        if (percentage < 0){

            realPercentageLeftOption = 1 - (1 - Math.abs(percentage));
            realPercentageRightOption = 0;

        } else if (percentage > 0){

            realPercentageLeftOption = 0;
            realPercentageRightOption = percentage;

        } else {

            // Percentage must be 0.
            realPercentageLeftOption = 0;
            realPercentageRightOption = 0;
        }

        // Circles.
        float leftCircleRadius = SIZE_RADIUS_OPTION * realPercentageLeftOption;
        float rightCircleRadius = SIZE_RADIUS_OPTION * realPercentageRightOption;

        if (realPercentageLeftOption > 0 && mIsLeftOptionEnabled){

            float alpha = realPercentageLeftOption * 255f;

            mOptionPaint.setAlpha((int) alpha);
            mCanvas.drawCircle(mLeftOptionInitX, mOptionsInitY, leftCircleRadius, mOptionPaint);
        }

        if (realPercentageRightOption > 0 && mIsRightOptionEnabled){

            float alpha = realPercentageRightOption * 255f;

            mOptionPaint.setAlpha((int) alpha);
            mCanvas.drawCircle(mRightOptionInitX, mOptionsInitY, rightCircleRadius, mOptionPaint);
        }

        // Left icon.
        float left = mLeftOptionInitX - (SIZE_RADIUS_OPTION * SIZE_OPTION_ICONS_MODIFIER);
        float right = mLeftOptionInitX + (SIZE_RADIUS_OPTION * SIZE_OPTION_ICONS_MODIFIER);
        float height = (right - left);
        float top = mOptionsInitY - height / 2;
        float bottom = mOptionsInitY + height / 2;

        RectF leftRectF = new RectF(left, top, right, bottom);

        if (mIsLeftOptionEnabled){

            if (mCurrentTheme == THEME_DARK) {

                mIconPaint.setAlpha(Design.Icon.Dark.ALPHA_ACTIVE_INT);
                mCanvas.drawBitmap(mLeftDarkOptionBitmap, null, leftRectF, mIconPaint);
            } else {

                mIconPaint.setAlpha((int) (realPercentageLeftOption * 255f));
                mCanvas.drawBitmap(mLeftDarkOptionBitmap, null, leftRectF, mIconPaint);

                mIconPaint.setAlpha((int) ((1 - realPercentageLeftOption) * Design.Icon.Light.ALPHA_ACTIVE_INT));
                mCanvas.drawBitmap(mLeftLightOptionBitmap, null ,leftRectF, mIconPaint);
            }

        } else {

            mIconPaint.setAlpha((int) (255f * Design.Icon.Dark.ALPHA_INACTIVE_FLOAT));

            if (mCurrentTheme == THEME_DARK){
                mCanvas.drawBitmap(mLeftLightOptionBitmap, null, leftRectF, mIconPaint);
            } else {
                mCanvas.drawBitmap(mLeftDarkOptionBitmap, null, leftRectF, mIconPaint);
            }
        }

        // Right icon.
        left = mRightOptionInitX - (SIZE_RADIUS_OPTION * SIZE_OPTION_ICONS_MODIFIER);
        right = mRightOptionInitX + (SIZE_RADIUS_OPTION * SIZE_OPTION_ICONS_MODIFIER);

        RectF rightRectF = new RectF(left, top, right, bottom);

        if (mIsRightOptionEnabled){

            if (mCurrentTheme == THEME_DARK) {

                mIconPaint.setAlpha(Design.Icon.Dark.ALPHA_ACTIVE_INT);
                mCanvas.drawBitmap(mRightDarkOptionBitmap, null, rightRectF, mIconPaint);
            } else {

                mIconPaint.setAlpha((int) (realPercentageRightOption * 255f));
                mCanvas.drawBitmap(mRightDarkOptionBitmap, null, rightRectF, mIconPaint);

                mIconPaint.setAlpha((int) ((1 - realPercentageRightOption) * Design.Icon.Light.ALPHA_ACTIVE_INT));
                mCanvas.drawBitmap(mRightLightOptionBitmap, null, rightRectF, mIconPaint);
            }

        } else {

            mIconPaint.setAlpha((int) (255f * DISABLED_ALPHA_AMOUNT));

            if (mCurrentTheme == THEME_LIGHT){
                mCanvas.drawBitmap(mRightLightOptionBitmap, null, rightRectF, mIconPaint);
            } else {
                mCanvas.drawBitmap(mRightDarkOptionBitmap, null, rightRectF, mIconPaint);
            }

        }

    }

    /**
     * Decide the state of the getView based on multiple factors when the click is released.
     */
    private void decideCurrentState(){

        if (mCurrentFingerX <= (((mOriginInitX - mLeftOptionInitX) * (1 - SELECTION_SENSIVITY)))
                + mLeftOptionInitX){
            mState = STATE_SELECTED_LEFT;
        } else if (mCurrentFingerX >= (((mRightOptionInitX - mOriginInitX) * SELECTION_SENSIVITY))
                + mOriginInitX){
            mState = STATE_SELECTED_RIGHT;
        } else if (mCurrentFingerX < mOriginInitX){
            mState = STATE_RETURNING_LEFT;
        } else if (mCurrentFingerX > mOriginInitX){
            mState = STATE_RETURNING_RIGHT;
        } else {
            mState = STATE_NORMAL;
        }
    }

    /**
     * This methods returns a value within -1 and 1 which represents the current x position (the
     * finger of the user or the current x position when the trail is returning to origin) in
     * relation with the completed path; being -1 the center of the left option, 0 the center of
     * the origin, and 1 the center of the right option.
     * @param xPosition
     * @return
     */
    private float getOptionsCompletedPercentage(float xPosition){

        float percentage;

        if (xPosition <= mLeftOptionInitX){
            percentage = -1f;
        } else if (xPosition >= mRightOptionInitX){
            percentage = 1f;
        } else {

            float totalDistance = mRightOptionInitX - mLeftOptionInitX;
            float halfDistance = totalDistance / 2;
            float center = mRightOptionInitX - halfDistance;

            if (center == xPosition){
                percentage = 0f;
            } else if (xPosition > center){

                float coveredDistance = mRightOptionInitX - xPosition;
                percentage = ((100 / halfDistance) * coveredDistance) / 100;
                percentage = 1 - percentage;

            } else {

                float coveredDistance = mOriginInitX - xPosition;
                percentage = - (((100 / halfDistance) * coveredDistance) / 100);

            }

        }

        return percentage;
    }

    /**
     * This method handles the drawing of the background shape, the arrows and the text below
     * the options.
     */
    private void drawArrowsAndBackground(){

        float alpha = 0f;

        switch(mStateB){
            case STATEB_FADE_IN:

                if (mCurrentIntervalFade > mIntervalsFadeNumber){
                    mStateB = STATEB_ARROWS;
                    mCurrentIntervalFade = 1;
                    drawArrowsAndBackground();
                    return;
                }

                alpha = (1f / mIntervalsFadeNumber) * mCurrentIntervalFade;
                drawBackgroundShape(alpha);
                drawOptionsText(alpha);
                mCurrentIntervalFade++;
                mBackgroundDissapearNotified = false;
                mInvalidate = true;

                break;
            case STATEB_ARROWS:

                drawBackgroundShape(1f);
                drawOptionsText(1f);
                boolean arrowTravelEnd = drawArrows();
                if (arrowTravelEnd) {
                    mStateB = STATEB_STILL;
                    mInvalidate = true;
                    return;
                } else {
                    mInvalidate = true;
                }

                break;
            case STATEB_STILL:

                if (mState == STATE_MOVING ||
                        mState == STATE_SELECTED_LEFT ||
                        mState == STATE_SELECTED_RIGHT){
                    drawBackgroundShape(1f);
                    drawOptionsText(1f);
                } else {
                    mStateB = STATEB_FADE_OUT;
                    drawArrowsAndBackground();
                    return;
                }

                break;
            case STATEB_FADE_OUT:

                if (mCurrentIntervalFade > mIntervalsFadeNumber){
                    mStateB = STATEB_HIDE;
                    mCurrentIntervalFade = 1;
                    drawArrowsAndBackground();
                    return;
                }

                if (!mBackgroundDissapearNotified && mListener != null){
                    mListener.onBackgroundDissapear();
                    mBackgroundDissapearNotified = true;
                }

                alpha = 1 - ((1f / mIntervalsFadeNumber) * mCurrentIntervalFade);
                drawBackgroundShape(alpha);
                drawOptionsText(alpha);
                mCurrentIntervalFade++;

                mInvalidate = true;

                break;
            case STATEB_HIDE:

                // Reset vars.
                mCurrentIntervalFade = 1;

                break;
        }

    }

    /**
     * Draw the background shape.
     * @param alphaPercentage Specify with how much alpha level the shape must be drawn, from 0 to 1.
     */
    private void drawBackgroundShape(float alphaPercentage){

        // Alpha level.
        int alpha = (int) ((255 * ALPHA_MAX_BACKGROUND_SHAPE) * alphaPercentage);
        mBackgroundShapePaint.setAlpha(alpha);

        mCanvas.drawPath(mBackgroundShapePath, mBackgroundShapePaint);

    }

    /**
     * Draw the arrows.
     * @return Return false if the arrows are traveling, true if the arrows have ended its travel.
     */
    private boolean drawArrows(){

        float distance;
        float remain;
        float alphaF;

        if (mIsLeftOptionEnabled){
            distance = mLeftArrowCloseOriginX - mLeftArrowCloseOptionX;
            remain = mCurrentLeftArrowX - mLeftArrowCloseOptionX;
            alphaF = ((remain / distance)) * ALPHA_MAX_ARROWS;
        } else {
            distance = mRightArrowCloseOptionX - mRightArrowCloseOriginX;
            remain = mRightArrowCloseOptionX - mCurrentRightArrowX;
            alphaF = ((remain / distance)) * ALPHA_MAX_ARROWS;
        }


        int alpha = (int) (alphaF * 255);

        mIconPaint.setAlpha(alpha);

        // Left arrow.
        if (mIsLeftOptionEnabled){

            float left = mCurrentLeftArrowX;
            float right = left + mArrowsLeftBitmap.getWidth();
            float top = mArrowsY;
            float bottom = top + mArrowsLeftBitmap.getHeight();

            RectF rectF = new RectF(left, top, right, bottom);

            mCanvas.drawBitmap(mArrowsLeftBitmap, null, rectF, mIconPaint);

            mCurrentLeftArrowX -= mArrowTravelIncrement;

        }

        // Right arrow.
        if (mIsRightOptionEnabled){

            float left = mCurrentRightArrowX;
            float right = left + mArrowsRightBitmap.getWidth();
            float top = mArrowsY;
            float bottom = top + mArrowsLeftBitmap.getHeight();

            RectF rectF = new RectF(left, top, right, bottom);

            mCanvas.drawBitmap(mArrowsRightBitmap, null, rectF, mIconPaint);

            mCurrentRightArrowX += mArrowTravelIncrement;

        }

        if (mIsLeftOptionEnabled) {

            if (mCurrentLeftArrowX <= mLeftArrowCloseOptionX){
                // Reset.
                mCurrentLeftArrowX = mLeftArrowCloseOriginX;
                mCurrentRightArrowX = mRightArrowCloseOriginX;
                return true;
            } else {
                return false;
            }
        } else {

            if (mCurrentRightArrowX >= mRightArrowCloseOptionX){
                // Reset.
                mCurrentLeftArrowX = mLeftArrowCloseOriginX;
                mCurrentRightArrowX = mRightArrowCloseOriginX;
                return true;
            } else {
                return false;
            }
        }

    }

    /**
     * Draw the text below the options.
     * @param alphaPercentage Specify how much to show, from 0 to 1.
     */
    private void drawOptionsText(float alphaPercentage){

        if (mCurrentTheme == THEME_DARK){
            mLeftTextPaint.setColor(COLOR_TEXT_DARK);
            mLeftTextPaint.setAlpha((int) (255f * alphaPercentage));

            mRightTextPaint.setColor(COLOR_TEXT_DARK);
            mRightTextPaint.setAlpha((int) (255f * alphaPercentage));
        } else {
            mLeftTextPaint.setColor(COLOR_TEXT_LIGHT);
            mLeftTextPaint.setAlpha((int) ((255f * alphaPercentage) * ALPHA_MAX_TEXT_LIGHT));

            mRightTextPaint.setColor(COLOR_TEXT_LIGHT);
            mRightTextPaint.setAlpha((int) ((255f * alphaPercentage) * ALPHA_MAX_TEXT_LIGHT));
        }

        mLeftTextPaint.setTextAlign(Paint.Align.CENTER);
        mLeftTextPaint.setTextSize(SIZE_FONT_TEXT);

        mRightTextPaint.setTextAlign(Paint.Align.CENTER);
        mRightTextPaint.setTextSize(SIZE_FONT_TEXT);

        if (!mIsLeftOptionEnabled){
            mLeftTextPaint.setAlpha((int) (mLeftTextPaint.getAlpha() * DISABLED_ALPHA_AMOUNT));
        }

        if (!mIsRightOptionEnabled){
            mRightTextPaint.setAlpha((int) (mLeftTextPaint.getAlpha() * DISABLED_ALPHA_AMOUNT));
        }

        mCanvas.drawText(mLeftOptionText, mLeftTextInitX, mTextInitY, mLeftTextPaint);
        mCanvas.drawText(mRightOptionText, mRightTextInitX, mTextInitY, mRightTextPaint);

    }

    public interface OnOptionSelectedListener {

        /**
         * The user has selected the left option.
         */
        void onLeftOptionSelected();

        /**
         * The user has selected the right option.
         */
        void onRightOptionSelected();

        /**
         * The user has made an attempt to select the left option which is disabled.
         */
        void onLeftDisabledOptionSelected();

        /**
         * The user has made an attempt to select the right option which is disabled.
         */
        void onRightDisabledOptionSelected();

        /**
         * The user has begun to touch the getView.
         */
        void onTouch();

        /**
         * The user has released the finger from the getView.
         */
        void onTouchRelease();

        /**
         * The white background starts to dissapear.
         */
        void onBackgroundDissapear();

    }

    /**
     * Set an {@link SliderSelector.OnOptionSelectedListener} in order to receive event
     * callbacks.
     * @param listener
     */
    public void setOnOptionSelectedListener(OnOptionSelectedListener listener){
        mListener = listener;
    }

    /**
     * Reset the getView to its original state.
     */
    public void reset(){

        mState = STATE_NORMAL;
        mStateB = STATEB_HIDE;

        mCurrentLeftArrowX = mLeftArrowCloseOriginX;
        mCurrentRightArrowX = mRightArrowCloseOriginX;

        mOptionSelectedNotified = false;

        invalidate();

    }

    /**
     * Set a theme to the widget.
     * @param theme Theme to set, can be either: <br/>
     *              <ul>
     *                  <li>{@link SliderSelector#THEME_LIGHT}</li>
     *                  <li>{@link SliderSelector#THEME_DARK}</li>
     *              </ul>
     */
    public void setTheme(int theme){
        mCurrentTheme = theme;
        invalidate();
    }

    /**
     * Notify the touch of the user if needed.
     * @param isTouching
     */
    private void notifyUserTouch(boolean isTouching){

        if ((mUserTouch != isTouching) && mListener != null){

            if (isTouching) mListener.onTouch();
            else mListener.onTouchRelease();

            mUserTouch = isTouching;
        }

    }

}
