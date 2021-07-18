package io.github.dolphin2410.jaw.apis.util;

/**
 * The api url list
 *
 * @author dolphin2410
 */
public enum Apis {
    GITHUB_API("https://api.github.com");
    public final String uri;
    Apis(String uri){
        this.uri = uri;
    }
    private String safeUrlConcentration(String source, String to_add){
        if(source.endsWith("\"") && to_add.startsWith("\"")){
            return new StringBuilder(source).deleteCharAt(source.length()).append(to_add).toString();
        } else if(!source.endsWith("\"") && !to_add.startsWith("\"")){
            return source + "/" + to_add;
        } else {
            return source + to_add;
        }
    }

    /**
     * Concentrates two uri pieces
     * @param other The other uri piece to concentrate
     * @return THe concentrated url
     */
    public String concentrate(String other) {
        return safeUrlConcentration(uri, other);
    }
}
