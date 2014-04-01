package com.marduc812.databaseexample.example;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marduc on 3/31/14.
 */
public class ShoplistAdapter {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_ITEM = "item";
    public static final String KEY_COMMENT = "comment";

    private DBHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "Shopping";
    private static final String SQLITE_TABLE = "List";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;



    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_ITEM + "," +
                    KEY_COMMENT + ");";


    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public ShoplistAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public ShoplistAdapter open() throws SQLException {
        mDbHelper = new DBHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public long CreateItem(String item, String comment) {

        mDb = mDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ITEM, item);
        contentValues.put(KEY_COMMENT, comment);
        long data = mDb.insert(SQLITE_TABLE, null, contentValues);
        return data;
    }

    public boolean DeleteAllItems() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null , null);
        return doneDelete > 0;

    }

    public Cursor fletchByItem(String inputText) throws SQLException {

        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,KEY_ITEM, KEY_COMMENT},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID,KEY_ITEM, KEY_COMMENT},
                    KEY_ITEM + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllItems() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,KEY_ITEM,KEY_COMMENT},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertItem() {




    }

}
