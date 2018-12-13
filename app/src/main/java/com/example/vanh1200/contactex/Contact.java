package com.example.vanh1200.contactex;

public class Contact {
    private String mName;
    private String mPhone;
    private Boolean isFavorite;

    public Contact() {
        isFavorite = false;
    }

    public Contact(String name, String phone) {
        mName = name;
        mPhone = phone;
        isFavorite = false;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }
}
