package ru.sergalas.amplicodebot.bot.enums;

public enum CommandEnum {
    START("START"),
    ABOUT("ABOUT"),
    LANGUAGE("LANGUAGE"),
    EXPERTS("EXPERTS"),
    ASK_EXPERT("ASK_EXPERT"),
    QUESTION_EXPERT("QUESTION_EXPERT"),
    FACT("FACT"),
    ASC("ASC");

    private final String name;
    CommandEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
