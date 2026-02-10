package ru.sergalas.amplicodebot.bot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.sergalas.amplicodebot.bot.AmplicodeBot;

@RequiredArgsConstructor
@Configuration
public class TelegramConfig {

    @Bean
    public TelegramClient telegramClient(AmplicodeBot bot) {
        return new OkHttpTelegramClient(bot.getBotToken());
    }

}
