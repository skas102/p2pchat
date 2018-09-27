package models;

import java.util.List;

public class Group implements Contact {

    private String name;
    private List<Person> members;

    public Group(String name, List<Person> members) {
        this.name = name;
        this.members = members;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean getStatus() {
        return false;
    }

    @Override
    public ContactType getType() {
        return ContactType.GROUP;
    }

    public List<Person> getMembers() {
        return members;
    }
}
