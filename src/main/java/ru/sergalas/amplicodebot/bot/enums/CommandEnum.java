package ru.sergalas.amplicodebot.bot.enums;

public enum CommandEnum {
    ABOUT("ABOUT"),
    LANGUAGE("LANGUAGE");

    private final String name;
    CommandEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
