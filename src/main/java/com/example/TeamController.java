package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeamController {

    @GetMapping("/teams")
    public String showTeamsPage() {
        return "match-form"; // Assuming you have a "teams.html" file in your templates directory
    }
}
