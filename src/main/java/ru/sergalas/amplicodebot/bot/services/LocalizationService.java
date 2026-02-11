package ru.sergalas.amplicodebot.bot.services;

public interface LocalizationService {
    public String getLocalizedMessage(Long chatId, String key, Object... args);
}
