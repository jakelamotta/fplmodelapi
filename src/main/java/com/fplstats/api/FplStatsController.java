package com.fplstats.api;

import com.fplstats.business.adapter.DataImporterService;
import com.fplstats.business.adapter.MatcherService;
import com.fplstats.business.datacollection.DataCollectorService;
import com.fplstats.business.model.Calculator;
import com.fplstats.common.dto.fplstats.SeasonDto;
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
    private Calculator calculator;

    public FplStatsController(){

        calculator = new Calculator();
        dataCollectorService = new DataCollectorService();
        importerService = new DataImporterService();
        matcherService = new MatcherService();
    }

    @RequestMapping("/Model/Calculate")
    public String calculate(){
        String result = "Fel";

        try
        {
            result = calculator.calculate();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/")
    public String Index(){
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/Adapter/Adapt/FPL/Player")
    public String AdaptFplData()
    {
        String result = "Fel";

        try
        {
            result = matcherService.adaptFplData();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Adapter/Adapt/Understat/Team")
    public String ApdatUnderstatTeam(){

        String result = "Fel";

        try
        {
            result = matcherService.adaptTeams();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Adapter/Adapt/Understat/Game")
    public String ApdatUnderstatGame(){

        String result = "Fel";

        try
        {
            result = matcherService.adaptGames();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Adapter/Match/Understat/Player")
    public String MatchUnderstatPlayerData()
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

    @RequestMapping("/Adapter/Adapt/Understat/Player")
    public String AdaptUnderstatPlayerData()
    {
        String result = "Fel";

        try
        {
            result = matcherService.adaptUnderstatPlayers();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }


    @RequestMapping("/Import/Understat/GamePlayer")
    public String ImportUnderstatGamePlayers(@RequestParam(name = "league") String league,
                                             @RequestParam(name = "year") int year){
        String result = "Fel";

        try
        {
            result = importerService.importUnderstatGamePlayers(league, year);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Import/Understat/Game")
    public String ImportUnderstatGames(@RequestParam(name = "league") String league,
                                       @RequestParam(name = "year") int year){
        String result = "Fel";

        try
        {
            result = importerService.importUnderstatGames(league,year);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Import/Understat/Team")
    public String importUnderstatTeams(@RequestParam(name = "league") String league,
                                         @RequestParam(name = "year") int year){
        String result = "Fel";

        try
        {
            result = importerService.importUnderstatTeams(league,year);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Import/Understat/Player")
    public String importUnderstatPlayers(@RequestParam(name = "league") String league,
                                         @RequestParam(name = "year") int year){
        String result = "Fel";

        try
        {
            result = importerService.importUnderstatPlayers(league,year);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Import/Fpl/Player")
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

    @RequestMapping("/Collect/Fpl")
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

    @RequestMapping("/Collect/Understat/Base")
    public String CollectBaseData(@RequestParam(name = "league") String league,
                                  @RequestParam(name = "year") int year) {

        String result = "Fel";

        try
        {
            result = dataCollectorService.collectUnderstatBaseData(league, year);
        }
        catch (Exception e)
        {
            System.out.println("IOException");
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;

    }

    @RequestMapping("/Collect/Understat/Nested")
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

    @RequestMapping("/Adapter/Match/All")
    public String MatchAll(){
        String result = "Fel";

        try
        {
            result = matcherService.matchUnderstatPlayer();
        }
        catch (Exception e)
        {
            System.out.println("IOException");
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Adapter/Match/Manual")
    public String MatchAll(@RequestParam(name = "understatname") String understatName,
                           @RequestParam(name = "fplname") String fplname){
        String result = "Fel";

        try
        {
            result = matcherService.matchManually(understatName, fplname);
        }
        catch (Exception e)
        {
            System.out.println("IOException");
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Adapter/Adapt/All")
    public String AdaptAll(){
        String result = "Fel";

        try
        {
            result = matcherService.adaptFplData();

            if (result != "Success"){
                return  result;
            }

            result = matcherService.adaptUnderstatPlayers();

            if (result != "Success"){
                return  result;
            }

            result = matcherService.adaptTeams();

            if (result != "Success"){
                return  result;
            }

            result = matcherService.adaptGames();
        }
        catch (Exception e)
        {
            System.out.println("IOException");
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/GetCompleteUnderstatSeason")
    public String getCompleteUnderstatSeason(@RequestParam(name = "league") String league,
                                    @RequestParam(name = "year") int year) {

        String result = "Fel";

        try
        {
            result = dataCollectorService.collectUnderstatBaseData(league,year);

            if (result.equals("Success")){
                result = importerService.importUnderstatPlayers(league,year);

                if (result.equals("Success")){
                    result = importerService.importUnderstatGames(league,year);

                    if (result.equals("Success")){
                        result = dataCollectorService.collectUnderstatNestedData(league, year);

                        if (result.equals("Success")){
                            result = importerService.importUnderstatGamePlayers(league, year);
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("IOException");
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;

    }

    @RequestMapping("/update")
    public String update() {

        String result = "Fel";

        try
        {
            SeasonDto seasonDto = dataCollectorService.getActiveSeason();
            int year = seasonDto.getStartYear();
            String league = "EPL";

            result = dataCollectorService.collectFplData();

            if (result.equals("Success")){

                result = importerService.importFplData();

                if (result == "Success"){

                    result = dataCollectorService.collectUnderstatBaseData(league,year);

                    if (result.equals("Success")){
                        result = importerService.importUnderstatPlayers(league,year);

                        if (result.equals("Success")){
                            result = importerService.importUnderstatGames(league,year);

                            if (result.equals("Success")){
                                result = dataCollectorService.collectUnderstatNestedData(league, year);

                                if (result.equals("Success")){
                                    result = importerService.importUnderstatGamePlayers(league, year);

                                    if (result.equals("Success")){

                                        result = MatchAll();

                                        if (result.equals("Success")){
                                            result = AdaptAll();
                                        }

                                    }

                                }
                            }
                        }
                    }

                }
            }
        }
        catch (Exception e)
        {
            System.out.println("IOException");
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;

    }
}
