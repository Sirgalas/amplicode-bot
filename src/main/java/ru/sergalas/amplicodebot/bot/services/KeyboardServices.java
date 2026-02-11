package ru.sergalas.amplicodebot.bot.services;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface KeyboardServices {
    public ReplyKeyboard mainMenu(Long chatId);
}
