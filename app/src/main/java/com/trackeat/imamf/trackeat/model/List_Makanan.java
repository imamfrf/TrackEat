package com.trackeat.imamf.trackeat.model;

public class List_Makanan {

    private String namaMakanan, namaCatering, kaloriMakanan, harga;


    public List_Makanan(String namaMakanan, String namaCatering, String kaloriMakanan, String harga) {
        this.namaMakanan = namaMakanan;
        this.namaCatering = namaMakanan;
        this.kaloriMakanan = kaloriMakanan;
        this.harga = harga;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    public String getNamaCatering() {
        return namaCatering;
    }

    public String getKaloriMakanan() {
        return kaloriMakanan;
    }

    public String getHarga() {
        return harga;
    }
}
