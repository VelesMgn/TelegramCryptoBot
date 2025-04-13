package org.example.telegramcryptobot.service.commands.impl;

import lombok.RequiredArgsConstructor;
import org.example.telegramcryptobot.service.commands.impl.utils.AbstractBotCommand;
import org.example.telegramcryptobot.service.commands.BotCommandType;
import org.example.telegramcryptobot.service.database.UserDatabase;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class SubscribeEthereumPriceCommand extends AbstractBotCommand {
    private final static String INCORRECT_USER_REQUEST = "The required price is not specified: ";

    private final int removingCommandFromMessage = BotCommandType.SUBSCRIBE_ETHEREUM_PRICE.getCommand().length();
    private final GetPriceEthereumCommand getPriceEthereumCommand;
    private final UserDatabase database;

    @Override
    public SendMessage getMessageResponse(Update update) {
        String userMessage = correctMessageText(update);

        if(isNumeric(userMessage)) {
            updateUserData(userMessage, update);
            String formatted = String.format("A new subscription has been created for the price: %s USD", userMessage);
            String bitcoinPrice = getPriceEthereumCommand.getMessageResponse(update).getText();
            return createMessage(update, bitcoinPrice + "\n" + formatted);
        }

        String answerText = INCORRECT_USER_REQUEST + " " + userMessage;
        return createMessage(update, answerText);
    }

    private void updateUserData(String userMessage, Update update) {
        double price = Double.parseDouble(userMessage);
        database.updateBitcoinPrice(update.getMessage().getChatId(), price);
    }

    private boolean isNumeric(String input) {
        return isInteger(input) || isDouble(input);
    }

    public boolean isDouble(String userMessage) {
        try {
            Double.parseDouble(userMessage);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isInteger(String userMessage) {
        try {
            Integer.parseInt(userMessage);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String correctMessageText(Update update) {
        return update.getMessage().getText()
                .substring(removingCommandFromMessage).trim()
                .replace(',', '.')
                .replace("-", "");
    }
}
