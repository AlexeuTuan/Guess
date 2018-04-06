package alexeutuan.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AuthActivity extends AppCompatActivity {

    Button play,delProgr,addPicture;
    TextView trues,falses;


    OpenHelper openHelper;
    SQLiteDatabase db;
    Cursor c;

    int true_returnes;
    int false_returnes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        openHelper = new OpenHelper(this);
        db = openHelper.getWritableDatabase();
        c = db.rawQuery("SELECT * FROM " + openHelper.TABLE_NAME, null);
        c.moveToFirst();
        play = (Button) findViewById(R.id.bPlay);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTrueFalse();
                Intent i = new Intent(AuthActivity.this, GameActivity.class);
                i.putExtra("true_returnes", true_returnes);
                i.putExtra("false_returnes", false_returnes);
                startActivityForResult(i, 1);
            }
        });
        delProgr = (Button) findViewById(R.id.bDelProgress);
        delProgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.execSQL("DROP TABLE " + openHelper.TABLE_NAME);
                openHelper.onCreate(db);
                setTrueFalse();
            }
        });
        addPicture = (Button) findViewById(R.id.bAddPicture);
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AuthActivity.this,MainActivity.class);
                startActivityForResult(i,2);
            }
        });
        trues = (TextView) findViewById(R.id.tvTrue);
        falses = (TextView) findViewById(R.id.tvFalse);
        setTrueFalse();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                db.execSQL("UPDATE " + openHelper.TABLE_NAME + " SET " + openHelper.COLUMN_FALSE_RETURNES
                        + " = " + data.getIntExtra("false_returnes", 1) +
                        " WHERE " + openHelper.COLUMN_TAG_PROFILE + " = 1");
                db.execSQL("UPDATE " + openHelper.TABLE_NAME + " SET " + openHelper.COLUMN_TRUE_RETURNES
                        + " = " + data.getIntExtra("true_returnes",1) +
                        " WHERE " + openHelper.COLUMN_TAG_PROFILE + " = 1");

                setTrueFalse();
                break;
            case 2:
                Toast.makeText(this,"Спасибо за вклад в банк картинок!",Toast.LENGTH_SHORT).show();
                // addPicture.setEnabled(false);
                break;
        }
    }

    public void setTrueFalse() {
        c = db.rawQuery("SELECT * FROM " + openHelper.TABLE_NAME, null);
        c.moveToFirst();
        true_returnes = c.getInt(c.getColumnIndex(openHelper.COLUMN_TRUE_RETURNES));
        false_returnes = c.getInt(c.getColumnIndex(openHelper.COLUMN_FALSE_RETURNES));
        trues.setText(true_returnes + " trues");
        falses.setText(false_returnes + " falses");
    }
}
