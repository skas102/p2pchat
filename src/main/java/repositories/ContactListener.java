package repositories;

import models.Person;

public interface ContactListener {
    void onIncomingFriendRequest(Person p);
    void onMyFriendRequestRemoved(Person p);
    void onContactListUpdated();
}
