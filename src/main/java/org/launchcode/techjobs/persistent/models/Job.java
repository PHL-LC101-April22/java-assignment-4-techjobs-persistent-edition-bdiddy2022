package org.launchcode.techjobs.persistent.models;

import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Job extends AbstractEntity{

    @ManyToOne(targetEntity = Employer.class)
    private Employer employer;

    @ManyToMany(targetEntity = Skill.class)
    private List<Skill> skills = new ArrayList<>();

    public Job() {}

    public Job(Employer anEmployer, List<Skill> someSkills) {
        super();
        this.employer = anEmployer;
        this.skills = someSkills;
    }

    // Getters and setters.

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
