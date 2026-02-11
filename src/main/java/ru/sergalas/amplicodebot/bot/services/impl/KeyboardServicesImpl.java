package ru.sergalas.amplicodebot.bot.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.sergalas.amplicodebot.bot.services.KeyboardServices;
import ru.sergalas.amplicodebot.bot.services.LocalizationService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeyboardServicesImpl implements KeyboardServices {

    private final LocalizationService localizationService;

    @Override
    public ReplyKeyboard mainMenu(Long chatId) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow rowOne = new KeyboardRow();
        rowOne.add(localizationService.getLocalizedMessage(chatId, "menu.about"));
        keyboard.add(rowOne);

        KeyboardRow rowTwo = new KeyboardRow();
        rowTwo.add(localizationService.getLocalizedMessage(chatId, "menu.language"));
        keyboard.add(rowTwo);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboard);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }
}
