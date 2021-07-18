package io.github.dolphin2410.jaw.util.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;

/**
 * You can use this to easily handle online files. You can use the downloadLocally method to download from the file URI given.
 * @author dolphin2410
 */
public class OnlineFile {
    static class OnlineFileException extends RuntimeException {
        public OnlineFileException(String message) {
            super(message);
        }
    }
    private final URL url;
    public OnlineFile(URL url) {
        this.url = url;
    }
    public static OnlineFile from(String url) {
        try {
            return from(new URL(url));
        }
        catch (MalformedURLException e) {
            throw new OnlineFileException(e.toString());
        }
    }
    public static OnlineFile from(URL url) {
        return new OnlineFile(url);
    }
    public File downloadLocally(Path path) {
        ReadableByteChannel readableByteChannel;
        FileOutputStream fileOutputStream;
        FileChannel fileChannel;
        try {
            readableByteChannel = Channels.newChannel(url.openStream());
            fileOutputStream = new FileOutputStream(path.toFile());
            fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            return path.toFile();
        }
        catch (IOException e) {
            throw new OnlineFileException(e.toString());
        }
    }
    public File downloadLocally(File file) {
        return downloadLocally(file.toPath());
    }
    public File downloadLocally(String path) {
        return downloadLocally(new File(path));
    }
}