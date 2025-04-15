package org.example.telegramcryptobot.service.commands.impl.user;

import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.service.button.CreateReplyKeyboardMarkup;
import org.example.telegramcryptobot.service.commands.BotCommandType;
import org.example.telegramcryptobot.service.commands.impl.utils.AbstractBotCommand;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetContentCommand extends AbstractBotCommand {
    private final static String ANSWER_TEXT = "Select an entertainment content category:";
    private final CreateReplyKeyboardMarkup replyKeyboardMarkup;

    @Override
    public SendMessage getMessageResponse(Update update) {
        SendMessage message = createMessage(update, ANSWER_TEXT);
        message.setReplyMarkup(createButton());

        return message;
    }

    private ReplyKeyboard createButton() {
        ReplyKeyboardMarkup keyboardMarkup = replyKeyboardMarkup.getReplyKeyboardMarkup();
        String[] buttonTexts = {BotCommandType.NEWS.getCommand(), BotCommandType.FACTS.getCommand()};
        List<KeyboardRow> keyboardRows = replyKeyboardMarkup.getButton(buttonTexts[0], buttonTexts[1]);
        keyboardMarkup.setKeyboard(keyboardRows);

        return keyboardMarkup;
    }
}
