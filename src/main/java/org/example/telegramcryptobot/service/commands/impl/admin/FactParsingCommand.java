package org.example.telegramcryptobot.service.commands.impl.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.telegramcryptobot.model.Fact;
import org.example.telegramcryptobot.service.commands.impl.utils.AbstractBotCommand;
import org.example.telegramcryptobot.service.database.FactsDatabase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FactParsingCommand extends AbstractBotCommand {
    private final static String FINAL_MESSAGE = "The list of facts in the database has been updated.";

    private final List<Fact> factList = new ArrayList<>();
    private final FactsDatabase factDatabase;

    @Value("${facts.path}")
    private String path;

    @Override
    public SendMessage getMessageResponse(Update update) {
        deserialization();
        factDatabase.deleteAllFacts(); // Remove if necessary
        factDatabase.save(factList);

        return createMessage(update, FINAL_MESSAGE);
    }

    private void deserialization() {
        try {
            JSONArray factsArray = (JSONArray) new JSONParser().parse(getJsonFile());
            parseFacts(factsArray);
        } catch (ParseException e) {
            log.error("Error deserializing the json file: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private String getJsonFile() {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            log.error("Error reading the json file: {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    private void parseFacts(JSONArray factsArray) {
        factsArray.forEach(factsObject -> {
            JSONObject factsJsonObject = (JSONObject) factsObject;
            Fact fact = Fact.builder()
                    .text((String) factsJsonObject.get("body"))
                    .id((Long) factsJsonObject.get("id"))
                    .build();
            if(isCorrect(fact)) factList.add(fact);
        });
    }

    private static boolean isCorrect(Fact fact) {
        return fact != null && (!fact.getText().isBlank() || fact.getId() != 0);
    }
}
