package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class kakeiboAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int layoutID;
    private String[] namelist;
    private String[] pricelist;
    private Bitmap[] photolist;
    private int selectPos;

    static class ViewHolder {
        TextView text;
        TextView price;
        ImageView img;
    }

    kakeiboAdapter(Context context, int itemLayoutId,
                   String[] names, String[] prices, int[] photos ){

        inflater = LayoutInflater.from(context);
        layoutID = itemLayoutId;

        namelist = names;
        pricelist = prices;
        // bitmapの配列
        photolist = new Bitmap[photos.length];
        // drawableのIDからbitmapに変換
        for( int i = 0; i< photos.length; i++){
            photolist[i] = BitmapFactory.decodeResource(context.getResources(), photos[i]);
        }
        selectPos=-1;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;


        if (convertView == null) {
            convertView = inflater.inflate(layoutID, null);
            holder = new ViewHolder();
            holder.img = convertView.findViewById(R.id.img_item);
            holder.text = convertView.findViewById(R.id.text_view);
            holder.price = convertView.findViewById(R.id.price);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        convertView.setMinimumHeight(20);
        holder.img.setImageBitmap(photolist[position]);


        holder.text.setText(namelist[position]);
        holder.price.setText(pricelist[position]);

        return convertView;
    }

    @Override
    public int getCount() {
        return namelist.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setprice(int idx,String price){
        pricelist[idx] = price;
    }
    public int getprice(int idx){
        int price = Integer.parseInt(pricelist[idx]);
        return price;
    }
}
