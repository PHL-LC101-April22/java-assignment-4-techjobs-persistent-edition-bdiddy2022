package org.launchcode.techjobs.persistent.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employer extends AbstractEntity {

    @JoinColumn(name = "employer_id")
    @OneToMany
    private List<Job> jobs = new ArrayList<>();
    @Size(min=3, max=50, message="Must be between 3-500 characters")
    @NotBlank(message = "Cannot be blank!")
    private String location;


    public Employer() {}

    public Employer(String location) {
        super();
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Job> jobs(){
        return jobs;
    }
}
