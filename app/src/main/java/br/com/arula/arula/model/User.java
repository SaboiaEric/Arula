package br.com.arula.arula.model;

/**
 * Created by Rafael Felipe on 03/10/2017.
 */

public class User {
    private Long Id;

    private String Name;

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
