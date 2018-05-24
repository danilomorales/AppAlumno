package com.example.danil.appalumno.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.danil.appalumno.Modelo.Alumno;

import java.util.ArrayList;
import java.util.List;

public class DaoAlumno extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "DBAlumno";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_alumno = "alumno";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "nombre";
    private static final String KEY_APELLIDO = "apellido";
    private static final String KEY_DNI = "dni";
    public static String TAG = "tag";
    private static final String CREATE_TABLE_Alumno = "CREATE TABLE "
            + TABLE_alumno + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
            + KEY_DNI + " TEXT," + KEY_APELLIDO + " TEXT);";
    private SQLiteDatabase db = null;

    public DaoAlumno(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_Alumno);
    }

    public void open() {
        try { // Abrimos la base de datos en modo escritura
            db = this.getWritableDatabase();
        } catch (Exception e) {
            throw new RuntimeException("Error al abrir la base de datos.");
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS; " + CREATE_TABLE_Alumno);
        onCreate(db);
    }

    public long addContactoDetail(Alumno student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getNombre());
        values.put(KEY_DNI, student.getDni());
        values.put(KEY_APELLIDO, student.getApellido());
        long insert = db.insert(TABLE_alumno, null, values);
        return insert;
    }

    public int updateEntry(Alumno student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getNombre());
        values.put(KEY_DNI, student.getDni());
        values.put(KEY_APELLIDO, student.getApellido());
        return db.update(TABLE_alumno, values, KEY_ID + " = ?",
                new String[]{String.valueOf(student.id)});
    }

    public void deleteEntry(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_alumno, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public Alumno getStudent(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_alumno + " WHERE "
                + KEY_ID + " = " + id;
        Log.d(TAG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        Alumno students = new Alumno();
        students.id = c.getInt(c.getColumnIndex(KEY_ID));
        students.dni = c.getString(c.getColumnIndex(KEY_DNI));
        students.nombre = c.getString(c.getColumnIndex(KEY_NAME));
        students.apellido = c.getString(c.getColumnIndex(KEY_APELLIDO));

        return students;
    }

    public List<Alumno> searchContact(String name) {
        List<Alumno> studentsArrayList = new ArrayList<Alumno>();
        String selectQuery = "SELECT  * FROM " + TABLE_alumno + " WHERE "+KEY_NAME+" LIKE '%"+name+"%'";
        Log.d(TAG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Alumno students = new Alumno();
                students.id = c.getInt(c.getColumnIndex(KEY_ID));
                students.dni = c.getString(c.getColumnIndex(KEY_DNI));
                students.nombre = c.getString(c.getColumnIndex(KEY_NAME));
                students.apellido = c.getString(c.getColumnIndex(KEY_APELLIDO));
                studentsArrayList.add(students);
            } while (c.moveToNext());
        }
        return studentsArrayList;
    }

    public List<Alumno> getAllStudentsList() {
        List<Alumno> studentsArrayList = new ArrayList<Alumno>();
        String selectQuery = "SELECT  * FROM " + TABLE_alumno;
        Log.d(TAG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Alumno students = new Alumno();
                students.id = c.getInt(c.getColumnIndex(KEY_ID));
                students.dni = c.getString(c.getColumnIndex(KEY_DNI));
                students.nombre = c.getString(c.getColumnIndex(KEY_NAME));
                students.apellido = c.getString(c.getColumnIndex(KEY_APELLIDO));
                studentsArrayList.add(students);
            } while (c.moveToNext());
        }
        return studentsArrayList;
    }
}

