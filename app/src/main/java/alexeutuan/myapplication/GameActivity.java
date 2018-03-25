package alexeutuan.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {

    int true_returnes;
    int false_returnes;

    Button ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

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

    }
}
