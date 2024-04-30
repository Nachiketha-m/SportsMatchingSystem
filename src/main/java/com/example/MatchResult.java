package com.example;

import java.util.ArrayList;
import java.util.List;

public class MatchResult {
    private final String team1Name;
    private final String team2Name;
    private final String matchDate;
    private final String venueName;
    private final List<String> team1Players;
    private final List<String> team2Players;

    // Private constructor to force use of the Builder
    private MatchResult(Builder builder) {
        this.team1Name = builder.team1Name;
        this.team2Name = builder.team2Name;
        this.matchDate = builder.matchDate;
        this.venueName = builder.venueName;
        this.team1Players = builder.team1Players;
        this.team2Players = builder.team2Players;
    }

    // Getters
    public String getTeam1Name() {
        return team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public String getVenueName() {
        return venueName;
    }

    public List<String> getTeam1Players() {
        return new ArrayList<>(team1Players);
    }

    public List<String> getTeam2Players() {
        return new ArrayList<>(team2Players);
    }

    // Static Builder Class
    public static class Builder {
        private String team1Name;
        private String team2Name;
        private String matchDate;
        private String venueName;
        private List<String> team1Players = new ArrayList<>();
        private List<String> team2Players = new ArrayList<>();

        public Builder team1Name(String team1Name) {
            this.team1Name = team1Name;
            return this;
        }

        public Builder team2Name(String team2Name) {
            this.team2Name = team2Name;
            return this;
        }

        public Builder matchDate(String matchDate) {
            this.matchDate = matchDate;
            return this;
        }

        public Builder venueName(String venueName) {
            this.venueName = venueName;
            return this;
        }

        public Builder addTeam1Player(String player) {
            team1Players.add(player);
            return this;
        }

        public Builder addTeam2Player(String player) {
            team2Players.add(player);
            return this;
        }

        public MatchResult build() {
            return new MatchResult(this);
        }
    }
}