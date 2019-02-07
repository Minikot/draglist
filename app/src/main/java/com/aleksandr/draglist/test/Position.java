package com.aleksandr.draglist.test;

public class Position {
    private int position;
    private String title;
    private boolean selected;

    public Position(String title, boolean selected) {
        this.title = title;
        this.selected = selected;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }
//
//    public void setSelected(boolean selected) {
//        this.selected = selected;
//    }

    public Position switchSelected() {
        this.selected ^= true;
        return this;
    }
}
