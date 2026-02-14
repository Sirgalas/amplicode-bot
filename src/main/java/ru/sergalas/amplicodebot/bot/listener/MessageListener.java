package ru.sergalas.amplicodebot.bot.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.sergalas.amplicodebot.bot.events.MessageEvent;
import ru.sergalas.amplicodebot.bot.services.MessageTrackerService;

import java.io.Serializable;

@Component
@RequiredArgsConstructor
public class MessageListener {

    private final TelegramClient telegramClient;
    private final MessageTrackerService messageTrackerService;

    @EventListener
    public void on(MessageEvent event) throws TelegramApiException {
        BotApiMethod<? extends Serializable> message = event.getMessage();
        Serializable executed = telegramClient.execute(message);
        if(executed instanceof Message && ((Message) executed).getMessageId() != null) {
            messageTrackerService.saveLastMessage(
                ((Message) executed).getChatId(),
                ((Message) executed).getMessageId()
            );
        }
    }
}
