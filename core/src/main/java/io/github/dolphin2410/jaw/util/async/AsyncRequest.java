package io.github.dolphin2410.jaw.util.async;

import io.github.dolphin2410.jaw.reflection.FieldAccessor;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * An asynchronous REST Api request. This currently supports GET, POST, PUT, DELETE.
 *
 * @author dolphin2410
 */
public class AsyncRequest {
    private final URI uri;
    private final RestMethod method;
    private final HashMap<String, String> headers = new HashMap<>();
    private final Object data = null;

    /**
     * Asynchronous request
     * @param uri The URI
     * @param method The REST Method
     */
    public AsyncRequest(URI uri, RestMethod method) {
        this.uri = uri;
        this.method = method;
    }

    /**
     * Asynchronous request
     * @param uri The URI
     * @param method The REST Method
     */
    public AsyncRequest(String uri, RestMethod method) {
        this.uri = URI.create(uri);
        this.method = method;
    }

    /**
     * Sets the body that will be passed to the REST Api server
     * @param data The data
     */
    public void setData(Object data) {
        new FieldAccessor<>(this, "data").set(data);
    }
    private HttpRequest.BodyPublisher inferPublisher() {
        if(this.data == null) {
            return HttpRequest.BodyPublishers.noBody();
        } else if (this.data instanceof String) {
            return HttpRequest.BodyPublishers.ofString((String) this.data);
        } else if(this.data instanceof File) {
            try {
                return HttpRequest.BodyPublishers.ofFile(((File) this.data).toPath());
            }
            catch (FileNotFoundException e){
                throw new RuntimeException("The specified file hasn't been found. " + e.toString());
            }
        } else if(this.data instanceof byte[]){
            return HttpRequest.BodyPublishers.ofByteArray((byte[])this.data);
        }
        throw new RuntimeException("Invalid Data format");
    }
    private HttpRequest.Builder addHeaders(HttpRequest.Builder builder, Map<String, String> map) {
        map.forEach(builder::setHeader);
        return builder;
    }

    /**
     * Sets header
     * @param key The key
     * @param value The value
     * @return The request
     */
    public AsyncRequest setHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }
    private HttpRequest createBaseRequest() {
        HttpRequest.Builder final_builder = addHeaders(HttpRequest.newBuilder().uri(this.uri), headers);
        return switch (method) {
            case GET -> final_builder.GET().build();
            case POST -> final_builder.POST(inferPublisher()).build();
            case DELETE -> final_builder.DELETE().build();
            case PUT -> final_builder.PUT(inferPublisher()).build();
        };
    }

    /**
     * Send without any callback
     * @return CompletableFuture
     */
    public CompletableFuture<HttpResponse<String>> send() {
        return send(null);
    }

    /**
     * Send with callback
     * @param action The callback
     * @return Completable future
     */
    public CompletableFuture<HttpResponse<String>> send(@Nullable Consumer<HttpResponse<String>> action) {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        CompletableFuture<HttpResponse<String>> future = new CompletableFuture<>();
        Async.execute(()->{
            try {
                HttpResponse<String> response = client.send(createBaseRequest(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
                future.complete(response);
                if (action != null) {
                    action.accept(response);
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e.getClass().getSimpleName() + " occurred while sending a request to the server. \nStacktrace: " + e);
            }
        });
        return future;
    }
}
