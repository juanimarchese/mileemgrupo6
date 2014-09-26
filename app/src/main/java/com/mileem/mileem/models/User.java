package com.mileem.mileem.models;

/**
 * Created by ramirodiaz on 25/09/14.
 */
public class User extends  IdName {
    String telephone;
    String email;

    public User(int id, String name) {
        super(id, name);
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
