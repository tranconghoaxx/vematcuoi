package tdc.edu.vn.smile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class SmileyView extends View {
    private Paint mCirclePaint;
    private Paint mEyeAndMouthPaint;
    private float mCenterX;
    private float mCenterY;
    private float mRadius;
    private RectF mArcBounds = new RectF();
    Path path = new Path();
    Paint paint = new Paint();

    public SmileyView(Context context) {
        this(context, null);
    }

    public SmileyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmileyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaints();
    }

    private void initPaints() {

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.YELLOW);
        mEyeAndMouthPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mEyeAndMouthPaint.setStyle(Paint.Style.STROKE);
        mEyeAndMouthPaint.setStrokeWidth(8 * getResources().getDisplayMetrics().density);
        mEyeAndMouthPaint.setStrokeCap(Paint.Cap.ROUND);
        mEyeAndMouthPaint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(16 * getResources().getDisplayMetrics().density);



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(w, h);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mCirclePaint);
        // draw eyes
        float eyeRadius = mRadius / 5f;
        float eyeOffsetX = mRadius / 3f;
        float eyeOffsetY = mRadius / 3f;
        canvas.drawCircle(mCenterX - eyeOffsetX, mCenterY - eyeOffsetY, eyeRadius, mEyeAndMouthPaint);
        canvas.drawCircle(mCenterX + eyeOffsetX, mCenterY - eyeOffsetY, eyeRadius, mEyeAndMouthPaint);
        // draw mouth
        float mouthInset = mRadius / 3f;
        mArcBounds.set(mouthInset, mouthInset, mRadius * 2 - mouthInset, mRadius * 2 - mouthInset);
        canvas.drawArc(mArcBounds, 45f, 90f, false, mEyeAndMouthPaint);
        canvas.drawPath(path,paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w / 2f;
        mCenterY = h / 2f;
        mRadius = Math.min(w, h) / 2f;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
                path.addCircle(eventX, eventY, 10, Path.Direction.CW);
                break;
            case MotionEvent.ACTION_CANCEL: {
                path.addCircle(eventX, eventY, 10, Path.Direction.CW);
                break;
            }
            default:
                return false;
        }
        invalidate();
        return true;
    }
}
