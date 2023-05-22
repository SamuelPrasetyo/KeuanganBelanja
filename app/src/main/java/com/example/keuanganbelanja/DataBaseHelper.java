package com.example.keuanganbelanja;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    //konstanta, digunakan dalam sintaks query, agar tidak mengulang ulang pengetikan dalam sintaks query tsb.
    public static final String TABLE_ANGGARAN = "anggaran";
    public static final String KOLOM_NAMA_BARANG = "nama_barang";
    public static final String KOLOM_HARGA_BARANG = "harga_barang";
    public static final String KOLOM_ID = "id";

    //constructor
    public DataBaseHelper(@Nullable Context context) {
        super(context, "dataBelanja.db", null, 1);
    }

    //this is called the first time a database is accessed. There should be code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //buat statement query, yang akan men-generate table di sistem SQLite
        String CreateTableStatement = "create table " + TABLE_ANGGARAN + "(" + KOLOM_ID + " integer primary key autoincrement, " + KOLOM_NAMA_BARANG + " TEXT, " + KOLOM_HARGA_BARANG + " INT)";

        sqLiteDatabase.execSQL(CreateTableStatement);
    }


    //this is called if the database version number changes. It prevents previous users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // DAO (Data Access Object) Style in software design

    //method addOne, method untuk menambah satu "item" ke dalam database
    //addOne akan melihat input baru dari ModelBelanja
    public boolean addOne(ModelBelanja modelBelanja){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //method Content Value, sama kayak associative array dalam Php, yaitu array yang key nya merupakan strings bukan angka
        ContentValues cv = new ContentValues();

        //menambah value ke dalam Content Value, dengan method put(), parameter: Key (String), value
        cv.put(KOLOM_NAMA_BARANG, modelBelanja.getNama());
        cv.put(KOLOM_HARGA_BARANG, modelBelanja.getHarga());

        //long merupakan tipe variabel yang sangat sukses, jika kita mendapatkan angka positif maka sukses, jika dapat angka negatif maka gagal
        long insert = sqLiteDatabase.insert(TABLE_ANGGARAN, null, cv);
        if (insert == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean deleteOne(ModelBelanja modelBelanja){
        // cari modelBelanja di dalam database. If ketemu, hapus dan return true.
        // jika tidak ketemu, return false

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String queryString = "DELETE FROM " + TABLE_ANGGARAN + " WHERE " + KOLOM_ID + " = " + modelBelanja.getId();

        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }

    //define properties apa yang akan ada ketika di-return, dalam kasus ini, kita me-return semuanya
    //arraylist     1:00:20      https://youtu.be/312RhjfetP8


    // membuat method getEveryone(),
    // berguna untuk mengambil data belanja dari dalam database, id, namaBarang, hargaBarang
    public List<ModelBelanja> getEveryone() {
        List<ModelBelanja> returnList = new ArrayList<>();

        // ambil data dari database
        String queryString = "SELECT * FROM " + TABLE_ANGGARAN;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        //cursor di dalam sqlite, adalah kumpulan hasil dari sintaks query yang dijalankan
        //cursor adalah hasil

        if(cursor.moveToFirst()){
            //loop melalui cursor (hasil) dan buat objek list belanja baru, tampung objek baru ke dalam returnList
            //1:04:23    https://youtu.be/312RhjfetP8
            do{
                int idBarang = cursor.getInt(0);
                String namaBarang = cursor.getString(1);
                int hargaBarang = cursor.getInt(2);

                ModelBelanja newBelanja = new ModelBelanja(idBarang, namaBarang, hargaBarang);
                returnList.add(newBelanja);

            }while(cursor.moveToNext());
            //selama kita bisa berpindah ke data / baris selanjutnya dalam database
            //selama ada baris baru di dalam database, maka semua di dalam "do" akan terkesekusi

        }else{
            //failure
        }
        cursor.close();
        sqLiteDatabase.close();
        return returnList;
    }
}
