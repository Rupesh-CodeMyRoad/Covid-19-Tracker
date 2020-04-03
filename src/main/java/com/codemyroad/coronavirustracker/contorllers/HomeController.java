package com.codemyroad.coronavirustracker.contorllers;

import com.codemyroad.coronavirustracker.models.LocationStats;
import com.codemyroad.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
//Not a RESTController .... Simple Controller for HTML Response
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    //mappping to root url....It return the home templete
    @GetMapping("/")
    public String home(Model model){
        //Get all values in services to model in controllers.
        //Setting attribute in controller so that it can be parsed into templete
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        //Getting list of object, converting to stream,each object mapping to integer value and then sum up then assign to int totalReportedCases.
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);

        return"home";
}
}
