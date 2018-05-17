package alexeutuan.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GameActivity extends AppCompatActivity {

    int true_returnes;
    int false_returnes;
    String arr;

    private SurfaceHelperGame sHelperGame;
    Button ok;
    EditText imageName;
    Button accept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        sHelperGame = (SurfaceHelperGame) findViewById(R.id.surfaceView2);

        true_returnes = getIntent().getIntExtra("true_returnes",0);
        false_returnes = getIntent().getIntExtra("false_returnes",0);


        ok = (Button) findViewById(R.id.bOk);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("true_returnes",true_returnes);
                i.putExtra("false_returnes", false_returnes);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        accept = (Button) findViewById(R.id.bAccept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestTask requestTask = new RequestTask("2");
                requestTask.execute();
                arr = requestTask.response;
                sHelperGame.arr = arr;
            }
        });

        imageName = (EditText) findViewById(R.id.imageNameRes);


        RequestTask requestTask = new RequestTask("2");
        requestTask.execute();
        arr = requestTask.response;
        sHelperGame.arr = arr;
    }
    @Override
    protected void onStop() {
        super.onStop();
        sHelperGame.drawThread.running = false;
    }
    @Override
    protected void onPause() {
        super.onPause();
        sHelperGame.drawThread.running = false;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sHelperGame.drawThread.running = false;
    }
}
