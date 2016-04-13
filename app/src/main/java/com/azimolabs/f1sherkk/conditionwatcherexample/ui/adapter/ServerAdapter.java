package com.azimolabs.f1sherkk.conditionwatcherexample.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.azimolabs.f1sherkk.conditionwatcherexample.R;
import com.azimolabs.f1sherkk.conditionwatcherexample.data.Server;

/**
 * Created by F1sherKK on 13/04/16.
 */
public class ServerAdapter extends ArrayAdapter<Server> {

    public ServerAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_server, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvServerName = (TextView) convertView.findViewById(R.id.tvServerName);
            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.tvServerName.setText(getItem(position).getName());

        return convertView;
    }

    class ViewHolder {
        TextView tvServerName;
    }
}
