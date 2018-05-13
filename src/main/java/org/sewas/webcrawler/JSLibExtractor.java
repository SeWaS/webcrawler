package org.sewas.webcrawler;

import org.jsoup.nodes.Document;
import org.sewas.webcrawler.searchengine.SearchEngine;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class JSLibExtractor {

    private final SearchEngine searchEngine;
    // All linked elements in <head> section with <script type="text/javascript" ...> are considered as JS libraries
    private final String JSLibCSSSelector = "head > script[type=text/javascript]";

    public JSLibExtractor(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    public List<String> askSearchEngineForUsedLibs(String searchTerm) {
        // Get an array of all Websites that were listed on the search engine's result page
        List<Document> websites = this.searchEngine.getWebsitesFoundForSearchTerm(searchTerm);

        // Get used JS-Libs for each website
        return websites.stream()
                .filter(Objects::nonNull)
                .filter(website -> !this.getSrcAttr(website).equals(""))
                .map(this::getSrcAttr)
                .collect(toList());
    }

    private String getSrcAttr(Document website) {
        return website.select(JSLibCSSSelector).attr("src");
    }
}
