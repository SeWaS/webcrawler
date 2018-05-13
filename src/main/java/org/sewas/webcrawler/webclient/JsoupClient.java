package org.sewas.webcrawler.webclient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupClient implements WebClient {

    @Override
    public Document getWebsite(String url) {

        Document website;

        try {
            website = Jsoup.connect(url)
                    .userAgent("Mozilla")
                    .get();
        } catch (IOException e) {
            // Return null if web page cannot be accessed
            return null;
        }

        return website;
    }
}
