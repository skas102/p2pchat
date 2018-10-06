package dtos;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class GroupDTO implements Serializable {
    private String groupname;
    private List<String> members;
    private UUID groupKey;

    public GroupDTO(String groupname, List<String> members, UUID groupKey) {
        this.groupname = groupname;
        this.members = members;
        this.groupKey = groupKey;
    }

    public String getGroupname() {
        return groupname;
    }

    public List<String> getMembers() {
        return members;
    }

    public UUID getGroupKey() {
        return groupKey;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", groupname, members.toString());
    }

}
