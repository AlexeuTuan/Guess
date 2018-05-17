package alexeutuan.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceHelperGame extends SurfaceView implements SurfaceHolder.Callback {

    String arr;
    String[] ss = arr.split(",");

    DrawThread drawThread;
    public SurfaceHelperGame(Context context,AttributeSet attributeSet) {
        super(context,attributeSet);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getContext(),getHolder());
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // изменение размеров SurfaceView
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {/* ... */}
        }
    }

    public void drawPath() {
        for (int i = 1; i <ss.length-1 ; i += 2) {
            drawThread.path.moveTo(Float.valueOf(ss[i]),Float.valueOf(ss[i+1]));
            drawThread.path.lineTo(Float.valueOf(ss[i]),Float.valueOf(ss[i+1]));
        }
    }

    public class DrawThread extends Thread {

        Path path = new Path();
        private SurfaceHolder surfaceHolder;
        volatile boolean running = true;//флаг для остановки потока


        public DrawThread(Context context, SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void requestStop() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {
                Paint paint = new Paint();
                Canvas canvas = surfaceHolder.lockCanvas();
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(10);
                if (canvas != null) {
                    try {
                        canvas.drawColor(Color.WHITE);
                        canvas.drawPath(path, paint);
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}