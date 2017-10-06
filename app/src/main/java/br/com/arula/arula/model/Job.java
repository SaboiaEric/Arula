package br.com.arula.arula.model;

import java.io.Serializable;

/**
 * Created by Rafael Felipe on 03/10/2017.
 */

public class Job implements Serializable{
    private Long Id;
    private String Name;
    private String Desc;
    private Double Salary;
    private char[] Image;
    private String CompanyName;
    private String Type;
    private String Hour;
    private String Req;

    public char[] getImage() {
        return Image;
    }

    public void setImage(char[] image) {
        Image = image;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public String getReq() {
        return Req;
    }

    public void setReq(String req) {
        Req = req;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        this.Desc = desc;
    }

    public Double getSalary() {
        return Salary;
    }

    public void setSalary(Double salary) {
        this.Salary = salary;
    }

    public Job(String name) {
        this.Name = name;
    }

    public Job() {}

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
