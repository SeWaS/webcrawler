package org.sewas.webcrawler.searchengine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.sewas.webcrawler.webclient.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoogleSearchTest {

    @Test
    void shouldReturnListWithWebsitesFoundOnGoogleResultPage() {
        // given
        WebClient fakeClient = new FakeWebClient();
        SearchEngine google = new GoogleSearch(fakeClient);

        // when
        List<Document> websiteList = google.getWebsitesFoundForSearchTerm("MySearchterm");

        // then
        assertEquals("http://www.google.de/search?q=MySearchterm", ((FakeWebClient) fakeClient).getCalledUrl());
        assertEquals(1, websiteList.size());
        assertEquals(
                Jsoup.parse(FakeWebClient.html).toString(),
                websiteList.get(0).toString());
    }

}

class FakeWebClient implements WebClient {

    static String html = "<html>" +
            "<body>" +
            "<h3 class='r'>" +
            "<a href='/url?q=http://i.want.that.link&sa=whateverGooglePutsHere'>Linktext here</a>" +
            "</h3>" +
            "<h3 class='r'>" +
            "<a href='/search?q=http://forget.this.link.com&sa=whateverGooglePutsHere'>Search preview here</a>" +
            "</h3>" +
            "</body>" +
            "</html>";

    // which search engine url has been called?
    String calledUrl = null;

    @Override
    public Document getWebsite(String url) {
        if (calledUrl == null) calledUrl = url;
        return Jsoup.parse(html);
    }

    public String getCalledUrl() {
        return calledUrl;
    }
}
