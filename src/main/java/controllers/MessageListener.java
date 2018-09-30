package controllers;

import dtos.FriendRequestMessage;

public interface MessageListener {
    void onFriendRequest(FriendRequestMessage m);
}
