package io.github.dolphin2410.jaw.apis.github.internal;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.dolphin2410.jaw.apis.util.Apis;
import io.github.dolphin2410.jaw.util.async.AsyncRequest;
import io.github.dolphin2410.jaw.util.async.RestMethod;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * This contains information about a github repository
 *
 * @author dolphin2410
 */
public record GithubRepo(String repositoryName, GithubUser owner, String language, String gitURL, String sshURL, String repoURL, String raw) {
    /**
     * This exception is called when any error is caught during repository information fetching.
     *
     * @author dolphin2410
     */
    static class GithubRepoException extends RuntimeException {
        public GithubRepoException(String message) {
            super(message);
        }
    }

    /**
     * Get the repository with GithubUser and a repository name.
     * @param user The user
     * @param repoName The name of the repository
     * @return Created github repo instance
     */
    public static GithubRepo get(GithubUser user, String repoName) {
        CompletableFuture<HttpResponse<String>> future = new AsyncRequest(
                Apis.GITHUB_API.concentrate("repos/" + user.username() + "/" + repoName), RestMethod.GET).send(null);
        final JsonObject json_data;
        try {
            json_data = JsonParser.parseString(future.get().body()).getAsJsonObject();
        }
        catch (ExecutionException | InterruptedException e) {
            throw new GithubRepoException(e.toString());
        }
        if (json_data.has("message")) {
            throw new GithubUser.GithubUserException(json_data.get("message").getAsString());
        }
        String rp_language = json_data.get("language").getAsString();
        String rp_git_url = json_data.get("git_url").getAsString();
        String rp_ssh_url = json_data.get("ssh_url").getAsString();
        String rp_url = json_data.get("clone_url").getAsString();
        return new GithubRepo(repoName, user, rp_language, rp_git_url, rp_ssh_url, rp_url, json_data.toString());
    }
}
