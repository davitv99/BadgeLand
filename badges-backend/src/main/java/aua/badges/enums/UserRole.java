package aua.badges.enums;



/**
 * @author davitv
 */
public enum UserRole {
    USER("US", "User"),
    ADMIN("AD", "Administrator");


    private final String key;
    private final String value;

    UserRole(String key, String value) {
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
