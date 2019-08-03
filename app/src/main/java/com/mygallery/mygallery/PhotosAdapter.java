package com.mygallery.mygallery;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class PhotosAdapter extends RecyclerView.Adapter <PhotosAdapter.PhotosViewHolder> {
    private onItemClickListener mListener;
    ArrayList<PhotoModel> photos;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }


    public PhotosAdapter(ArrayList<PhotoModel> photos) {
        this.photos = photos;
    }

    @Override
    public PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_cardview, parent, false);

        PhotosViewHolder photosViewHolder = new PhotosViewHolder(v, mListener);

        return photosViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosViewHolder holder, int position) {
        holder.photoId.setText(String.valueOf(photos.get(position).getPhotoId()));
        holder.photoName.setText(photos.get(position).getPhotoName());

        new imageLoadFromUrl((ImageView) (holder.photoImage))
                .execute(photos.get(position).getPhotoUrl());

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public static class PhotosViewHolder extends RecyclerView.ViewHolder{
        TextView photoId;
        TextView photoName;
        ImageView photoImage;
        public PhotosViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            photoId = (TextView) itemView.findViewById(R.id.photoId);
            photoName = (TextView) itemView.findViewById(R.id.photoName);
            photoImage = (ImageView) itemView.findViewById(R.id.photoImage);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

}
