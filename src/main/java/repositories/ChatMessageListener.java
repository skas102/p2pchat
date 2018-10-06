package repositories;

import models.ChatMessage;
import models.Contact;

public interface ChatMessageListener {
    void onMessageReceived(Contact c, ChatMessage m);
}