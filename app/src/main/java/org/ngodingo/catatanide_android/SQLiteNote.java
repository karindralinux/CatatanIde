package org.ngodingo.catatanide_android;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karindra Linux on 03/12/2017.
 */

public class SQLiteNote extends SQLiteOpenHelper {

    private static final String TAG = SQLiteNote.class.getSimpleName();

    // Versi Database
    private static final int DATABASE_VERSION = 1;

    // Nama Database
    private static final String DATABASE_NAME = "note_db";

    // Nama Tabel
    private static final String TABLE_NOTE = "table_note";

    //Kolom - kolom tabel
    public static final String KEY_ID = "_id";
    public static final String KEY_WAKTU = "waktu";
    public static final String KEY_JUDUL = "judul";
    public static final String KEY_ISI = "isi";

    public SQLiteNote(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //nge-create databasenya
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //harus kita perhatikan penulisan kodenya karena ini sensitive-case
        String CREATE_NOTE_TABLE = "CREATE TABLE " + TABLE_NOTE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_WAKTU + " TEXT,"
                + KEY_ISI + " TEXT,"
                + KEY_JUDUL + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + TABLE_NOTE);
        onCreate(sqLiteDatabase);
    }

    public void addData(String waktu, String judul, String isi) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (!CheckData(judul)) {
            // kalau iya, berarti nge-add data baru
            // ketika database nya kosong
            ContentValues values = new ContentValues();
            values.put(KEY_WAKTU, waktu);
            values.put(KEY_JUDUL, judul);
            values.put(KEY_ISI, isi);

            db.insert(TABLE_NOTE,null,values);
        }

        //kalau udah ada isinya databasenya
        else {
            try {
                db.beginTransaction();
                db.execSQL("UPDATE " + TABLE_NOTE + " SET waktu='" + waktu + "', judul='" + judul + "', isi='" + isi +
                "' WHERE judul='" + judul + "'");

            } finally {
                db.endTransaction();
            }
        }

        db.close();
    }

    public List<ItemList> getData() {

        //ngambil data untuk di set di list

        List<ItemList> noteDetail = new ArrayList<>();
        String USER_DETAIL_SELECT_QUERY = "SELECT * FROM " + TABLE_NOTE;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_DETAIL_SELECT_QUERY,null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    ItemList itemList = new ItemList();
                    itemList.waktu = cursor.getString(cursor.getColumnIndex(KEY_WAKTU));
                    itemList.judul = cursor.getString(cursor.getColumnIndex(KEY_JUDUL));
                    itemList.isi = cursor.getString(cursor.getColumnIndex(KEY_ISI));

                    noteDetail.add(itemList);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "error kie");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return noteDetail;

    }

    //ngecek databasenya ada isinya atau enggak ya
    private boolean CheckData(String judul) {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NOTE + " WHERE judul='" + judul + "'";
        Cursor cursor = sqldb.rawQuery(query,null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }

        cursor.close();
        return true;
    }

    public void deleteItemSelected(String waktu) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            db.execSQL("DELETE from " + TABLE_NOTE + " WHERE waktu ='" + waktu + "'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d(TAG, "Error ketika mau menghapus item");

        } finally {
            db.endTransaction();
        }

        //kalau udah selesai bikin balik lagi ke MainActivity.java

    }



}
