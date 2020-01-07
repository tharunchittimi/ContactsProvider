package com.example.contactsprovider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<MyModel> {

private ArrayList<MyModel> dataSet;
        Context mContext;

public CustomAdapter(ArrayList<MyModel> data, Context context) {
        super(context, R.layout.item_content, data);
        this.dataSet = data;
        this.mContext = context;

        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        MyModel myModel = dataSet.get(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_content, parent, false);
        TextView tvName =  rowView.findViewById(R.id.textView);
        TextView tvNumber =  rowView.findViewById(R.id.textView2);
        ImageView imageView =  rowView.findViewById(R.id.imageView);
        tvName.setText(myModel.name);
        tvNumber.setText(myModel.number);
        Glide.with(mContext).load(myModel.imageUri).placeholder(R.drawable.nomg) .apply(RequestOptions.circleCropTransform()).into(imageView);
        return rowView;

        }
}
