package ru.sergalas.amplicodebot.bot.commands.impl.llm;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sergalas.amplicodebot.bot.commands.Command;
import ru.sergalas.amplicodebot.bot.enums.CommandEnum;
import ru.sergalas.amplicodebot.bot.events.MessageEvent;
import ru.sergalas.amplicodebot.bot.services.LocalizationService;
import ru.sergalas.amplicodebot.entities.enums.UserState;
import ru.sergalas.amplicodebot.entities.services.UserService;

@RequiredArgsConstructor
@Component
public class AscCommand implements Command {
    private final ApplicationEventPublisher publisher;
    private final LocalizationService localizationService;
    private final UserService userService;

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
                localizationService.getLocalizedMessage(chatId,"menu.ask")
            );
    }

    @Override
    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            userService.setUserState(chatId, UserState.WAITING_FOR_QUESTION);
            SendMessage message = SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(
                    localizationService.getLocalizedMessage(chatId, "ask.spring.ready")
                )
                .build();
            publisher.publishEvent(new MessageEvent(this, message));
        }
    }

    @Override
    public String getCommand() {
        return CommandEnum.ASC.getName();
    }
}
