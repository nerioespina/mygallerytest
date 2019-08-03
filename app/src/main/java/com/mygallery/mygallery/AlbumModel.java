package com.mygallery.mygallery;

public class AlbumModel {
    private int albumId;
    private String albumName;

    public AlbumModel(int albumId, String albumName) {
        this.albumId = albumId;
        this.albumName = albumName;
    }

    public AlbumModel() {
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
}
