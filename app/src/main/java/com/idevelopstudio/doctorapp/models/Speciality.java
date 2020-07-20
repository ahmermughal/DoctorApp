package com.idevelopstudio.doctorapp.models;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class Speciality {

    private String title;
    private Drawable image;
    private int backgroundColor;
    private boolean selected = false;

    public Speciality(String title, Drawable image, int backgroundColor) {
        this.title = title;
        this.image = image;
        this.backgroundColor = backgroundColor;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void toggle() {
        this.selected = !selected;
    }
}
