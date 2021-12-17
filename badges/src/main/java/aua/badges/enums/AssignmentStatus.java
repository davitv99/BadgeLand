package aua.badges.enums;

/**
 * @author davitv
 */
public enum AssignmentStatus {
    ACCEPTED("a", "Accepted"),
    REJECTED("r", "Rejected"),
    PENDING("p","Pending");

    private final String key;
    private final String value;

    AssignmentStatus(String key, String value) {
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
