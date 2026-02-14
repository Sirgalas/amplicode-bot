package ru.sergalas.amplicodebot.bot.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import ru.sergalas.amplicodebot.bot.events.MessageEvent;
import ru.sergalas.amplicodebot.bot.services.MessageTrackerService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class MessageTrackerServiceImpl implements MessageTrackerService {
    private final Map<Long, Integer> lastBotMessages = new ConcurrentHashMap<>();
    private final ApplicationEventPublisher publisher;

    @Override
    public void saveLastMessage(Long chatId, Integer messageId) {
        lastBotMessages.put(chatId, messageId);
    }

    @Override
    public void deleteLastMessage(Long chatId) {
        Integer messageId = lastBotMessages.get(chatId);
        if(messageId != null){
            DeleteMessage deleteMessage = DeleteMessage
                    .builder()
                    .chatId(chatId)
                    .messageId(messageId)
                    .build();
            publisher.publishEvent(new MessageEvent(this, deleteMessage));
        }
    }
}
