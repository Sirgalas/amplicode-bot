package ru.sergalas.amplicodebot.servuces.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sergalas.amplicodebot.entity.UserSession;
import ru.sergalas.amplicodebot.exception.UserSessionNotFoundException;
import ru.sergalas.amplicodebot.repository.UserSessionRepository;
import ru.sergalas.amplicodebot.servuces.UserService;

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

    private UserSession getCreateOrUpdateUserSession(Long chatId) throws UserSessionNotFoundException {
        return repository.findByChatId(chatId).orElseGet(() ->{
            return repository.save(new UserSession(chatId));
        });
    }

}
