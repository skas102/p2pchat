package controllers;

import models.Person;

import java.io.IOException;

public interface MessageListener {
    void onFriendRequest(Person requester);
    void onFriendConfirm(Person sender);
    void onFriendRejection(Person sender);
    void onFriendRemoval(Person sender);
    void onGroupInvitation(String groupKey) throws IOException, ClassNotFoundException;
    void onGroupLeave(Person sender, String groupKey) throws IOException, ClassNotFoundException;
}
