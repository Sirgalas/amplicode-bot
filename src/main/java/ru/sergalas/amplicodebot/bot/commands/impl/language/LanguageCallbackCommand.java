package ru.sergalas.amplicodebot.bot.commands.impl.language;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sergalas.amplicodebot.bot.commands.Command;
import ru.sergalas.amplicodebot.bot.enums.CommandEnum;
import ru.sergalas.amplicodebot.bot.enums.LangEnum;
import ru.sergalas.amplicodebot.bot.events.MessageEvent;
import ru.sergalas.amplicodebot.bot.services.KeyboardServices;
import ru.sergalas.amplicodebot.bot.services.LocalizationService;
import ru.sergalas.amplicodebot.bot.services.MessageTrackerService;
import ru.sergalas.amplicodebot.entities.entity.UserSession;
import ru.sergalas.amplicodebot.entities.services.UserService;


@RequiredArgsConstructor
@Component
public class LanguageCallbackCommand implements Command {
    private final ApplicationEventPublisher publisher;
    private final LocalizationService localizationService;
    private final UserService userService;
    private final KeyboardServices keyboardServices;
    private final MessageTrackerService messageTrackerService;

    @Override
    public boolean canHandle(Update update) {
        if(!update.hasCallbackQuery()) {
            return false;
        }
        String callbackData = update.getCallbackQuery().getData();

        return callbackData.equals(LangEnum.LANG_RU.getCode())
                || callbackData.equals(LangEnum.LANG_EN.getCode());
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        messageTrackerService.deleteLastMessage(chatId);
        String callbackData = update.getCallbackQuery().getData();
        if(callbackData.equals(LangEnum.LANG_RU.getCode())) {
            eventPublisher(chatId, UserSession.LOCALE_RU);
            return;
        }
        if(callbackData.equals(LangEnum.LANG_EN.getCode())) {
            eventPublisher(chatId,UserSession.LOCALE_EN);
        }
    }

    @Override
    public String getCommand() {
        return CommandEnum.ABOUT.getName();
    }

    private void eventPublisher(Long chatId, String locale) {
        userService.setLocale(chatId,locale);
        String localizedMessage = localizationService.getLocalizedMessage(
                chatId,
                "language.switched"
        );
        SendMessage message = SendMessage
                .builder()
                .chatId(chatId.toString())
                .replyMarkup(keyboardServices.mainMenu(chatId))
                .text(localizedMessage)
                .build();
        publisher.publishEvent(new MessageEvent(this, message));
    }
}
