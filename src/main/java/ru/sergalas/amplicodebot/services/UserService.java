package ru.sergalas.amplicodebot.services;

import java.util.Locale;

public interface UserService {
    public Locale getLocale(Long chatId);
    public void setLocale(Long chatId, String locale);
}
