package br.com.arula.arula.model;

import java.io.Serializable;

/**
 * Created by Rafael Felipe on 03/10/2017.
 */

public class User implements Serializable{
    private Long Id;
    private String Name;
    private String Email;
    private String CPF;
    private String Address;
<<<<<<< HEAD
    private String Image;
=======
    private char[] Image;
>>>>>>> dc13c0ca8fd3745f4996e2b8186ebb2c83bbb0c0
    private String Formation;
    private Double Score;
    private String Desc;
    private String Req;

<<<<<<< HEAD
    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
=======
    public char[] getImage() {
        return Image;
    }

    public void setImage(char[] image) {
>>>>>>> dc13c0ca8fd3745f4996e2b8186ebb2c83bbb0c0
        Image = image;
    }

    public String getFormation() {
        return Formation;
    }

    public void setFormation(String formation) {
        Formation = formation;
    }

    public Double getScore() {
        return Score;
    }

    public void setScore(Double score) {
        Score = score;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getReq() {
        return Req;
    }

    public void setReq(String req) {
        Req = req;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public User() {}

<<<<<<< HEAD
    public User (String name, String formation, Double score, String desc, String req, String image) {
        this.Name = name;
        this.Formation = formation;
        this.Score = score;
        this.Desc = desc;
        this.Req = req;
        this.Image = image;
=======
    public User (String name) {
        this.Name = name;
>>>>>>> dc13c0ca8fd3745f4996e2b8186ebb2c83bbb0c0
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
