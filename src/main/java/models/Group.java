package models;

import dtos.GroupDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Group implements Contact {

    private String name;
    private List<Person> members;
    private UUID uniqueID;

    public Group(String name) {
        this.name = name;
        this.members = new ArrayList<>();
        this.uniqueID = UUID.randomUUID();
    }

    public Group(String name, UUID uuid, List<Person> members) {
        this.name = name;
        this.members = members;
        this.uniqueID = uuid;
    }

    private List<String> getMemberNames() {
        List<String> memberNames = new ArrayList<>();
        for (Person p : members) {
            memberNames.add(p.getName());
        }
        return memberNames;
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

    public UUID getUniqueId() {
        return uniqueID;
    }

    public void leave(Person person) {
        members.remove(person);
    }

    public void join(Person person) {
        members.add(person);
    }

    public List<Person> getMembers() {
        return members;
    }

    public GroupDTO createGroupDTO() {
        return new GroupDTO(name, getMemberNames(), getUniqueId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Person)) {
            return false;
        }
        Group other = (Group) obj;
        return uniqueID.equals(other.uniqueID);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + uniqueID.hashCode();
        return result;
    }
}
