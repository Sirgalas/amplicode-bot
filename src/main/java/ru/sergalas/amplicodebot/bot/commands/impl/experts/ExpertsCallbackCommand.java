package ru.sergalas.amplicodebot.bot.commands.impl.experts;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sergalas.amplicodebot.bot.commands.Command;
import ru.sergalas.amplicodebot.bot.data.ExpertData;
import ru.sergalas.amplicodebot.bot.enums.CommandEnum;
import ru.sergalas.amplicodebot.bot.events.MessageEvent;
import ru.sergalas.amplicodebot.bot.services.ExpertsService;
import ru.sergalas.amplicodebot.bot.services.KeyboardServices;
import ru.sergalas.amplicodebot.bot.services.LocalizationService;
import ru.sergalas.amplicodebot.bot.services.MessageTrackerService;
import ru.sergalas.amplicodebot.entities.enums.UserState;
import ru.sergalas.amplicodebot.entities.services.UserService;


@RequiredArgsConstructor
@Component
public class ExpertsCallbackCommand implements Command {
    private final ApplicationEventPublisher publisher;
    private final LocalizationService localizationService;
    private final UserService userService;
    private final KeyboardServices keyboardServices;
    private final MessageTrackerService messageTrackerService;
    private final ExpertsService expertsService;

    @Override
    public boolean canHandle(Update update) {
        if(!update.hasCallbackQuery()) {
            return false;
        }
        String callbackData = update.getCallbackQuery().getData();
        return callbackData.startsWith(ExpertsCommand.EXPERT_PREFIX);
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        messageTrackerService.deleteLastMessage(chatId);
        String callbackData = update.getCallbackQuery().getData();
        String expertId = callbackData.substring(ExpertsCommand.EXPERT_PREFIX.length());
        userService.setSelectedExpertId(chatId, expertId);
        userService.setUserState(chatId, UserState.WAITING_FOR_EXPERT_QUESTION);
        ExpertData expertData = expertsService.getExpertById(expertId, userService.getLocale(chatId));
        String localizedMessage = localizationService.getLocalizedMessage(chatId, "ask.expert.selected", expertData);
        userService.getLocale(chatId);
        SendMessage message = SendMessage
                .builder()
                .chatId(chatId.toString())
                .text(localizedMessage)
                .build();
        publisher.publishEvent(new MessageEvent(this, message));
    }

    @Override
    public String getCommand() {
        return "%s_SELECT_CALLBACK".formatted(CommandEnum.EXPERTS.getName());
    }

}
