package ru.sergalas.amplicodebot.entities.services;

import ru.sergalas.amplicodebot.entities.enums.UserState;

import javax.swing.plaf.PanelUI;
import java.util.Locale;

public interface UserService {
    public Locale getLocale(Long chatId);
    public void setLocale(Long chatId, String locale);
    public void setUserState(Long chatId, UserState userState);
    void setSelectedExpertId(Long chatId, String expertId);
    public String getSelectedExpertId(Long chatId);
    public UserState getUserState(Long chatId);

}
