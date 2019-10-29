package com.example.anle.luutruonline.Model;

public class Anh {
    private int IdAnh;
    private String Url;

    public Anh(int idAnh, String url) {
        IdAnh = idAnh;
        Url = url;
    }

    public int getIdAnh() {
        return IdAnh;
    }

    public void setIdAnh(int idAnh) {
        IdAnh = idAnh;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
