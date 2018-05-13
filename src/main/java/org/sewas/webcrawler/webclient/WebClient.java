package org.sewas.webcrawler.webclient;

import org.jsoup.nodes.Document;

public interface WebClient {
    Document getWebsite(String url);
}
