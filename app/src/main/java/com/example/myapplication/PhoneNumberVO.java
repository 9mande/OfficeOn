package com.example.myapplication;

public class PhoneNumberVO {
    private String name;
    private String phone;
    private String company;
    private String position;
    private String email;
    private int photo;

    public PhoneNumberVO(String names, String phones, String companys, String positions, String emails) {
        this.name = names;
        this.phone = phones;
        this.company = companys;
        this.position = positions;
        this.email = emails;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String names){
        this.name = names;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setPhone(String phones){
        this.phone = phones;
    }

    public String getCompany(){
        return this.company;
    }

    public void setCompany(String companys) {this.company = companys;}

    public String getPosition() {return this.position;}

    public void setPosition(String positions) {this.position = positions;}

    public String getEmail(){return this.email;}

    public void setEmail(String emails) {this.email = emails;}

    public int getPhoto(){
        return this.photo;
    }

    public void setPhoto(int photoi){
        this.photo = photoi;
    }


    @Override
    public String toString(){
        return "PhoneNumberVO{" +
                "name='" + this.name + '\'' +
                ", phone='" + this.phone + '\'' +
                ", company='" + this.company + '\'' +
                ", position='" + this.position + '\'' +
                ", email='" + this.email + '\'' +
                '}';
    }

}
