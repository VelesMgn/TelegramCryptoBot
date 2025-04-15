package org.example.telegramcryptobot.service.command.impl.user;

import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.service.button.CreateInlineKeyboardMarkup;
import org.example.telegramcryptobot.service.command.BotCommandType;
import org.example.telegramcryptobot.service.command.impl.utils.AbstractBotCommand;
import org.example.telegramcryptobot.service.database.FactsDatabase;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
@RequiredArgsConstructor
public class GetFactCommand extends AbstractBotCommand {
    private final FactsDatabase database;

    @Override
    public SendMessage getMessageResponse(Update update) {
        SendMessage message = createMessage(update, getFactText());
        message.setReplyMarkup(getInlineKeyboardMarkup());

        return message;
    }

    @Override
    public EditMessageText getCallbackAnswer(Update update) {
        String callBackData = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();

        EditMessageText editMessageText = createEditMessageText(chatId, messageId);

        if(callBackData.equals(BotCommandType.YES.getCommand())) {
            editMessageText.setText(getFactText());
            editMessageText.setReplyMarkup(getInlineKeyboardMarkup());
        } else {
            editMessageText.setText("We will be waiting for you for new facts!");
        }

        return editMessageText;
    }

    private EditMessageText createEditMessageText(long chatId, int messageId) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(String.valueOf(chatId));
        editMessageText.setMessageId(messageId);
        return editMessageText;
    }

    private InlineKeyboardMarkup getInlineKeyboardMarkup() {
        CreateInlineKeyboardMarkup button = new CreateInlineKeyboardMarkup();
        String[] buttonTexts = {"Yes", "No"};
        String[] buttonCommands = {BotCommandType.YES.getCommand(), BotCommandType.NO.getCommand()};

        button.createButton(buttonTexts[0], buttonCommands[0], buttonTexts[1], buttonCommands[1]);
        return button.getInlineKeyboardMarkup();
    }

    private String getFactText() {
        String fact = database.getFact();
        return String.format("%s\n\nDo you want to know more facts?", fact);
    }
}
