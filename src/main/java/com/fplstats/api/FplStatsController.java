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
import javassist.bytecode.analysis.Executor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


@SpringBootApplication(scanBasePackages = "com.fplstats")
@RestController
public class FplStatsController {

    private static final String SUCCESS_STRING = "Success";
    private MatcherService matcherService;
    private DataCollectorService dataCollectorService;
    private DataImporterService importerService;
    private Calculator calculator;
    private TeamPicker picker;

    public FplStatsController() {

        calculator = new Calculator();
        dataCollectorService = new DataCollectorService();
        importerService = new DataImporterService();
        matcherService = new MatcherService();
        picker = new TeamPicker();
    }

    @RequestMapping("/Model/Pick")
    public String pick(@RequestParam(name = "iterations") long iterations) {

        String result;

        try {
            AtomicReference<Double>  sum = new AtomicReference<>(0d);
            AtomicReference<Double> totalPoints = new AtomicReference<>(0d);

            Map<String, CalculatedPlayerStatisticsDto> players = picker.pickTeam(iterations);

            players.values().stream().forEach(e -> {
                sum.accumulateAndGet(e.getCost(), (v, u) -> v+u);
                totalPoints.accumulateAndGet(e.getxPAbs(), (v, u) -> v+u);
            });
            for (Map.Entry<String, CalculatedPlayerStatisticsDto> entry : players.entrySet()) {

            }

            return new StringBuilder().append(players.get("G").getPlayerName()).append(" -- ")
                    .append(players.get("D1").getPlayerName()).append(" ").append(players.get("D2").getPlayerName()).append(" ")
                    .append(players.get("D3").getPlayerName()).append(" -- ")
                    .append(players.get("M1").getPlayerName()).append(" ").append(players.get("M2").getPlayerName()).append(" ")
                    .append(players.get("M3").getPlayerName()).append(" ").append(players.get("M4").getPlayerName()).append(" -- ")
                    .append(players.get("F1").getPlayerName()).append(" ").append(players.get("F2").getPlayerName()).append(" ")
                    .append(players.get("F3").getPlayerName()).append(" -- ").append(sum).append(" -- ").append(totalPoints).toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Model/Calculate")
    public String calculate() {
        try {
            calculator.calculate(null, 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            String result = e.getMessage();
            return result;
        }

        return "Success";
    }

    @RequestMapping("/")
    public String Index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/Adapter/Adapt/FPL/Player")
    public String AdaptFplData() {
        String result = "Fel";

        try {
            result = matcherService.adaptFplPlayerData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Adapter/Adapt/Understat/Team")
    public String ApdatUnderstatTeam() {

        String result = "Fel";

        try {
            result = matcherService.adaptTeams();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Adapter/Adapt/Understat/Game")
    public String ApdatUnderstatGame() {

        String result = "Fel";

        try {
            result = matcherService.adaptGames(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Adapter/Match/Understat/Player")
    public String MatchUnderstatPlayerData() {
        String result = "Fel";

        try {
            result = matcherService.matchUnderstatPlayer();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Adapter/Adapt/Understat/Player")
    public String AdaptUnderstatPlayerData() {
        String result = "Fel";

        try {
            result = matcherService.adaptUnderstatPlayers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }


    @RequestMapping("/Import/Understat/GamePlayer")
    public String ImportUnderstatGamePlayers(@RequestParam(name = "league") String league,
                                             @RequestParam(name = "year") int year) {
        String result = "Fel";

        try {
            importerService.importUnderstatGamePlayers(league, year);
        } catch (Exception e) {
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Import/Understat/Game")
    public String ImportUnderstatGames(@RequestParam(name = "league") String league,
                                       @RequestParam(name = "year") int year) {
        String result = "Fel";

        try {
            importerService.importUnderstatGames(league, year);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Import/Understat/Team")
    public String importUnderstatTeams(@RequestParam(name = "league") String league,
                                       @RequestParam(name = "year") int year) {
        String result = "Fel";

        try {
            importerService.importUnderstatTeams(league, year);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Import/Understat/Player")
    public String importUnderstatPlayers(@RequestParam(name = "league") String league,
                                         @RequestParam(name = "year") int year) {
        String result = "Fel";

        try {
            importerService.importUnderstatPlayers(league, year);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Import/Fpl/History")
    public String importFplHistory(@RequestParam(name = "year") int year) {
        String result = "Fel";

        try {
            result = importerService.importFplHistory(year);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Import/Fpl/Player")
    public String ImportFplData() {
        try {
            importerService.importFplData();
        } catch (Exception e) {
            return e.getMessage();
        }

        return "Success!";
    }

    @RequestMapping("/Collect/Fpl")
    public String CollectFplData() {

        String result = "Fel";

        try {
            dataCollectorService.collectFplData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;

    }

    @RequestMapping("/Collect/Understat/Base")
    public String CollectBaseData(@RequestParam(name = "league") String league,
                                  @RequestParam(name = "year") int year) {

        String result = "Fel";

        try {
            result = dataCollectorService.collectUnderstatBaseData(league, year);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;

    }

    @RequestMapping("/Collect/Understat/Nested")
    public String CollectNestedData(@RequestParam(name = "league") String league,
                                    @RequestParam(name = "year") int year) {

        String result = "Fel";

        try {
            result = dataCollectorService.collectUnderstatNestedData(league, year);
        } catch (Exception e) {
            System.out.println("IOException");
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Adapter/Match/All")
    public String MatchAll() {
        String result = "Fel";

        try {
            result = matcherService.matchUnderstatPlayer();
        } catch (Exception e) {
            System.out.println("IOException");
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Adapter/Match/Manual")
    public String MatchAll(@RequestParam(name = "understatname") String understatName,
                           @RequestParam(name = "fplname") String fplname) {
        String result = "Fel";

        try {
            result = matcherService.matchManually(understatName, fplname);
        } catch (Exception e) {
            System.out.println("IOException");
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    @RequestMapping("/Adapter/Adapt/All")
    public String AdaptAll() {
        String result = "Fel";

        try {
            adaptAll(1);
        } catch (Exception e) {
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

        try {
            result = dataCollectorService.collectUnderstatBaseData(league, year);

            if (result.equals(SUCCESS_STRING)) {
                importerService.importUnderstatPlayers(league, year);
                result = importUnderstatTeams(league, year);

                if (result.equals(SUCCESS_STRING)) {
                    importerService.importUnderstatGames(league, year);
                    result = dataCollectorService.collectUnderstatNestedData(league, year);

                    if (result.equals(SUCCESS_STRING)) {
                        importerService.importUnderstatGamePlayers(league, year);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;

    }

    @RequestMapping("/update")
    public String update() {

        CountDownLatch countDownLatch = new CountDownLatch(2);
        String result = "Fel";

        try {
            SeasonDto seasonDto = dataCollectorService.getActiveSeason();
            int year = seasonDto.getStartYear();
            String league = "EPL";
            AtomicInteger errors = new AtomicInteger(0);

            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    dataCollectorService.collectFplData();
                    importerService.importFplData();
                }
                catch (Exception e){
                    errors.incrementAndGet();
                }
                countDownLatch.countDown();
            });

            result = dataCollectorService.collectUnderstatBaseData(league, year);

            if (result.equals(SUCCESS_STRING)) {
                Executors.newSingleThreadExecutor().execute(() -> {
                    try {
                        importerService.importUnderstatPlayers(league, year);
                        importerService.importUnderstatGames(league, year);
                        dataCollectorService.collectUnderstatNestedData(league, year);
                        importerService.importUnderstatGamePlayers(league, year);
                    }
                    catch (Exception e){
                        errors.incrementAndGet();
                    }
                    countDownLatch.countDown();
                });

                countDownLatch.await();

                if (errors.get() > 0){
                    throw new Exception();
                }
                result = MatchAll();

                if (result.equals(SUCCESS_STRING)) {

                    result = adaptAll(seasonDto.getId());

                    if (result.equals(SUCCESS_STRING)) {
                        calculator.calculate(null, 0);
                    }
                }
            }

        } catch (
                Exception e) {
            System.out.println("IOException");
            System.out.println(e.getMessage());
            result = e.getMessage();
        }

        return result;

    }

    private String adaptAll(int seasonId) throws NonExistingSeasonTeamPlayerException, NonExistingGameException, NonExistingTeamException {
        String result = matcherService.adaptFplPlayerData();

        if (!result.equals(SUCCESS_STRING)) {
            return result;
        }

        result = matcherService.adaptUnderstatPlayers();

        if (!result.equals(SUCCESS_STRING)) {
            return result;
        }

        result = matcherService.adaptTeams();

        if (!result.equals(SUCCESS_STRING)) {
            return result;
        }

        result = matcherService.adaptGames(seasonId);

        return result;
    }


}
