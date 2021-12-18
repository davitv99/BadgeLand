package io.project.app.enums;

/**
 * @author davitv
 */
public enum FileType {


    JPEG("JPEG", "image/jpeg"),
    PNG("PNG", "image/png");

    private final String key;
    private final String value;

    FileType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static FileType fromString(String text) {
        for (FileType m : FileType.values()) {
            if (m.value.equalsIgnoreCase(text)) {
                return m;
            }
        }
        return null;
    }

}
