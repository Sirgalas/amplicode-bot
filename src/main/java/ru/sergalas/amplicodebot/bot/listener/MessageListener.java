package ru.sergalas.amplicodebot.bot.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.sergalas.amplicodebot.bot.AmplicodeBot;
import ru.sergalas.amplicodebot.bot.events.MassageEvent;

import java.io.Serializable;

@Component
@RequiredArgsConstructor
public class MessageListener {

    private final TelegramClient telegramClient;

    @EventListener
    public void on(MassageEvent event) throws TelegramApiException {
        BotApiMethod<? extends Serializable> message = event.getMessage();
        telegramClient.execute(message);
    }
}
