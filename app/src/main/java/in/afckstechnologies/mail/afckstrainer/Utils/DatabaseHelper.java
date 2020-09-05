package in.afckstechnologies.mail.afckstrainer.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    //Constants for Database name, table name, and column names
    public static final String DB_NAME = "AFCKSDB";
    public static final String TABLE_NAME = "vwDepartments";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DEPT_NAME = "dept_name";
    public static final String COLUMN_GROUP_USER_NAME = "group_user_name";
    public static final String COLUMN_STATUS = "status";

    //database version
    private static final int DB_VERSION = 1;

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //creating the database
    @Override
    public void onCreate(SQLiteDatabase db) {
       /* String sql = "CREATE TABLE " + TABLE_NAME
                + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME +
                " VARCHAR, " + COLUMN_STATUS +
                " TINYINT);";
        db.execSQL(sql);*/

        String sql = "CREATE TABLE " + TABLE_NAME
                + "(" + COLUMN_ID +
                " VARCHAR , " + COLUMN_DEPT_NAME +
                " VARCHAR , " + COLUMN_GROUP_USER_NAME +
                " VARCHAR, " + COLUMN_STATUS +
                " TINYINT);";
        db.execSQL(sql);

    }

    //upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    /*
     * This method is taking two arguments
     * first one is the name that is to be saved
     * second one is the status
     * 0 means the name is synced with the server
     * 1 means the name is not synced with the server
     * */
    public boolean addName(String deviceId, String name, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, deviceId);
        contentValues.put(COLUMN_DEPT_NAME, name);
        contentValues.put(COLUMN_GROUP_USER_NAME, name);
        contentValues.put(COLUMN_STATUS, status);
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public boolean addNameSync(String id, String deptname, String groupname, int status) {
        if (getDuplicates(id)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, id);
            contentValues.put(COLUMN_DEPT_NAME, deptname);
            contentValues.put(COLUMN_GROUP_USER_NAME, groupname);
            contentValues.put(COLUMN_STATUS, status);
            db.insert(TABLE_NAME, null, contentValues);
            db.close();
        }
        return true;
    }

    public boolean addNameUpdateSync(String deviceId, String name, int status) {
        if (!getDuplicates(deviceId))  {
            String id1 = "\"" + deviceId + "\"";
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_STATUS, 0);
            db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id1, null);
            db.close();
        } else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, deviceId);

            contentValues.put(COLUMN_STATUS, 0);
            db.insert(TABLE_NAME, null, contentValues);
            db.close();
        }
        return true;
    }

    public boolean getDuplicates(String id) {
        // get readable database as we are not inserting anything
        boolean flag = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_DEPT_NAME, COLUMN_STATUS},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            flag = true;
        } else {

        }
        cursor.close();
        return flag;
    }

    /*
     * This method taking two arguments
     * first one is the id of the name for which
     * we have to update the sync status
     * and the second one is the status that will be changed
     * */
    public boolean updateNameStatus(String id, int status) {
        String id1 = "\"" + id + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id1, null);
        db.close();
        return true;
    }

    /*
     * this method will give us all the name stored in sqlite
     * */
    public Cursor getNames(String search) {
        SQLiteDatabase db = this.getReadableDatabase();
        //String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_STATUS + " <> 3 ORDER BY " + COLUMN_ID + " ASC;";
        String sql = "SELECT * FROM " + TABLE_NAME + " where " + COLUMN_DEPT_NAME + " like '%" + search + "%' or "+ COLUMN_GROUP_USER_NAME + " like '%" + search + "%'";
       // "Select id,dept_name from vwDepartments where dept_name Like '%$Prefixtext%' or group_user_name Like '%$Prefixtext%'"
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    /*
     * this method is for getting all the unsynced name
     * so that we can sync it with database
     * */
    public Cursor getUnsyncedNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_STATUS + " <> 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

  /*  public int updateNote(Name note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, note.getName());
        values.put(COLUMN_STATUS, 2);
        // updating row
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(Name note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, 3);
      *//*  db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});*//*
        db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public void deletePNote(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});

        db.close();
    }
*/
    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }
}
