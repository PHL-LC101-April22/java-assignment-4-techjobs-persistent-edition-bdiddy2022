package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.*;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "list")
public class ListController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private SkillRepository skillRepository;

    static HashMap<String, String> columnChoices = new HashMap<>();

    public ListController () {

        columnChoices.put("all", "All");
        columnChoices.put("employer", "Employer");
        columnChoices.put("skill", "Skill");

    }

    @RequestMapping("")
    public String list(Model model) {
        List <Employer>employers = new ArrayList<>();
        for (Employer employer: employerRepository.findAll()) {
            employers.add(employer);
        }
        model.addAttribute("employers", employers);
        List<Skill> skills = new ArrayList<>();
        for (Skill skill:
             skillRepository.findAll()) {
            skills.add(skill);
        }
        model.addAttribute("skills", skills);

        return "list";
    }

    @RequestMapping(value = "jobs")
    public String listJobsByColumnAndValue(Model model, @RequestParam String column, @RequestParam String value) {
        Iterable<Job> jobs;
        Iterable<Employer> employers;
        
        if (column.toLowerCase().equals("all")){
            jobs = jobRepository.findAll();
            model.addAttribute("jobs", jobs);
        } else {
            jobs = JobData.findByColumnAndValue(column, value, jobRepository.findAll());
            model.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value);
            model.addAttribute("jobs", jobs);
        }

        return "list-jobs";
    }
}
