package org.sewas.webcrawler.searchengine;

import org.jsoup.nodes.Document;

import java.util.List;

public interface SearchEngine {

    // Use this Method to return all Websites from a searchengine result page
    List<Document> getWebsitesFoundForSearchTerm(String searchTerm);

}
