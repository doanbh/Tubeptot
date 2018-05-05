package vn.k2studio.appanhdai.model;

import java.util.ArrayList;

public class ItemNew {
    private String nameOwnPost;
    private int avaOwnPost;
    private String timeAgo;
    private String titleNew;
    private String descripNew;
    private ArrayList<Integer> listImageNew;

    public ItemNew(String nameOwnPost, int avaOwnPost, String timeAgo, String titleNew,
            String descripNew, ArrayList<Integer> listImageNew) {
        this.nameOwnPost = nameOwnPost;
        this.avaOwnPost = avaOwnPost;
        this.timeAgo = timeAgo;
        this.titleNew = titleNew;
        this.descripNew = descripNew;
        this.listImageNew = listImageNew;
    }

    public String getNameOwnPost() {
        return nameOwnPost;
    }

    public void setNameOwnPost(String nameOwnPost) {
        this.nameOwnPost = nameOwnPost;
    }

    public int getAvaOwnPost() {
        return avaOwnPost;
    }

    public void setAvaOwnPost(int avaOwnPost) {
        this.avaOwnPost = avaOwnPost;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }

    public String getTitleNew() {
        return titleNew;
    }

    public void setTitleNew(String titleNew) {
        this.titleNew = titleNew;
    }

    public String getDescripNew() {
        return descripNew;
    }

    public void setDescripNew(String descripNew) {
        this.descripNew = descripNew;
    }

    public ArrayList<Integer> getListImageNew() {
        return listImageNew;
    }

    public void setListImageNew(ArrayList<Integer> listImageNew) {
        this.listImageNew = listImageNew;
    }
}
