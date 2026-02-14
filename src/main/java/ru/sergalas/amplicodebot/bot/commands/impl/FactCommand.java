package ru.sergalas.amplicodebot.bot.commands.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sergalas.amplicodebot.bot.commands.Command;
import ru.sergalas.amplicodebot.bot.enums.CommandEnum;
import ru.sergalas.amplicodebot.bot.events.MessageEvent;
import ru.sergalas.amplicodebot.bot.services.KeyboardServices;
import ru.sergalas.amplicodebot.bot.services.LocalizationService;

@RequiredArgsConstructor
@Component
public class FactCommand implements Command {
    private final ApplicationEventPublisher publisher;
    private final LocalizationService localizationService;

    @Override
    public boolean canHandle(Update update) {
        if(!update.hasMessage() || !update.getMessage().hasText()) {
            return false;
        }
        Long chatId = update.getMessage().getChatId();
        return update
            .getMessage()
            .getText()
            .equals(
                localizationService.getLocalizedMessage(chatId,"menu.fact")
            );
    }

    @Override
    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();

            SendMessage message = SendMessage // Create a message object
                    .builder()
                    .chatId(chatId)
                    .text(
                            localizationService.getLocalizedMessage(chatId,"fact.default")
                    )
                    .build();
            publisher.publishEvent(new MessageEvent(this, message));
        }
    }

    @Override
    public String getCommand() {
        return CommandEnum.FACT.getName();
    }
}
