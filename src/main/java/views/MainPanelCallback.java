package views;

import models.Contact;

public interface MainPanelCallback {
    void ShowContactDetail(Contact contact);

    void RemoveDetail();
}
