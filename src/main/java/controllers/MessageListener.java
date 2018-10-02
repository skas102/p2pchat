package controllers;

import models.Person;

public interface MessageListener {
    void onFriendRequest(Person requester);
    void onFriendConfirm(Person sender);
    void onFriendRemoval(Person sender);
}
