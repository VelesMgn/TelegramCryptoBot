package org.example.telegramcryptobot.service.commands.impl.admin;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.service.commands.BotCommandType;
import org.example.telegramcryptobot.service.commands.impl.utils.AbstractBotCommand;
import org.example.telegramcryptobot.service.database.UserDatabase;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageForAllCommand extends AbstractBotCommand {
    private final int prefixLength = BotCommandType.SEND_FOR_ALL.getCommand().length();
    private final UserDatabase userDatabase;

    @Override
    public List<SendMessage> getMessageForAll(Update update) {
        String textToSend = createTextToSend(update);
        return userDatabase.getAllUsers().stream()
                .map(user -> createMessage(update, textToSend)).collect(Collectors.toList());
    }

    private String createTextToSend(Update update) {
        String textToSend = update.getMessage().getText().substring(prefixLength).trim();
        return textToSend.isBlank() ? EmojiParser.parseToUnicode("Empty mailing list") : textToSend;
    }
}
