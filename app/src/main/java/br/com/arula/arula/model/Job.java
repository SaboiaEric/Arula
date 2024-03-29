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
    private String Image;
    private String CompanyName;
    private String Type;
    private String Hour;
    private String Req;
    private boolean control;

    public Job(String name, String companyName, Double salary, String type, String hour, String desc, String req, String image) {
        this.Name = name;
        this.CompanyName = companyName;
        this.Salary = salary;
        this.Type = type;
        this.Hour = hour;
        this.Desc = desc;
        this.Req = req;
        this.Image = image;
    }


    public Job() {}

    public Job(String name) {
        this.Name = name;
    }

    public boolean getControl() {
        return control;
    }

    public void setControl(boolean control) {
        this.control = control;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
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
