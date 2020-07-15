package com.fplstats.api;

import com.fplstats.common.dto.fplstats.Result;
import com.fplstats.datacollection.DataCollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.Name;
import java.io.Console;
import java.io.IOException;


@SpringBootApplication(scanBasePackages = "com.fplstats")
@RestController

public class FplStatsController {


    private DataCollectorService dataCollectorService;

    public FplStatsController(){
        dataCollectorService = new DataCollectorService();
    }


    @RequestMapping("/")
    public String Index(){
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/CollectUnderstatNestedData")
    public String CollectNestedData(@RequestParam(name = "league") String league,
                                  @RequestParam(name = "year") int year) {

        String result = "Fel";

        try
        {
            result = dataCollectorService.collectUnderstatNestedData(league, year);
        }
        catch (Exception e)
        {
            System.out.println("IOException");
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;

    }


    @RequestMapping("/CollectUnderstatBaseData")
    public String CollectBaseData(@RequestParam(name = "league") String league,
                                           @RequestParam(name = "year") int year){

        String result = "Fel";

        try
        {
            result = dataCollectorService.collectUnderstatBaseData(league, year);
        }
        catch (IOException e)
        {
            System.out.println("IOException");
            System.out.println(e.getMessage());
            result = e.getMessage();
        }
        catch (Exception e){
            System.out.println("InterruptedException");
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }
}
