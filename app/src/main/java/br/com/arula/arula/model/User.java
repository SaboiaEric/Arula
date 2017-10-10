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
    private String Image;
    private String Formation;
    private Double Score;
    private String Desc;
    private String Req;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
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

    public User (String name, String formation, Double score, String desc, String req, String image) {
        this.Name = name;
        this.Formation = formation;
        this.Score = score;
        this.Desc = desc;
        this.Req = req;
        this.Image = image;
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
