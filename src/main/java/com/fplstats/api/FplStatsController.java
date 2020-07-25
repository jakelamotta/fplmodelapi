package com.fplstats.api;

import com.fplstats.business.adapter.DataImporterService;
import com.fplstats.business.adapter.MatcherService;
import com.fplstats.business.datacollection.DataCollectorService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;


@SpringBootApplication(scanBasePackages = "com.fplstats")
@RestController
public class FplStatsController {

    private MatcherService matcherService;
    private DataCollectorService dataCollectorService;
    private DataImporterService importerService;

    public FplStatsController(){

        dataCollectorService = new DataCollectorService();
        importerService = new DataImporterService();
        matcherService = new MatcherService();
    }


    @RequestMapping("/")
    public String Index(){
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/Adapter/FPL")
    public String AdaptFplData()
    {
        String result = "Fel";

        try
        {
            result = matcherService.matchFplData();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Adapter/Understat/Player")
    public String AdaptUnderstatPlayerData()
    {
        String result = "Fel";

        try
        {
            result = matcherService.matchUnderstatPlayer();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }



    @RequestMapping("/ImportUnderstatGamePlayers")
    public String ImportUnderstatGamePlayers(){
        String result = "Fel";

        try
        {
            result = importerService.importUnderstatGamePlayers("EPL", 2019);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/ImportUnderstatGames")
    public String ImportUnderstatGames(){
        String result = "Fel";

        try
        {
            result = importerService.importUnderstatGames("EPL", 2019);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/ImportUnderstatPlayers")
    public String importUnderstatPlayers(){
        String result = "Fel";

        try
        {
            result = importerService.importUnderstatPlayers("EPL", 2019);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/ImportFplData")
    public String ImportFplData() {

        String result = "Fel";

        try
        {
            result = importerService.importFplData();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/CollectFplData")
    public String CollectFplData() {

        String result = "Fel";

        try
        {
            result = dataCollectorService.collectFplData();
        }
        catch (Exception e)
        {
            System.out.println("IOException");
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;

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
