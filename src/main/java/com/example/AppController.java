package com.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class AppController {

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private UserBetRepository userBetRepository;
    
    @Autowired
    private EloRepository eloRepo;
    
    

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/loginSuccess")
    public String showLoginSuccessPage() {
        return "login_success";
    }

    @GetMapping("/loginError")
    public String showLoginErrorPage() {
        return "login_error";
    }

    @GetMapping("/login1")
    public String showLoginForm() {
        return "login"; // assuming "login.html" is the Thymeleaf template for login
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @PostMapping("/process_register")
    public String processRegistration(User user, Model model) {
        synchronized (this) { // Synchronize the block for thread safety
            // Save user to the main user table
            User savedUser = userRepo.save(user);
            
            // Get the ID of the saved user
            Long userId = savedUser.getid();
            
            // Create a corresponding entry in the Elo table with default Elo rating
            Elo elo = new Elo();
            elo.setUserId(userId);
            elo.setRating(1200); // Default Elo rating
            eloRepo.save(elo);
    
            // Pass the ID to the registration success page
            model.addAttribute("userId", userId);
            return "registration_success";
        }
    }

    @GetMapping("/setPreferences")
    public String showSetPreferencesPage() {
        return "set_preferences";
    }

    @GetMapping("/choice")
    public String showChoicePage() {
        return "choice";
    }

    @PostMapping("/login")
    public String login(User user) {
        User existingUser = userRepo.findByUsername(user.getusername());
        if (existingUser != null && existingUser.getpassword().equals(user.getpassword())) {
            return "redirect:/loginSuccess"; // Redirect to success page
        } else {
            return "redirect:/loginError"; // Redirect to error page
        }
    }
   

    @PostMapping("/submit")
    public String submitPreferences(UserBet userBet,Model model) {
        // Save user bet to the database
    	
        userBetRepository.save(userBet);

        String timings=userBet.getTimings();
        String venue=userBet.getVenue();
        Long userId=userBet.getUserId();
        
        List<UserBet> topPlayers = userBetRepository.findTop5ByUserIdNotAndVenueAndTimingsOrderByBetDesc(userId, venue,timings);

        model.addAttribute("userBetList", topPlayers);
        model.addAttribute("userId",userId);

        return "submission";
    }
    
    @GetMapping("/resultentry")
    public String showResultEntryPage(@RequestParam("userId") Long loggedInPlayerId, @RequestParam("opponentId") Long opponentId, Model model,User user) {
        // Retrieve user details for both players (optional, based on your needs)
        User loggedInPlayer = userRepo.findById(loggedInPlayerId).orElse(null);
        User opponentPlayer = userRepo.findById(opponentId).orElse(null);

        model.addAttribute("loggedInPlayer", loggedInPlayer);
        model.addAttribute("opponentPlayer", opponentPlayer);

        return "result_entry"; // Result entry page displays details of both players
    }
    
    private int getRatingFromDatabase(Long playerId) {
        Elo elo = eloRepo.findByUserId(playerId);
        return elo != null ? elo.getRating() : 1200; // Default rating if not found
    }

    // Method to update Elo rating in the database
    private void updateRatingInDatabase(Long playerId, int newRating) {
        Elo elo = eloRepo.findByUserId(playerId);
        if (elo != null) {
            elo.setRating(newRating);
            eloRepo.save(elo);
        }
    }

    // Method to calculate new Elo rating
    private int calculateNewRating(int rating1, int rating2, int score, int player) {
        double expectedScore = 1 / (1 + Math.pow(10, (rating2 - rating1) / 400.0));
        double actualScore = score == player ? 1 : 0;
        double kFactor = 32; // You can adjust this value based on your requirements
    
        // Calculate the change in rating for each player
        double ratingChange = kFactor * (actualScore - expectedScore);
    
        // For the winner, add the rating change
        if (score == player) {
            ratingChange *= 2; // Double the rating change for the winner
        }
    
        // Update the ratings
        int newRating1 = (int) (rating1 + ratingChange);
        int newRating2 = (int) (rating2 - ratingChange);
    
        return player == 1 ? newRating1 : newRating2;
    }
    

    @PostMapping("/submitScore")
    public String submitScore(@RequestParam int yourScore,
                              @RequestParam int opponentScore,
                              @RequestParam Long loggedInPlayerId,
                              @RequestParam Long opponentPlayerId,
                              Model model) {
        // Get the Elo ratings for both players from the database
        int rating1 = getRatingFromDatabase(loggedInPlayerId);
        int rating2 = getRatingFromDatabase(opponentPlayerId);

        // Calculate new Elo ratings based on the submitted scores
        int newRating1 = calculateNewRating(rating1, rating2, yourScore, 1);
        int newRating2 = calculateNewRating(rating2, rating1, opponentScore, 0);

        // Update Elo ratings in the database
        updateRatingInDatabase(loggedInPlayerId, newRating1);
        updateRatingInDatabase(opponentPlayerId, newRating2);

        // Pass the new Elo ratings to the view
        model.addAttribute("newRating1", newRating1);
        model.addAttribute("newRating2", newRating2);

        // Return the name of the Thymeleaf template to display the new Elo ratings
        return "update-rating-page";
    }

    

    
   
}
