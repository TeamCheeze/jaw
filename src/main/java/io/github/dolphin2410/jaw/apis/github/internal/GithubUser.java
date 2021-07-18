package io.github.dolphin2410.jaw.apis.github.internal;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.dolphin2410.jaw.apis.util.Apis;
import io.github.dolphin2410.jaw.util.async.AsyncRequest;
import io.github.dolphin2410.jaw.util.async.RestMethod;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * This contains basic information of a github user.
 *
 * @author dolphin2410
 */
public record GithubUser(String username, String bio, int followers, int following, URL avatar_url, int num_repo, int num_gist, String raw) {
    /**
     * This exception is used when error is caught during information fetching
     *
     * @author dolphin2410
     */
    static class GithubUserException extends RuntimeException {
        public GithubUserException(String message) {
            super(message);
        }
    }

    /**
     * Gets a user from a github username
     * @param username The username
     * @return The GithubUser instance
     * @throws GithubUserException This is thrown when error is caught while fetching information
     */
    public static GithubUser get(String username) throws GithubUserException {
        CompletableFuture<HttpResponse<String>> future = new AsyncRequest(Apis.GITHUB_API.concentrate("users/" + username), RestMethod.GET).setHeader("Accept", "application/vnd.github.v3+json")
                .send(null);
        JsonObject json_data;
        try {
            json_data = JsonParser.parseString(future.get().body()).getAsJsonObject();
        }
        catch (ExecutionException | InterruptedException e) {
            throw new GithubUserException(e.toString());
        }
        if (json_data.has("message")) {
            throw new GithubUserException(json_data.get("message").getAsString());
        }
        String gh_username = json_data.get("login").getAsString();
        String gh_bio = json_data.get("bio").getAsString();
        int gh_following = json_data.get("following").getAsInt();
        int gh_followers = json_data.get("followers").getAsInt();
        URL gh_avatar_url;
        try {
            gh_avatar_url = URI.create(json_data.get("avatar_url").getAsString()).toURL();
        }
        catch (MalformedURLException e){
            throw new GithubUserException(e.toString());
        }
        int gh_num_repo = json_data.get("public_repos").getAsInt();
        int gh_num_gist = json_data.get("public_gists").getAsInt();
        return new GithubUser(gh_username, gh_bio, gh_following, gh_followers, gh_avatar_url, gh_num_repo, gh_num_gist, json_data.toString());
    }
}
