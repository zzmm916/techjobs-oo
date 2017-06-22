package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view

        Job job = jobData.findById(id);
//        ArrayList<Job> jobs = jobData.findById(id);
        model.addAttribute("job", job);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
//         new Job and add it to the jobData data store. Then
//         redirect to the job detail view for the new Job.

//        model.addAttribute(new JobForm());
        if(errors.hasErrors()) {
            return "new-job";
        }

        // Build new job
        Job newJob = new Job();
        newJob.setName(jobForm.getName());
        newJob.setEmployer(jobData.getEmployers().findById(jobForm.getEmployerId()));
        //fix final three to look similar to the top
        newJob.setLocation(jobData.getLocations().findById(jobForm.getLocationId()));
        newJob.setCoreCompetency(jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId()));
        newJob.setPositionType(jobData.getPositionTypes().findById(jobForm.getPositionTypeId()));
        //add new job to list of jobs
        jobData.add(newJob);

//        model.addAttribute("id", newJob.getId());

        //redirect to new job ** must change return state
        return "redirect:/job?id="+ newJob.getId();

    }
}
