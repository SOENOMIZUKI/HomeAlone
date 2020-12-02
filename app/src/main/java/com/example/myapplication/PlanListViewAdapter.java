package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

class PlanLisViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int resourcedId;
    private List<Plan> items;

    static class ViewHolder {
        TextView planView;
        TextView noteView;
        ImageView colorView;
        Button deleteButton;
    }

    PlanLisViewAdapter(Context context, int resourcedId, List<Plan> items) {
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
            holder.planView = convertView.findViewById(R.id.planView);
            holder.noteView = convertView.findViewById(R.id.noteView);
            holder.deleteButton = convertView.findViewById(R.id.delete);
            holder.colorView = convertView.findViewById(R.id.colorView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.planView.setText(items.get(0).getPlans());
        holder.colorView.setImageResource(R.drawable.aka);
        holder.noteView.setText(items.get(0).getNote());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, R.id.delete);
            }
        });


        return convertView;
    }


    @Override
    public int getCount() {
        return items.size();
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