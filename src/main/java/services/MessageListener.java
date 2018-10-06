package services;

import messages.NewChatMessage;
import models.Person;

import java.io.IOException;
import java.util.UUID;

public interface MessageListener {
    void onFriendRequest(Person requester);

    void onFriendConfirm(Person sender);

    void onFriendRejection(Person sender);

    void onFriendRemoval(Person sender);

    void onGroupInvitation(UUID groupKey) throws IOException, ClassNotFoundException;

    void onGroupLeave(Person sender, UUID groupKey) throws IOException, ClassNotFoundException;

    void onGroupJoin(Person joiner, UUID groupKey);

    void onChatMessageReceived(NewChatMessage newChatMessage);
}
