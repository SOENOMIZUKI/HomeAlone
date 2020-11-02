package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

class CustomAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int resourcedId;
    private String[] items;

    static class ViewHolder {
        Switch switch1;
        TextView textView;
    }

    CustomAdapter(Context context, int resourcedId, String[] items) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resourcedId = resourcedId;
        this.items = items;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resourcedId, parent, false);

            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.txtArea1);
            holder.switch1 = convertView.findViewById(R.id.switch1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(items[position]);

        holder.switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, R.id.switch1);
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return items.length;
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
