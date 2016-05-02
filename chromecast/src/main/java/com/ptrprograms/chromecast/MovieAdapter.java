package com.ptrprograms.chromecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieAdapter extends ArrayAdapter<Video> {

    public MovieAdapter(Context context) {
        this(context, 0);
    }

    public MovieAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if( convertView == null ) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.video_list_layout_item, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.text);
            holder.poster = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(getItem(position).getTitle());
        Picasso.with(getContext()).load(getItem(position).getPoster()).into(holder.poster);

        return convertView;
    }

    public class ViewHolder {
        public TextView title;
        public ImageView poster;
    }
}
