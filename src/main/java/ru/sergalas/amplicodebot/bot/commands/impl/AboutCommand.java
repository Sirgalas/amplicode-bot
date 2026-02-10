package ru.sergalas.amplicodebot.bot.commands.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.sergalas.amplicodebot.bot.commands.Command;
import ru.sergalas.amplicodebot.bot.enums.CommandEnum;
import ru.sergalas.amplicodebot.bot.events.MassageEvent;

@RequiredArgsConstructor
@Component
public class AboutCommand implements Command {
    private final ApplicationEventPublisher publisher;

    @Override
    public boolean canHandle(Update update) {
        if(!update.hasMessage() && !update.getMessage().hasText()) {
            return false;
        }
        return update.getMessage().getText().equals("/about");
    }

    @Override
    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();

            SendMessage message = SendMessage // Create a message object
                    .builder()
                    .chatId(chatId)
                    .text("Приветствую")
                    .build();
            publisher.publishEvent(new MassageEvent(this, message));
        }
    }

    @Override
    public String getCommand() {
        return CommandEnum.ABOUT.getName();
    }
}
