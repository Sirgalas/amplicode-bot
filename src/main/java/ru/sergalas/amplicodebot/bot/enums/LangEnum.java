package ru.sergalas.amplicodebot.bot.enums;

public enum LangEnum {
    LANG_RU("lang_ru"),
    LANG_EN("lang_en");

    private final String code;

    LangEnum(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
