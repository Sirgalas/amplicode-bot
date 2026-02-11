package ru.sergalas.amplicodebot.bot.commands.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import ru.sergalas.amplicodebot.bot.commands.Command;
import ru.sergalas.amplicodebot.bot.enums.CommandEnum;
import ru.sergalas.amplicodebot.bot.enums.LangEnum;
import ru.sergalas.amplicodebot.bot.events.MassageEvent;
import ru.sergalas.amplicodebot.bot.services.KeyboardServices;
import ru.sergalas.amplicodebot.bot.services.LocalizationService;
import ru.sergalas.amplicodebot.services.UserService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class LanguageCallbackCommand implements Command {
    private final ApplicationEventPublisher publisher;
    private final LocalizationService localizationService;
    private final UserService userService;
    private final KeyboardServices keyboardServices;

    @Override
    public boolean canHandle(Update update) {
        if(!update.hasCallbackQuery()) {
            return false;
        }
        String callbackData = update.getCallbackQuery().getData();

        return callbackData.equals(LangEnum.LANG_RU.getCode())
                || callbackData.equals(LangEnum.LANG_EN.getCode());
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String callbackData = update.getCallbackQuery().getData();
        if(callbackData.equals(LangEnum.LANG_RU.getCode())) {
            userService.setLocale(chatId,"ru");
            String localizedMessage = localizationService.getLocalizedMessage(
                    chatId,
                    "language.switched"
            );
            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatId.toString())
                    .replyMarkup(keyboardServices.mainMenu(chatId))
                    .text(localizedMessage)
                    .build();
            publisher.publishEvent(new MassageEvent(this, message));
            return;
        }
        if(callbackData.equals(LangEnum.LANG_EN.getCode())) {
            userService.setLocale(chatId,"en");
            String localizedMessage = localizationService.getLocalizedMessage(
                    chatId,
                    "language.switched"
            );
            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatId.toString())
                    .replyMarkup(keyboardServices.mainMenu(chatId))
                    .text(localizedMessage)
                    .build();
            publisher.publishEvent(new MassageEvent(this, message));
            return;
        }
    }

    private ReplyKeyboard languageInline(Long chatId) {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add(
            new InlineKeyboardRow(
                InlineKeyboardButton
                    .builder()
                    .text(localizationService.getLocalizedMessage(chatId,"language.ru"))
                    .callbackData(LangEnum.LANG_RU.getCode()).build()
            )
        );
        rows.add(
            new InlineKeyboardRow(
                InlineKeyboardButton
                    .builder()
                    .text(localizationService.getLocalizedMessage(chatId,"language.en"))
                    .callbackData(LangEnum.LANG_EN.getCode())
                    .build()
            )
        );
        return InlineKeyboardMarkup.builder().keyboard(rows).build();
    }

    @Override
    public String getCommand() {
        return CommandEnum.ABOUT.getName();
    }
}
