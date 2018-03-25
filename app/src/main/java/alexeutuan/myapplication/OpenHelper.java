package alexeutuan.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "GameDB";
    static final String TABLE_NAME = "ProfilesTable";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_TAG_PROFILE = "TAG";
    static final String COLUMN_TRUE_RETURNES = "TRUEQ";
    static final String COLUMN_FALSE_RETURNES = "FALSEQ";
    private static final int DATABASE_VERSION = 1;

    OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TAG_PROFILE + " INTEGER NOT NULL, " +
                COLUMN_FALSE_RETURNES + " INTEGER NOT NULL, " +
                COLUMN_TRUE_RETURNES + " INTEGER NOT NULL);";
        db.execSQL(query);
        db.execSQL("INSERT INTO "+ TABLE_NAME + "(" + COLUMN_TAG_PROFILE + "," + COLUMN_FALSE_RETURNES + "," + COLUMN_TRUE_RETURNES + ") VALUES (1,0,0)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(db);
    }
}
