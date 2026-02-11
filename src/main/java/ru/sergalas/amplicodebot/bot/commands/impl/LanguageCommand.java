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
import ru.sergalas.amplicodebot.bot.services.LocalizationService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class LanguageCommand implements Command {
    private final ApplicationEventPublisher publisher;
    private final LocalizationService localizationService;

    @Override
    public boolean canHandle(Update update) {
        if(!update.hasMessage() && !update.getMessage().hasText()) {
            return false;
        }
        Long chatId = update.getMessage().getChatId();
        return update
                .getMessage()
                .getText()
                .equals(
                    localizationService.getLocalizedMessage(chatId,"menu.language")
                );
    }

    @Override
    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();

            SendMessage message = SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .replyMarkup(languageInline(chatId))
                .text(
                    localizationService.getLocalizedMessage(chatId,"language.select")
                )
                .build();

            publisher.publishEvent(new MassageEvent(this, message));
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
