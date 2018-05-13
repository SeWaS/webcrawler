package org.sewas.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.sewas.webcrawler.searchengine.SearchEngine;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSLibExtractorTest {

    @Test
    void shouldReturnTheRightJSLib() {
        // given
        SearchEngine fakeSearchEngine = new FakeSearchEngine();
        JSLibExtractor jsLibExtractor = new JSLibExtractor(fakeSearchEngine);

        // when
        List<String> jsLibs = jsLibExtractor.askSearchEngineForUsedLibs("Any search string");

        // then
        String expectedName = "That's what I want to have";
        assertEquals(1, jsLibs.size());
        assertEquals(expectedName, jsLibs.get(0));
    }

}

/**
 * Fake implementation for search engine
 */
class FakeSearchEngine implements SearchEngine {

    @Override
    public List<Document> getWebsitesFoundForSearchTerm(String searchTerm) {
        String html = "<html>" +
                "<script type=\"text/javascript\" src=\"That's what I want to have\"></script>" +
                "<script src=\"I don't want this\"></script>" +
                "<script type=\"text/javascript\">And I don't want this, too</script>" +
                "</html>";
        Document fakeWebsite = Jsoup.parse(html);

        return Arrays.asList(fakeWebsite);
    }
}
