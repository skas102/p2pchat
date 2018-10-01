package repositories;

import models.Person;

public interface ContactListener {
    void onIncomingFriendRequest(Person p);
}
