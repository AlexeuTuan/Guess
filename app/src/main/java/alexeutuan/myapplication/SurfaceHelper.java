package alexeutuan.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import org.json.JSONArray;

public class SurfaceHelper extends SurfaceView implements SurfaceHolder.Callback {

    DrawThread drawThread;
    ClassPoint cPoint = new ClassPoint();
    JSONArray coordinateX = new JSONArray();
    JSONArray coordinateY = new JSONArray();

    public SurfaceHelper(Context context,AttributeSet attributeSet) {
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
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            drawThread.path.moveTo(event.getX(), event.getY());  //  определение координат пальца
        }
        //  если палец движется по экрану
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            drawThread.path.lineTo(event.getX(), event.getY());  //  рисование линии по координатам
        }

        // Получаем касание и передаем в массив
        cPoint.x[cPoint.i] = (int) event.getX();
        cPoint.y[cPoint.i] = (int) event.getY();

        // Элементы массива добавляем в JsonArray
        coordinateX.put(cPoint.x[cPoint.i]);
        coordinateY.put(cPoint.y[cPoint.i]);
        Log.d("TAG", "touchEv " + cPoint.i + " " + cPoint.x[cPoint.i] + " " + cPoint.y[cPoint.i]);

        cPoint.i++; // переход к следующему элементу массива
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
            } catch (InterruptedException e) {
                //
            }
        }
    }

    public class DrawThread extends Thread {

        // public int colorPoint = 5;
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
