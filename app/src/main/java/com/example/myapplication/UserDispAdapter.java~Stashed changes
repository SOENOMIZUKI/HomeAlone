package com.example.myapplication;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class UserDispAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private int layoutID;
    private String[] namelist;
    private String[] datelist;


    static class ViewHolder {
        TextView text;
        TextView date;

    }

    UserDispAdapter(Context context, int itemLayoutId,
                   String[] names, String[] prices ){

        inflater = LayoutInflater.from(context);
        layoutID = itemLayoutId;

        namelist = names;
        datelist = prices;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(layoutID, null);
            holder = new ViewHolder();

            holder.text = convertView.findViewById(R.id.text_view);
            holder.date = convertView.findViewById(R.id.price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }




        holder.text.setText(namelist[position]);
        holder.date.setText(datelist[position]);

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
}
