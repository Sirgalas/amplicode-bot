package ru.sergalas.amplicodebot.entities.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sergalas.amplicodebot.entities.entity.UserSession;
import ru.sergalas.amplicodebot.entities.enums.UserState;
import ru.sergalas.amplicodebot.exception.UserSessionNotFoundException;
import ru.sergalas.amplicodebot.entities.repository.UserSessionRepository;
import ru.sergalas.amplicodebot.entities.services.UserService;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserSessionRepository repository;

    @Override
    public Locale getLocale(Long chatId) {
        return Locale.forLanguageTag(getCreateOrUpdateUserSession(chatId).getLocale());
    }

    @Override
    public void setLocale(Long chatId, String locale) {
        UserSession userSession = getCreateOrUpdateUserSession(chatId);
        userSession.setLocale(locale);
        repository.save(userSession);
    }

    @Override
    public void setUserState(Long chatId, UserState userState) {
        UserSession userSession = getCreateOrUpdateUserSession(chatId);
        userSession.setUserState(userState);
        repository.save(userSession);
    }



    @Override
    public void setSelectedExpertId(Long chatId, String expertId) {
        UserSession userSession = getCreateOrUpdateUserSession(chatId);
        userSession.setSelectedExpertId(expertId);
        repository.save(userSession);
    }

    @Override
    public String getSelectedExpertId(Long chatId) {
        return getCreateOrUpdateUserSession(chatId).getSelectedExpertId();
    }

    @Override
    public UserState getUserState(Long chatId) {
        return getCreateOrUpdateUserSession(chatId).getUserState();
    }

    private UserSession getCreateOrUpdateUserSession(Long chatId) throws UserSessionNotFoundException {
        return repository.findByChatId(chatId).orElseGet(() ->{
            return repository.save(new UserSession(chatId));
        });
    }

}
