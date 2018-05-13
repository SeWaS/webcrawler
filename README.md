# Web Crawler

This command line tool accepts a string that will be used to perform a Google search. The web crawler will receive the
DOM of Google's main search result page and extract all main links of the websites found by Google. 

Furthermore each of the extracted website links will be called and all used JavaScript Libs (see more details below) will be
extracted and printed in your terminal.

## How to find JavaScript Libraries

The web crawler parses a website's DOM and considers every `<script>` Tag with the attributes `type = "text/javascript"`
and `src = "[NOT_EMPTY]"` placed in the HTML `<header>` section as JavaScript library.

## Used 3rd party libraries

The only 3rd party libraries used were `Jsoup` for receiving and parsing websites and `JUnit Jupiter` for testing.

## Testing

Only unit tests are available. Tests can be started with 
```
./gradlew test
```

The class `JsoupClient` has no tests yet. To provide a rational test for this class, network interaction with pre-defined
stubs is necessary. This could be achieved with e.g. `WireMock`.

## Some considerations about concurrency

This web crawler does not use concurrency (yet). The place to employ concurrency would be `GoogleSearch#getWebsitesFoundForSearchTerm`
where the web crawler iterates through the List of received websites and fetches them via network connection.

```
// NOTE: consider concurrency here!
// Download each website that google found and return it
return links.stream()
    .map(this.webClient::getWebsite)
    .collect(toList());
```

Since there
are about 10 search result links on Google's main search result page one could considerate to start a thread for each website, 
that tries to fetch the website and parses it.

## How to detect multiple appearances of the same JavaScript Lib

That is still a thing to tackle. With a human's eye you often recognize that the same JavaScript library appears multiple times;
maybe in another version and maybe loaded online or from a local resource. One can think about a kind of mapping or a pattern where
the web crawler can easily see if the used JavaScript Lib is already listed or not.
