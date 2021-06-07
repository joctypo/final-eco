package com.joctypo.finaleco;

public class User {

    String id;
    String name;
    String email;
    String password;
    String rol;
    String phoneNumber;




    public User(){

    }

    public User(String id, String name, String email, String password,String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public User(String id, String name, String email, String password,String phoneNumber,String rol) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.rol=rol;
        this.phoneNumber = phoneNumber;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
