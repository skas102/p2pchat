package dtos;

import java.io.Serializable;
import java.util.List;

public class GroupDTO implements Serializable {
    private String groupname;
    private List<String> members;

    public GroupDTO(String groupname, List<String> members) {
        this.groupname = groupname;
        this.members = members;
    }

    public String getGroupname() {
        return groupname;
    }

    public List<String> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", groupname, members.toString());
    }

}
