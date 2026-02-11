package ru.sergalas.amplicodebot.servuces;

import ru.sergalas.amplicodebot.entity.UserSession;
import ru.sergalas.amplicodebot.exception.UserSessionNotFoundException;

import java.util.Locale;

public interface UserService {
    public Locale getLocale(Long chatId);
    public void setLocale(Long chatId, String locale);
}
