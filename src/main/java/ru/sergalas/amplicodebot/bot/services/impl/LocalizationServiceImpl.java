package ru.sergalas.amplicodebot.bot.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.sergalas.amplicodebot.bot.services.LocalizationService;
import ru.sergalas.amplicodebot.servuces.UserService;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {

    private final UserService userService;
    private final MessageSource messageSource;

    @Override
    public String getLocalizedMessage(Long chatId, String key, Object... args) {
        Locale locale = userService.getLocale(chatId);
        return messageSource.getMessage(key, args, locale);
    }
}
