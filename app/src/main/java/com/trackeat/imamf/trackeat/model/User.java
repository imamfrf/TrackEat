package com.trackeat.imamf.trackeat.model;

public class User {

    private String userId;
    private String nama;
    private String email;
    private String tanggalLahir;
    private String jenisKelamin;
    private String tinggiBadan;
    private String beratBadan;
    private String photoUrl;

    public User() {
    }

    public User(String userId, String nama, String email, String tanggalLahir, String jenisKelamin, String tinggiBadan, String beratBadan, String photoUrl) {
        this.userId = userId;
        this.nama = nama;
        this.email = email;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.tinggiBadan = tinggiBadan;
        this.beratBadan = beratBadan;
        this.photoUrl = photoUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getTinggiBadan() {
        return tinggiBadan;
    }

    public void setTinggiBadan(String tinggiBadan) {
        this.tinggiBadan = tinggiBadan;
    }

    public String getBeratBadan() {
        return beratBadan;
    }

    public void setBeratBadan(String beratBadan) {
        this.beratBadan = beratBadan;
    }
}
