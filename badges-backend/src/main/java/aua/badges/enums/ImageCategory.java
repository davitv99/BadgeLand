package aua.badges.enums;

/**
 * @author davitv
 */
public enum ImageCategory {

    BADGE("B", "Badge"),
    ACCOUNT("A", "Account");


    private final String key;
    private final String value;

    ImageCategory(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
