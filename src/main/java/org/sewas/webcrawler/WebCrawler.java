package org.sewas.webcrawler;

import org.sewas.webcrawler.searchengine.GoogleSearch;
import org.sewas.webcrawler.searchengine.SearchEngine;
import org.sewas.webcrawler.webclient.JsoupClient;
import org.sewas.webcrawler.webclient.WebClient;

import java.util.List;
import java.util.Scanner;

public class WebCrawler {

    public static void main(String[] args) {
        // Ask for searchTerm
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter yout GoogleSearch searchterm: ");
        String searchTerm = scanner.nextLine();
        scanner.close();

        // Ask JSLibExtractor for used JavaScript libs
        WebClient webClient = new JsoupClient();
        SearchEngine googleSearch = new GoogleSearch(webClient);
        JSLibExtractor jsLibExtractor = new JSLibExtractor(googleSearch);

        List<String> usedJSLibs = jsLibExtractor.askSearchEngineForUsedLibs(searchTerm);


        // NOTE: here all found JSLibs are printed, not just the top 5
        System.out.println("These JS libraries have been foung in the search results:");
        System.out.println("----------------------");
        for (String jsLib: usedJSLibs) {
            System.out.println(jsLib);
        }
    }

}
