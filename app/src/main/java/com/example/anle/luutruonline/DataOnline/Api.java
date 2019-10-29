package com.example.anle.luutruonline.DataOnline;

public class Api {
    private static final String ROOT_URL="https://dbnhatrovungtau.000webhostapp.com/PHPAlbum/Api.php?apicall=";

    public static final String actionKttk="ktTaikhoan";
    public static final String actionDktk="dkTaikhoan";
    public static final String actionDangNhap="kiemtraDN";
    public static final String actionLayDsAlbum="laydsAlbum";
    public static final String actionThemAlbum="themAlbum";

    public static final String URL_DKTK=ROOT_URL+actionDktk;
    public static final String URL_KTTK=ROOT_URL+actionKttk;
    public static final String URL_LAY_DSALBUM=ROOT_URL+actionLayDsAlbum;
    public static final String URL_DANGNHAP=ROOT_URL+actionDangNhap;
    public static final String URL_THEM_ALBUM=ROOT_URL+actionThemAlbum;

}
