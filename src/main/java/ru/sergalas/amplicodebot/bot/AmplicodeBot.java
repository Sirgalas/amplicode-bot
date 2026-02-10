package ru.sergalas.amplicodebot.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sergalas.amplicodebot.bot.handlers.CommandHandler;

@RequiredArgsConstructor
@Component
public class AmplicodeBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    @Value("${app.telegram.bot.token}")
    private String botToken;

    private final CommandHandler commandHandler;




    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        commandHandler.handle(update);
    }
}
