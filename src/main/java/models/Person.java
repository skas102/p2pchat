package models;

public class Person implements Contact {

    private String username;
    private String ipAddress;
    private boolean isOnline;

    public Person(String username, String ipAddress, boolean isOnline) {
        this.username = username;
        this.ipAddress = ipAddress;
        this.isOnline = isOnline;
    }

    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public boolean getStatus() {
        return isOnline;
    }

    @Override
    public ContactType getType() {
        return ContactType.PERSON;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public boolean isOnline() {
        return this.isOnline;
    }
}
