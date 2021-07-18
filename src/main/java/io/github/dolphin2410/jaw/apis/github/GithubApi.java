package io.github.dolphin2410.jaw.apis.github;

import io.github.dolphin2410.jaw.apis.github.internal.GithubArchive;
import io.github.dolphin2410.jaw.apis.github.internal.GithubRepo;
import io.github.dolphin2410.jaw.apis.github.internal.GithubUser;

/**
 * GithubApi modules all together
 *
 * @author dolphin2410
 */
public class GithubApi {
    /**
     * Gets user
     * @param username The github username
     * @return The GithubUser instance
     */
    public static GithubUser getUser(String username) {
        return GithubUser.get(username);
    }

    /**
     * Gets repository
     * @param user The github user
     * @param repoName The github repository name
     * @return The GithubRepo instance
     */
    public static GithubRepo getRepo(GithubUser user, String repoName) {
        return GithubRepo.get(user, repoName);
    }

    /**
     * Gets archive of version
     * @param repo The github repository
     * @param version The release version
     * @return An array of archives
     */
    public static GithubArchive[] getArchives(GithubRepo repo, String version) {
        return GithubArchive.get(repo, version);
    }

    /**
     * Gets archive of latest version
     * @param repo The github repository
     * @return An array of archives
     */
    public static GithubArchive[] getLatestArchives(GithubRepo repo) {
        return GithubArchive.get(repo);
    }
}
