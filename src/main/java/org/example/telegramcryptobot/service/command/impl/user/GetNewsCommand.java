package org.example.telegramcryptobot.service.command.impl.user;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.telegramcryptobot.service.command.impl.utils.AbstractBotCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetNewsCommand  extends AbstractBotCommand {
    @Value("${rss.feed}")
    private String rssUrl;

    @Override
    public SendMessage getMessageResponse(Update update) {
        List<SyndEntry> allNews = fetchNews();
        allNews.sort(Comparator.comparing(SyndEntry::getPublishedDate));

        String messageText = createTextMessage(allNews);

        return createMessage(update, messageText);
    }

    private List<SyndEntry> fetchNews() {
        List<SyndEntry> allEntries = new ArrayList<>();

        try {
            URL feedSource = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedSource));
            allEntries.addAll(feed.getEntries());
        } catch (Exception e) {
            System.err.println("Error loading the feed " + rssUrl + ": " + e.getMessage());
        }

        return allEntries;
    }

    private String createTextMessage(List<SyndEntry> allNews) {
        int limit = Math.min(5, allNews.size());

        StringBuilder sb = new StringBuilder("Latest Cryptocurrency News:\n\n");

        for (int i = 0; i < limit; i++) {
            SyndEntry entry = allNews.get(i);
            sb.append("• ").append(entry.getTitle()).append("\n");
            sb.append(entry.getLink()).append("\n");
            sb.append("\n");
        }

        return sb.toString();
    }
}
