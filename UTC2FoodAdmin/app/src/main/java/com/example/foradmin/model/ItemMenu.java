package com.example.foradmin.model;

public class ItemMenu {
    int imvMenu;
    String txtMenu;

    public ItemMenu(int imvMenu, String txtMenu) {
        this.imvMenu = imvMenu;
        this.txtMenu = txtMenu;
    }

    public int getImvMenu() {
        return imvMenu;
    }

    public void setImvMenu(int imvMenu) {
        this.imvMenu = imvMenu;
    }

    public String getTxtMenu() {
        return txtMenu;
    }

    public void setTxtMenu(String txtMenu) {
        this.txtMenu = txtMenu;
    }
}
