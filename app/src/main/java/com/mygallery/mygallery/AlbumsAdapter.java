package com.mygallery.mygallery;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AlbumsAdapter extends RecyclerView.Adapter <AlbumsAdapter.AlbumsViewHolder> {
    private onItemClickListener mListener;
    ArrayList<AlbumModel> albums;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }


    public AlbumsAdapter(ArrayList<AlbumModel> albums) {
        this.albums = albums;
    }

    @Override
    public AlbumsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_cardview, parent, false);

        AlbumsViewHolder albumsViewHolder = new AlbumsViewHolder(v, mListener);

        return albumsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumsViewHolder holder, int position) {
        holder.albumId.setText(String.valueOf(albums.get(position).getAlbumId()));
        holder.albumName.setText(albums.get(position).getAlbumName());
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public static class AlbumsViewHolder extends RecyclerView.ViewHolder{
        TextView albumId;
        TextView albumName;
        public AlbumsViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            albumId = (TextView) itemView.findViewById(R.id.albumId);
            albumName = (TextView) itemView.findViewById(R.id.albumName);

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
