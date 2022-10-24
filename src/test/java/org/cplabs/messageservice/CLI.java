package org.cplabs.messageservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CLI {

    static public void main( String ... args ) {
        (new CLI()).quickAndDirty();
    }

    /**
     * Prepares an executes a short workflow (smoketest).
     *
     */
//    @Test
    void quickAndDirty() {
        final String protocolServerAndPort = "http://localhost:8080";
        final String commonDomain = "messages";
        final List<Request> reqeustOrderedWorkflow = List.of(
                Request.create("GET", String.format("%s/%s/%s", protocolServerAndPort, "actuator", "health"), ""),
                Request.create("GET", String.format("%s/%s/%s", protocolServerAndPort, commonDomain, "all"), ""),
                Request.create("POST", String.format("%s/%s/%s", protocolServerAndPort, commonDomain, "mike/kalina"), "Test Message 1"),
                Request.create("POST", String.format("%s/%s/%s", protocolServerAndPort, commonDomain, "kalina/mike"), "Response to Test Message 1"),
                Request.create("GET", String.format("%s/%s/%s", protocolServerAndPort, commonDomain, "all"), "")
        );

        for (Request r : reqeustOrderedWorkflow) {
            // get next request and construct a HttpRequest from it
            HttpRequest request;
            try {
                if (r.type().equals("GET"))
                    request = HttpRequest.newBuilder()
                            .uri(new URI(r.url()))
                            .GET()
                            .build();
                else request = HttpRequest.newBuilder()
                        .uri(new URI(r.url()))
                        .POST(HttpRequest.BodyPublishers.ofString(r.body()))
                        .build();
            } catch (URISyntaxException e) {
                e.printStackTrace();
                break;
            }

            // Execute the HttpRequest with a HttpClient
            HttpResponse<String> response = null;
            try {
                HttpResponse.BodyHandler<String> responseBodyHandler = HttpResponse.BodyHandlers.ofString();
                response = HttpClient.newBuilder().build().send(request, responseBodyHandler);

                // stop the testing on failure (checks a range of successful 2xx values)
                Assertions.assertTrue(HttpStatus.valueOf(response.statusCode()).is2xxSuccessful(),
                        r + "::" + response.body().toString());

                if (HttpStatus.valueOf(response.statusCode()).is2xxSuccessful())
                    System.out.println(r.url() + " ~ " + response.body().toString());
                else
                    System.err.println(r.url() + " ~ " + response.body().toString());
                // try to keep console output in order
                System.out.flush();
                System.err.flush();
            } catch (IOException|InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            finally {
            }
        }
    }

}

/**
 * Context Record of a HTTP request to make later.
 * @param type
 * @param url
 * @param body
 */
record Request(String type, String url, String body) {
    static Request create(String type, String url, String body) {
        return new Request(type, url, body);
    }

    @Override
    public String toString() {
        return "Request{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}