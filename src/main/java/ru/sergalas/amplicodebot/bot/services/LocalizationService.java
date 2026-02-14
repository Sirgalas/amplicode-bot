package ru.sergalas.amplicodebot.bot.services;

import java.util.Locale;

public interface LocalizationService {
    public String getLocalizedMessage(Long chatId, String key, Object... args);
    public String getLocalizedMessage(String key, Locale locale, Object... args);
}
