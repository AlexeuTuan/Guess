package alexeutuan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button red,blue,yellow,green,black,white;
    private Button ok;
    private SurfaceHelper sHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sHelper = (SurfaceHelper) findViewById(R.id.surfaceViewMain);
        sHelper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sHelper.onTouchEvent(event);
                return true;
            }
        });
        ok = (Button) findViewById(R.id.bOkMain);
        red = (Button) findViewById(R.id.bred);
        blue = (Button) findViewById(R.id.bblue);
        yellow = (Button) findViewById(R.id.byellow);
        green = (Button) findViewById(R.id.bgreen);
        black = (Button) findViewById(R.id.bblack);
        white = (Button) findViewById(R.id.bwhite);
    }
    public void onClick(View v) throws JSONException {
        switch(v.getId()) {
            case R.id.bOkMain: sHelper.drawThread.running = false;
                JSONObject jsonFile = new JSONObject();
                jsonFile.put("coordinateX",sHelper.coordinateX);
                jsonFile.put("coordinateY", sHelper.coordinateY);
                setResult(2);
                finish();
                break;
            /*
            case R.id.bred: sHelper.drawThread.colorPoint = 1; break;
            case R.id.bblue: sHelper.drawThread.colorPoint = 2; break;
            case R.id.byellow: sHelper.drawThread.colorPoint = 3; break;
            case R.id.bgreen: sHelper.drawThread.colorPoint = 4; break;
            case R.id.bblack: sHelper.drawThread.colorPoint = 5; break;
            case R.id.bwhite: sHelper.drawThread.colorPoint = 6; break;
            */
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        sHelper.drawThread.running = false;
    }
    @Override
    protected void onPause() {
        super.onPause();
        sHelper.drawThread.running = false;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sHelper.drawThread.running = false;
    }
}
