package ru.sergalas.amplicodebot.bot.commands.impl.experts;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import ru.sergalas.amplicodebot.bot.commands.Command;
import ru.sergalas.amplicodebot.bot.data.ExpertData;
import ru.sergalas.amplicodebot.bot.enums.CommandEnum;
import ru.sergalas.amplicodebot.bot.events.MessageEvent;
import ru.sergalas.amplicodebot.bot.services.ExpertsService;
import ru.sergalas.amplicodebot.bot.services.LocalizationService;
import ru.sergalas.amplicodebot.entities.services.UserService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpertsCommand implements Command {
    public static final String EXPERT_PREFIX = "expert_";

    private final LocalizationService localizationService;
    private final ExpertsService expertsService;
    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    @Override
    public boolean canHandle(Update update) {
        if(!update.hasMessage() || !update.getMessage().hasText()) {
            return false;
        }
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        return message.equals(localizationService.getLocalizedMessage(chatId, "menu.experts"));
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        List<ExpertData> expertData = expertsService.listExperts(userService.getLocale(chatId));
        List<String> expertNames = expertData.stream().map(ExpertData::getFullName).toList();
        List<String> expertIds = expertData.stream().map(ExpertData::getId).toList();
        SendMessage message = SendMessage
            .builder()
            .chatId(chatId.toString())
            .text(localizationService.getLocalizedMessage(chatId, "ask.expert.select"))
            .replyMarkup(expertsInline(expertNames, expertIds))
            .build();
        publisher.publishEvent(new MessageEvent(this, message));
    }

    @Override
    public String getCommand() {
        return CommandEnum.EXPERTS.getName();
    }

    private ReplyKeyboard expertsInline(List<String> names, List<String> ids) {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        for(int i =0; i < names.size(); i++) {
            rows.add(new InlineKeyboardRow(InlineKeyboardButton
                .builder()
                .text(names.get(i))
                .callbackData(EXPERT_PREFIX + ids.get(i))
                .build()));
        }
        return InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();
    }

}
