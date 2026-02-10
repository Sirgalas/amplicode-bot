package ru.sergalas.amplicodebot.bot.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sergalas.amplicodebot.bot.commands.Command;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class CommandHandler {

    private final Collection<Command> commands;



    public void handle(Update update) {
        for (Command command : commands) {
            if(command.canHandle(update)) {
                command.handle(update);
            }
        }
    }
}
