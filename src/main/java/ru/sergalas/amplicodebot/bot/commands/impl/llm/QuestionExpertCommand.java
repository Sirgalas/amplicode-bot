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
public class QuestionExpertCommand implements Command {
    private final ApplicationEventPublisher publisher;
    private final LocalizationService localizationService;
    private final UserService userService;

    @Override
    public boolean canHandle(Update update) {
        if(!update.hasMessage() || !update.getMessage().hasText()) {
            return false;
        }
        Long chatId = update.getMessage().getChatId();
        UserState userState = userService.getUserState(chatId);
        return userState != null && userState.equals(UserState.WAITING_FOR_QUESTION);
    }

    @Override
    public void handle(Update update) {
            long chatId = update.getMessage().getChatId();
            String question = update.getMessage().getText();
            userService.setUserState(chatId, UserState.IDLE);
            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text(
                            localizationService.getLocalizedMessage(chatId,"expert.question.default")
                    )
                    .build();
            publisher.publishEvent(new MessageEvent(this, message));

    }

    @Override
    public String getCommand() {
        return CommandEnum.QUESTION_EXPERT.getName();
    }
}
