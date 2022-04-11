package com.source.ui.paint.colorFilter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.source.ui.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ColorFilterAdapter extends RecyclerView.Adapter<ColorFilterAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private List<float[]> filters;

    public ColorFilterAdapter(LayoutInflater mInflater, List<float[]> filters) {
        this.mInflater = mInflater;
        this.filters = filters;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        MyViewHolder viewHolder;
        viewHolder = new MyViewHolder(mInflater.inflate(R.layout.list_item, parent, false));
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    @Override
    public void onBindViewHolder(@NotNull final MyViewHolder holder, int position) {
        ColorFilter.imageViewColorFilter(holder.imageView, filters.get(position));
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.img);
        }
    }

}
