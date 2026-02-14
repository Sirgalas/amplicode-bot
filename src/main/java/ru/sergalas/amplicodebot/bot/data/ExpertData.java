package ru.sergalas.amplicodebot.bot.data;

import lombok.Data;


@Data
public class ExpertData {
    private final String id;
    private final String fullName;
    private final String bio;
    private final String contacts;
    private final String personaPrompt;

    @Override
    public String toString() {
        return "%s. %s. %s".formatted(fullName, bio, contacts);
    }
}
