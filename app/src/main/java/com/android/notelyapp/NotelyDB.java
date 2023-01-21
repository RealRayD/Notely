package com.android.notelyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class NotelyDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "notelyDB";
    private static final String TABLE_NAME = "notelyTable";

    public NotelyDB(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    private static final String THE_ID = "id";
    private static final String THE_TITLE = "title";
    private static final String THE_CONTENT = "content";
    private static final String THE_DATE = "date";
    private static final String THE_TIME = "time";
    @Override
    public void onCreate(SQLiteDatabase db) {
         String createDb = "CREATE TABLE " +TABLE_NAME+ " ("+
                 THE_ID+" INTEGER PRIMARY KEY,"+
                 THE_TITLE+" TEXT,"+
                 THE_CONTENT+" TEXT,"+
                 THE_DATE+" TEXT,"+
                 THE_TIME+" TEXT"
                 +" )";
         db.execSQL(createDb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion >= newVersion)
            return;

        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }

    public long addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(THE_TITLE, note.getTitle());
        v.put(THE_CONTENT, note.getContent());
        v.put(THE_DATE, note.getDate());
        v.put(THE_TIME, note.getTime());
        long ID = db.insert(TABLE_NAME,null,v);
        return  ID;
    }

    public Note getNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] {THE_ID, THE_TITLE, THE_CONTENT, THE_DATE, THE_TIME};
       Cursor cursor=  db.query(TABLE_NAME, query,THE_ID+ "=?", new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        return new Note(
                Long.parseLong(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
    }

    public List<Note> getAllNotes(){
        List<Note> allNotes = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME+ " ORDER BY " +THE_ID+ " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do {
                Note note = new Note();
                note.setId(Long.parseLong(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setDate(cursor.getString(3));
                note.setTime(cursor.getString(4));
                allNotes.add(note);
            } while (cursor.moveToNext());
        }

        return allNotes;

    }

    public int editNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(THE_TITLE, note.getTitle());
        c.put(THE_CONTENT, note.getContent());
        c.put(THE_DATE, note.getDate());
        c.put(THE_TIME, note.getTime());
        return db.update(TABLE_NAME, c,THE_ID+ "=?", new String[]{String.valueOf(note.getId())});
    }

    void deleteNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,THE_ID+ "=?", new String[]{String.valueOf(id)});
        db.close();
    }

}
