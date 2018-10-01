package controllers;

import models.Person;

import java.io.IOException;

public interface MessageListener {
    void onFriendRequest(Person requester);
    void onFriendConfirm(Person confirmer);
    void onGroupInvitation(String groupKey) throws IOException, ClassNotFoundException;
    void onGroupLeave(String groupKey) throws IOException, ClassNotFoundException;
}
