package io.github.dolphin2410.jaw.apis.github.internal;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.dolphin2410.jaw.util.io.OnlineFile;
import io.github.dolphin2410.jaw.apis.util.Apis;
import io.github.dolphin2410.jaw.util.async.AsyncRequest;
import io.github.dolphin2410.jaw.util.async.RestMethod;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * GithubArchive is used to access your release assets and default compressed zip and tar.gz source code files
 *
 * @author dolphin2410
 */
public record GithubArchive(GithubRepo repo, String name, String version, OnlineFile file) {
    /**
     * This is thrown when there is an error while handling github archives.
     *
     * @author dolphin2410
     */
    static class GithubArchiveException extends RuntimeException {
        public GithubArchiveException(String message) {
            super(message);
        }
    }

    /**
     * Execute this method to get all archives that exist in your specified release version.
     *
     * @param repository The repository to get archives from
     * @param version    The version of the release to get archives from
     * @return An array of archives that is available through github api. This includes assets and compressed source code files.
     * @throws GithubArchiveException When github api returned an error message, when there was an error sending a request to the github api, or when no archive was found.
     */
    public static GithubArchive[] get(GithubRepo repository, String version) throws GithubArchiveException {
        CompletableFuture<HttpResponse<String>> all_releases = new AsyncRequest(
                Apis.GITHUB_API.concentrate("repos/" + repository.owner().username() + "/" + repository.repositoryName() + "/releases"),
                RestMethod.GET
        ).send();
        JsonElement response;
        try {
            response = JsonParser.parseString(all_releases.get().body());
        } catch (InterruptedException | ExecutionException e) {
            throw new GithubArchiveException(e.toString());
        }
        if (response.isJsonObject()) {
            if (response.getAsJsonObject().has("message")) {
                throw new GithubArchiveException(response.getAsJsonObject().get("message").getAsString());
            }
        }
        JsonArray jsonAllRelease = response.getAsJsonArray();
        for (JsonElement release : jsonAllRelease) {
            if (release.getAsJsonObject().get("tag_name").getAsString().equals(version)) {
                return parseData(release.getAsJsonObject(), repository);
            }
        }
        throw new GithubArchiveException("No such archive found.");
    }

    /**
     * Execute this method to get the archives from the latest release of the specified repository.
     *
     * @param repository The repository in which you want to get the archive.
     * @return An array of latest release's archives.
     * @throws GithubArchiveException When Github Api returned an error message or when there was an error during request sending.
     */
    public static GithubArchive[] get(GithubRepo repository) throws GithubArchiveException {
        CompletableFuture<HttpResponse<String>> latestRelease = new AsyncRequest(
                Apis.GITHUB_API.concentrate("repos/" + repository.owner().username() + "/" + repository.repositoryName() + "/releases/latest"),
                RestMethod.GET
        ).send();
        JsonObject jsonLatestRelease;
        try {
            jsonLatestRelease = JsonParser.parseString(latestRelease.get().body()).getAsJsonObject();
        } catch (InterruptedException | ExecutionException e) {
            throw new GithubArchiveException(e.toString());
        }
        if (jsonLatestRelease.has("message")) {
            throw new GithubArchiveException(jsonLatestRelease.getAsJsonObject().get("message").getAsString());
        }
        return parseData(jsonLatestRelease, repository);
    }

    private static GithubArchive[] parseData(JsonObject release, GithubRepo repository) {
        ArrayList<GithubArchive> archives = new ArrayList<>();
        String version = release.get("tag_name").getAsString();
        release.get("assets").getAsJsonArray().forEach(asset -> {
                    archives.add(createArchive(
                            repository,
                            version,
                            asset.getAsJsonObject().get("browser_download_url").getAsString(),
                            asset.getAsJsonObject().get("name").getAsString()
                    ));
                }
        );
        archives.add(createArchive(
                repository,
                version,
                release.get("tarball_url").getAsString(),
                "1.0.tar.gz"
        ));
        archives.add(createArchive(
                repository,
                version,
                release.get("zipball_url").getAsString(),
                "1.0.zip"
        ));
        return archives.toArray(new GithubArchive[0]);
    }

    private static GithubArchive createArchive(GithubRepo repository, String version, String url, String archiveName) {
        try {
            return new GithubArchive(
                    repository,
                    archiveName,
                    version,
                    new OnlineFile(new URL(url))
            );
        } catch (MalformedURLException e) {
            throw new GithubArchiveException("Malformed URL: " + url);
        }
    }
}
