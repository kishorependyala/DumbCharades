package com.teabreaktechnology.dumcharades.bean;

import java.io.Serializable;

/**
 * Created by kishorekpendyala on 1/18/15.
 */
public class Team implements Serializable {

    private static final long serialVersionUID = 1;
    int teamId;
    String teamName;

    public Team(Builder builder) {
        this.teamId = builder.teamId;
        this.teamName = builder.teamName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(teamId).append(",").append(teamName).append("}");
        return sb.toString();
    }

    public int getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public static class Builder {

        private int teamId;
        private String teamName;

        public Builder teamId(int teamId) {
            this.teamId = teamId;
            return this;
        }

        public Builder teamName(String teamName) {
            this.teamName = teamName;
            return this;
        }

        public Team build() {
            return new Team(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        return teamId == team.teamId;

    }

    @Override
    public int hashCode() {
        return teamId;
    }
}
