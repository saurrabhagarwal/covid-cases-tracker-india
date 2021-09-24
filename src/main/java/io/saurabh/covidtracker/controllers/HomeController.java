package io.saurabh.covidtracker.controllers;

import io.saurabh.covidtracker.models.LocationStats;
import io.saurabh.covidtracker.models.StateData;
import io.saurabh.covidtracker.services.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CovidDataService covidDataService;

    @GetMapping("/world")
    public String home(Model model) {
        List<LocationStats> locationStatsList = covidDataService.getLocationStatsList();
        int totalCases = locationStatsList.stream().mapToInt(stats -> stats.getLatestTotalCases()).sum();
        int totalNewCases = locationStatsList.stream().mapToInt(stats -> stats.getPreDayCasesDiff()).sum();
        model.addAttribute("totalCases", totalCases);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("locationStats", locationStatsList);
        return "home";
    }

    @GetMapping("/")
    public String indiaHome(Model model) {
        model.addAttribute("stateDataList", covidDataService.getStateDataList());
        model.addAttribute("indiaData", covidDataService.getIndiaData());
        return "indiaHome";
    }
}
