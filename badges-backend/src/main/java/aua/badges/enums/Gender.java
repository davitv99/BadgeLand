package aua.badges.enums;

/**
 * @author davitv
 */
public enum Gender {
    MALE("m", "Male"),
    FEMALE("f", "Female");


    private final String key;
    private final String value;

    Gender(String key, String value) {
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
