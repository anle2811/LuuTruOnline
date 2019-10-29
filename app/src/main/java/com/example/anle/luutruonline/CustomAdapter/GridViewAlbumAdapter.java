package com.example.anle.luutruonline.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.anle.luutruonline.Model.Album;
import com.example.anle.luutruonline.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GridViewAlbumAdapter extends ArrayAdapter<Album> {

    private Context context;
    private int resource;
    private List<Album> imageUrlList;

    public GridViewAlbumAdapter(Context context,int resource,List<Album> imageUrlList){
        super(context,resource,imageUrlList);
        this.context=context;
        this.resource=resource;
        this.imageUrlList=imageUrlList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(resource,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.tv_tenalbum=convertView.findViewById(R.id.tv_tenAlbum);
            viewHolder.vfp_imgslide=convertView.findViewById(R.id.vfp_imgslide);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        List<List<ImageView>> list=new ArrayList<>();
        List<ImageView> listImg=new ArrayList<>();
        viewHolder.tv_tenalbum.setText(imageUrlList.get(position).getTenAlbum());
        for (int k=0;k<imageUrlList.get(position).getListAnh().size();k++){
            listImg.add(k,new ImageView(context));
            Picasso.get().load(imageUrlList.get(position).getListAnh().get(k).getUrl())
                    .placeholder(R.drawable.imgnull).into(listImg.get(k));
            viewHolder.vfp_imgslide.addView(listImg.get(k));
            viewHolder.vfp_imgslide.setFlipInterval(3000);
            viewHolder.vfp_imgslide.setAutoStart(true);

            viewHolder.vfp_imgslide.setInAnimation(context,android.R.anim.slide_in_left);
            viewHolder.vfp_imgslide.setOutAnimation(context,android.R.anim.slide_out_right);
        }
        list.add(position,listImg);
        return convertView;
    }

    public class ViewHolder{
        TextView tv_tenalbum;
        ViewFlipper vfp_imgslide;
    }
}
