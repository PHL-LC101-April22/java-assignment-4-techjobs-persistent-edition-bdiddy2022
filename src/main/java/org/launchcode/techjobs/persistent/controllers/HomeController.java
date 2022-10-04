package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.JobData;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    public JobRepository jobRepository;
    @Autowired
    public EmployerRepository employerRepository;
    @Autowired
    SkillRepository skillRepository;
    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("title", "My Jobs");
        model.addAttribute("jobs", jobRepository.findAll());

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model, @RequestParam int employerId, @RequestParam List<Integer> skills) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "add";
        } else{
//        model.addAttribute("employer", employerRepository.findById(employer_id));
//        model.addAttribute("skills", skillRepository.findAllById(skills));
        Iterable<Skill> someSkills=skillRepository.findAllById(skills);
        ArrayList<Skill> skillList = new ArrayList<>();
        for (Skill skill: someSkills) {skillList.add(skill);}
        newJob.setSkills(skillList);
        List<Employer> employerList = new ArrayList<>();
        Iterable<Employer> employerIterable=employerRepository.findAll();
        for(Employer employer : employerIterable) {
            if(employer.getId() == employerId){
                newJob.setEmployer(employer);
                break;
            }
        }

        jobRepository.save(newJob);
        model.addAttribute("job", newJob);

        }
        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
        model.addAttribute("job", jobRepository.findById(jobId).get());

        return "view";
    }


}
