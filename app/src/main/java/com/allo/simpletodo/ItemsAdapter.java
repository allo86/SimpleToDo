package com.allo.simpletodo;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allo.simpletodo.model.Item;
import com.allo.simpletodo.model.Priority;
import com.allo.simpletodo.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Items Adapter
 * Created by ALLO on 19/6/16.
 */
public class ItemsAdapter extends BaseAdapter {

    List<Item> items;
    LayoutInflater inflater;
    Context context;

    public ItemsAdapter(Context context, List<Item> items) {
        this.items = items;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.element_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        Item item = getItem(position);
        Priority priority = Priority.valueOf(item.getPriority());

        holder.tvDescription.setText(item.getDescription());
        holder.container.setBackgroundColor(ContextCompat.getColor(context, priority.getBackgroundColorResource()));
        if (item.getDueDate() != null) {
            holder.tvDueDate.setVisibility(View.VISIBLE);
            holder.tvDueDate.setText(Utils.formatDateTime(item.getDueDate()));
        } else {
            holder.tvDueDate.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void notifyDataSetChanged(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.container)
        LinearLayout container;

        @BindView(R.id.tvDescription)
        TextView tvDescription;

        @BindView(R.id.tvDueDate)
        TextView tvDueDate;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
