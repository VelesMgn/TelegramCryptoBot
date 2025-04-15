package org.example.telegramcryptobot.service.button;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateInlineKeyboardMarkup {
    @Getter
    private final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    private final List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();

    public void createButton(String firstButtonText, String firstButtonName,
                             String secondButtonText, String secondButtonName) {
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        rowInLine.add(getInlineKeyboardButton(firstButtonText, firstButtonName));
        rowInLine.add(getInlineKeyboardButton(secondButtonText, secondButtonName));

        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
    }

    private InlineKeyboardButton getInlineKeyboardButton(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }
}
