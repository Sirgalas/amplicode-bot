package ru.sergalas.amplicodebot.entities.enums;

public enum UserState {
    IDLE("IDLE"),
    WAITING_FOR_EXPERT_QUESTION("WAITING_FOR_EXPERT_QUESTION"),
    WAITING_FOR_QUESTION("WAITING_FOR_QUESTION");

    private String state;
    UserState(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }
}
