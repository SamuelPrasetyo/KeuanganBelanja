package com.example.keuanganbelanja;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //reference to buttons and other controls on the layout
    Button btn_add, btn_viewAll;
    EditText et_nama, et_harga;
    ListView lv_listBelanja;
    DataBaseHelper dataBaseHelper;

    // ArrayAdapter = perantara antara database dengan UI, menjadi jembatan yang membawa data dari database ke UI
    // berguna untuk tombol viewAll
    //1:10:20,      https://youtu.be/312RhjfetP8
    ArrayAdapter belanjaArrayAdapter;


    //ON CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        et_harga = findViewById(R.id.et_harga);
        et_nama = findViewById(R.id.et_nama);
        lv_listBelanja = findViewById(R.id.lv_listBelanja);

        // membuat object untuk arrayAdapter agar bisa mengambil data dari database
        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        TamplilkanListViewBelanja(dataBaseHelper);


        // BUTTON ADD
        //button listeners for the add and view all buttons
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelBelanja modelBelanja;
                //menggunakan try, karena jika tanpa try, button add yang diklik dengan kondisi editText kosong akan forced closed
                try{
                    //create instance, mengambil teks dari user (dengan getText())
                    modelBelanja = new ModelBelanja(-1, et_nama.getText().toString(), Integer.parseInt(et_harga.getText().toString()));
                    Toast.makeText(MainActivity.this, modelBelanja.toString(), Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "Error membuat list", Toast.LENGTH_SHORT).show();
                    modelBelanja = new ModelBelanja(-1, "error", 0);
                }

                //buat instance method
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                //insert method, si addOne(), panggil dari class databasehelper (bukan class ini)
                boolean berhasil = dataBaseHelper.addOne(modelBelanja);

                Toast.makeText(MainActivity.this, "Berhasil=" +berhasil, Toast.LENGTH_SHORT).show();

                TamplilkanListViewBelanja(dataBaseHelper);

            }
        });


        //BUTTON VIEW ALL
        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TamplilkanListViewBelanja(dataBaseHelper);
                //setiap kali user "add" item belanja baru, list view tidak terupdate dengan item terbaru tersebut

                //Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_LONG).show();
            }
        });


        // method untuk DELETE
        lv_listBelanja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ModelBelanja clickedBelanja = (ModelBelanja) adapterView.getItemAtPosition(i);
                dataBaseHelper.deleteOne(clickedBelanja);
                TamplilkanListViewBelanja(dataBaseHelper);
                Toast.makeText(MainActivity.this, "Deleted" + clickedBelanja.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }




    // method untuk tombol viewAll
    private void TamplilkanListViewBelanja(DataBaseHelper dataBaseHelper) {
        // ambil referensi ArrayAdapter dari atas ^^^
        // menampilkan view list ketika aplikasi baru dibuka
        belanjaArrayAdapter = new ArrayAdapter<ModelBelanja>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getEveryone());
        // asocciate the arrayAdapter with the actual control on the screen
        lv_listBelanja.setAdapter(belanjaArrayAdapter);
    }
}