package org.sewas.webcrawler.searchengine;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sewas.webcrawler.webclient.WebClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class GoogleSearch implements SearchEngine {

    private final WebClient webClient;
    private final String GOOGLE = ("http://www.google.de/search?q=");

    public GoogleSearch(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<Document> getWebsitesFoundForSearchTerm(String searchTerm) {

        // Encode search term so that we can use it as query param
        String encodedSearchTerm;

        try {
            encodedSearchTerm = URLEncoder.encode(searchTerm, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unfortunately we were not able to encode your search term properly :(");
        }

        // Get GoogleSearch's search result page
        Document googleResultPage = this.webClient.getWebsite(GOOGLE + encodedSearchTerm);

        // Scan search result page for DOM elements that contain links
        Elements resultLinkElements = googleResultPage.select("h3.r > a");

        // Extract URLs (from href attribute) from link elements
        List<String> links = getURLsFromResultElements(resultLinkElements);

        // NOTE: consider concurrency here!
        // Download each website that google found and return it
        return links.stream()
                .map(this.webClient::getWebsite)
                .collect(toList());
    }

    private List<String> getURLsFromResultElements(Elements resultLinkElements) {
        // separate real search result (start with "/url?q=") from result previews (start with "/search?q=")
        return resultLinkElements.stream()
                .filter(element -> element.attr("href").charAt(1) == 'u')
                .map(this::extractSubstringFromGoogleLink)
                .collect(toList());
    }

    private String extractSubstringFromGoogleLink(Element element) {
        // Get href attribute for given link element
        String href = element.attr("href");

        // Pick the link out from pattern "/url?q=[link]&sa="
        String[] temp = href.split("q=");
        String[] link = temp[1].split("&sa=");
        return link[0];
    }
}
