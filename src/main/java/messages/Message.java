package messages;

import java.io.Serializable;

public interface Message extends Serializable {
    MessageType getType();

    String getSenderUsername();
}
