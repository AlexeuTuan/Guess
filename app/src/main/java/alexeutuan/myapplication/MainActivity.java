package alexeutuan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button ok;
    private EditText imageName;
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
        imageName = (EditText) findViewById(R.id.imageName);
        ok = (Button) findViewById(R.id.bOkMain);
    }
    public void onClick(View v) throws JSONException {
        switch(v.getId()) {
            case R.id.bOkMain:
                if(imageName.getText().toString().equals("")) {
                    Toast.makeText(this,"Введите название!",Toast.LENGTH_SHORT).show();
                } else {
                    if(sHelper.arr.equals("")) {
                        Toast.makeText(this,"Нарисуйте картинку!",Toast.LENGTH_SHORT).show();
                    } else {
                        sHelper.drawThread.running = false;
                        Log.d("Debug", sHelper.arr);
                        RequestTask requestTask = new RequestTask(sHelper.arr + "," + imageName.getText());
                        // requestTask.execute();
                        setResult(2);
                        finish();
                    }
                }
                break;
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
