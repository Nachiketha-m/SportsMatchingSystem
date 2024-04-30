package com.example;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GameController {

    @GetMapping("/match-result")
    public String showMatchResult() {
        return "match-result";
    }

    @GetMapping("/enter-scores")
    public String showEnterScoresPage() {
        return "enter-scores";
    }

    @PostMapping("/submit-scores")
    public String submitScores(@RequestParam int team1, @RequestParam int team2) {
        // Process submitted scores
        return "redirect:/match-result"; // Redirect to match result page
    }

    @GetMapping("/lets-play")
    public String goToEnterScores() {
        return "redirect:/enter-scores"; // Redirect to enter-scores page
    }
}
