package com.example;

import com.example.MatchForm;
import com.example.MatchResult;
import com.example.MatchResult.Builder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/match")
public class MatchController {
   
	
    @PostMapping("/result")
    public String showMatchResult(MatchForm form, Model model) {
        // Logic to generate match result
        MatchResult matchResult = generateMatchResult(form);

        // Pass match result to the view
        model.addAttribute("matchResult", matchResult);
        return "match-result";
    }
    

    private synchronized MatchResult generateMatchResult(MatchForm form) {
        // Implement your logic to generate match result here
        // This can include fetching data from the database, applying algorithms, etc.
        // For demo purposes, let's create a mock match result
    	MatchResult result = new MatchResult.Builder()
    			.team1Name("Team A")
                .team2Name( "Team B")
                .matchDate("2024-04-15")
                .venueName("Stadium XYZ")
                .addTeam1Player("Player A1")  // Example player, replace with dynamic data if available
                .addTeam2Player("Player B1")  // Example player, replace with dynamic data if available
                .build();
        return result;
    }
}
