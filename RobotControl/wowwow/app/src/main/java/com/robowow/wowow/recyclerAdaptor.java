package com.robowow.wowow;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.robowow.wowow.R;

import java.util.ArrayList;


public class recyclerAdaptor extends RecyclerView.Adapter<recyclerAdaptor.ViewHolder> {
    private ArrayList<itemData> itemData;
    private recycleViewClickListener mListener;

    public recyclerAdaptor(ArrayList<itemData> itemData){

        this.itemData = itemData;
    }

    public interface recycleViewClickListener{
        void onItemClicked(int position);
    }

    public void setOnClickListener(recycleViewClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        itemData item = itemData.get(position);
        holder.title.setText(item.getTitle());
        holder.imageView.setImageResource(item.getImage());

        if(mListener != null)
        {
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(pos);
                }
            });
        }

    }
    @Override
    public int getItemCount() {

        return itemData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            imageView.setBackground(new ShapeDrawable(new OvalShape()));
            imageView.setClipToOutline(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            title = itemView.findViewById(R.id.textTitle);
        }
    }

}