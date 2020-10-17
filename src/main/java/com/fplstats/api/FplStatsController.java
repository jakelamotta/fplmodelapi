package com.fplstats.api;

import com.fplstats.business.datacollection.DataImporterService;
import com.fplstats.business.adapter.MatcherService;
import com.fplstats.business.datacollection.DataCollectorService;
import com.fplstats.business.model.Calculator;
import com.fplstats.business.model.TeamPicker;
import com.fplstats.common.dto.fplstats.CalculatedPlayerStatisticsDto;
import com.fplstats.common.dto.fplstats.SeasonDto;
import com.fplstats.common.exception.NonExistingGameException;
import com.fplstats.common.exception.NonExistingSeasonTeamPlayerException;
import com.fplstats.common.exception.NonExistingTeamException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Map;


@SpringBootApplication(scanBasePackages = "com.fplstats")
@RestController
public class FplStatsController {

    private MatcherService matcherService;
    private DataCollectorService dataCollectorService;
    private DataImporterService importerService;
    private Calculator calculator;
    private TeamPicker picker;

    public FplStatsController(){

        calculator = new Calculator();
        dataCollectorService = new DataCollectorService();
        importerService = new DataImporterService();
        matcherService = new MatcherService();
        picker = new TeamPicker();
    }

    @RequestMapping("/Model/Pick")
    public String pick(@RequestParam(name="iterations") long iterations){
        String result = "Fel";

        try
        {
            double sum = 0.0;
            double totalPoints = 0.0;

            Map<String, CalculatedPlayerStatisticsDto> players = picker.pickTeam(iterations);

            for (String key: players.keySet()){
                sum += players.get(key).getCost();
                totalPoints += players.get(key).getxPAbs();
            }

            return new StringBuilder().append(players.get("G").getPlayerName()).append(" -- ")
                    .append(players.get("D1").getPlayerName()).append(" ").append(players.get("D2").getPlayerName()).append(" ")
                    .append(players.get("D3").getPlayerName()).append(" -- ")
                    .append(players.get("M1").getPlayerName()).append(" ").append(players.get("M2").getPlayerName()).append(" ")
                    .append(players.get("M3").getPlayerName()).append(" ").append(players.get("M4").getPlayerName()).append(" -- ")
                    .append(players.get("F1").getPlayerName()).append(" ").append(players.get("F2").getPlayerName()).append(" ")
                    .append(players.get("F3").getPlayerName()).append(" -- ").append(sum).append(" -- ").append(totalPoints).toString();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Model/Calculate")
    public String calculate(){
        String result = "Fel";

        try
        {
            result = calculator.calculate(null, 0);
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
            result = matcherService.adaptFplPlayerData();
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
            result = matcherService.adaptGames(0);
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

    @RequestMapping("/Import/Fpl/History")
    public String importFplHistory(@RequestParam(name = "year") int year){
        String result = "Fel";

        try
        {
            result = importerService.importFplHistory(year);
        }
        catch (Exception e)
        {
            System.out.println("IOException");
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
                    result = importUnderstatTeams(league,year);

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

                                            result = adaptAll(seasonDto.getId());

                                            if (result.equals("Success")){
                                                result = calculator.calculate(null, 0);
                                            }
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

    private String adaptAll(int seasonId) throws NonExistingSeasonTeamPlayerException, NonExistingGameException, NonExistingTeamException {
        String result = matcherService.adaptFplPlayerData();

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

        result = matcherService.adaptGames(seasonId);

        return result;
    }


}
