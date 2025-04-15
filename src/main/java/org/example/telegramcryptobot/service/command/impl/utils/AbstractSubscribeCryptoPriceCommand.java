package org.example.telegramcryptobot.service.command.impl.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class AbstractSubscribeCryptoPriceCommand extends AbstractBotCommand {
    private final static String INCORRECT_USER_REQUEST = """
            The required price is not specified: %s,
            for example /subscribe_bitcoin 60000,
            for example /subscribe_ethereum 1500.
            """;
    private final static String SUCCESSFUL_ATTEMPT_MESSAGE =
            "A new subscription has been created for the price: %s USD";

    protected abstract void updateUserData(Double price, Update update);
    protected abstract String getCryptoPrice(Update update);
    protected abstract Integer getCommandPrefixLength();

    @Override
    public SendMessage getMessageResponse(Update update) {
        String userMessage = correctMessageText(update);

        if (isNumeric(userMessage)) {
            double price = getCorrectPrice(userMessage);
            updateUserData(price, update);

            String formatted = String.format(SUCCESSFUL_ATTEMPT_MESSAGE, price);
            String cryptoPrice = getCryptoPrice(update);

            return createMessage(update, cryptoPrice + "\n" + formatted);
        }

        String answerText = String.format(INCORRECT_USER_REQUEST, userMessage);
        return createMessage(update, answerText);
    }

    private double getCorrectPrice(String userMessage) {
        BigDecimal bigDecimal = new BigDecimal(userMessage);
        bigDecimal = bigDecimal.setScale(3, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    private boolean isNumeric(String input) {
        return isInteger(input) || isDouble(input);
    }

    private boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String correctMessageText(Update update) {
        return update.getMessage().getText()
                .substring(getCommandPrefixLength()).trim()
                .replace(',', '.')
                .replace("-", "");
    }
}

