package com.example.keuanganbelanja;

public class ModelBelanja {

    //properti dari setiap satu item / satu baris dari list belanja / pengeluaran
    private int id;
    private String nama;
    private int harga;

    //constructors
    public ModelBelanja(int id, String nama, int harga) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
    }

    public ModelBelanja() {
    }

    //toString untuk mencetak konten dari class object

    @Override
    public String toString() {
        return "DataBarang{" +
                "id=" + id +
                ", nama='" + nama + '\'' +
                ", harga=" + harga +
                '}';
    }


    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
