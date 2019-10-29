package com.example.anle.luutruonline.Model;

import android.widget.ViewFlipper;

import java.util.List;

public class Album {
    private int IdAlbum;
    private String tenAlbum;
    private List<Anh> listAnh;

    public Album(int IdAlbum,String tenAlbum, List<Anh> listAnh) {
        this.IdAlbum=IdAlbum;
        this.tenAlbum = tenAlbum;
        this.listAnh = listAnh;
    }

    public String getTenAlbum() {
        return tenAlbum;
    }

    public void setTenAlbum(String tenAlbum) {
        this.tenAlbum = tenAlbum;
    }


    public int getIdAlbum() {
        return IdAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        IdAlbum = idAlbum;
    }

    public List<Anh> getListAnh() {
        return listAnh;
    }

    public void setListAnh(List<Anh> listAnh) {
        this.listAnh = listAnh;
    }
}
