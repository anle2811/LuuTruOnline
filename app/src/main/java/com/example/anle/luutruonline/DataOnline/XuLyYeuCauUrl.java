package com.example.anle.luutruonline.DataOnline;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class XuLyYeuCauUrl {
    public String sendPostRequest(String requestUrl, HashMap<String,String> postDataParams){
        URL url;
        StringBuilder sb=new StringBuilder(); //StringBuilder được dùng để tạo chuỗi có thể thay đổi
        try{
            url=new URL(requestUrl); //Tạo một url từ chuỗi requestURL
            HttpURLConnection conn=(HttpURLConnection) url.openConnection(); //Mở kết nối với host
            conn.setReadTimeout(60000); //Thời gian chờ đọc
            conn.setConnectTimeout(60000); //Thời gian chờ mở liên kết đến url
            conn.setRequestMethod("POST"); //Yêu cầu POST được sử dụng để gửi dữ liệu đến máy chủ
            // Bằng cách ghi vào Output Stream của kết nối, ta có thể dễ dàng thêm bất kỳ dữ liệu nào vào body của yêu cầu POST.
            // Tuy nhiên, trước khi ta làm vậy, mày phải chắc chắn rằng mày gọi phương thức setDoOutput() của đối tượng HttpURLConnection và truyền true vào nó.
            conn.setDoInput(true); //Cho phép đọc
            conn.setDoOutput(true); //Cho phép ghi

            OutputStream os=conn.getOutputStream(); //Mở một luồng ghi dữ liệu //Trả về một OutputStream để ghi từ nguồn
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(os,"UTF-8")); //Tạo đối tượng writer để ghi dữ liệu vào os

            writer.write(getPostDataString(postDataParams)); //Bắt đầu ghi dữ liệu truyền vào
            writer.flush(); //xả luồng để tất cả các vùng nhớ (buffer) của luồng trong trạng thái trống
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode(); //Lấy mã phản hồi khi kết nối
            if (responseCode==HttpURLConnection.HTTP_OK){
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream())); //Đọc dữ liệu
                sb=new StringBuilder();
                String response;
                while ((response=br.readLine())!=null){
                    sb.append(response);
                }
            }
        } catch (MalformedURLException e) {
            Log.d("MainActivity","MalformedURL: "+e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("MainActivity","IOExcep: "+e.getMessage());
            e.printStackTrace();
        }
        return sb.toString();

    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");
            //Mã hóa bộ dữ liệu gửi đến máy chủ
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        Log.d("MainActivity","Result: "+result.toString());
        return result.toString();
    }
}
