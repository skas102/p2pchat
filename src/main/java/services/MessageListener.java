package services;

import dtos.ChatMessage;
import models.Contact;
import models.Person;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface MessageListener {
    void onFriendRequest(Person requester);

    void onFriendConfirm(Person sender);

    void onFriendRejection(Person sender);

    void onFriendRemoval(Person sender);

    void onGroupInvitation(UUID groupKey) throws IOException, ClassNotFoundException;

    void onGroupLeave(Person sender, UUID groupKey) throws IOException, ClassNotFoundException;

    void onGroupJoin(Person joiner, UUID groupKey);

    void onChatMessageReceived(ChatMessage message);
}
