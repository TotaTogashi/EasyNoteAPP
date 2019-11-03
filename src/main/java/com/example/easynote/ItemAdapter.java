package com.example.easynote;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    private int resourceId;
    public ItemAdapter(Context context,int resource,List<Item> data){
        super(context,resource,data);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final Item item=getItem(position);
        View view;
        ViewHolder holder=new ViewHolder();  // viewHolder 是提升 ListView 运行效率
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            holder.title=view.findViewById(R.id.list_item_title);
            holder.date=view.findViewById(R.id.list_item_time);
            holder.body=view.findViewById(R.id.list_item_body);
            holder.style=view.findViewById(R.id.list_item_style);
            view.setTag(holder);
        } else {
            view = convertView;
            holder=(ViewHolder) view.getTag();
        }
        holder.title.setText(item.getTitle());
        holder.date.setText(item.getDate());
        holder.body.setText(item.getBody());
        switch (item.getStyle()){
            case 0:holder.style.setBackgroundColor(Color.parseColor("#5eb7b7"));holder.title.setTextColor(Color.parseColor("#5eb7b7"));break;
            case 1:holder.style.setBackgroundColor(Color.parseColor("#fc7978"));holder.title.setTextColor(Color.parseColor("#fc7978"));break;
            case 2:holder.style.setBackgroundColor(Color.parseColor("#ffd369"));holder.title.setTextColor(Color.parseColor("#ffd369"));break;
            default:break;
        }

        return view;

    }
    class ViewHolder{
        TextView style;
        TextView title;
        TextView date;
        TextView body;
    }

}
