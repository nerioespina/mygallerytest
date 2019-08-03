package com.mygallery.mygallery;

public class PhotoModel {
    private int photoId;
    private String photoName, photoUrl;

    public PhotoModel(int photoId, String photoName, String photoUrl) {
        this.photoId = photoId;
        this.photoName = photoName;
        this.photoUrl = photoUrl;
    }

    public int getPhotoId() {
        return photoId;
    }

    public String getPhotoName() {
        return photoName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
