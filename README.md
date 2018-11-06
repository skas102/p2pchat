P2PChat - Distributed Chat Application
=======================================

Usage: `java Main <client-port> <bootstrap-peer-ip> <bootstrap-peer-port>`

All three parameters are optional. Default client-port is 4000.

Start the application
---------------------

Start the first application as follows.

    java Main

> It starts the application at port 4000. Since it is the first peer, there is no bootstrap peer required.

Then, start another application with the bootstrap peer.

    java Main 4001 127.0.0.1 4000

> Second application starts at port 4001 and communicates the peer at 127.0.0.1:4000 for bootstrapping.

Username and Account
---------------------

Username cannot be chosen freely. It is determined by the port as `User<port>` (e.g. User4000).
Therefore, **DO NOT** start the clients with the same port.

For the following usernames, there are already wallets available to interact with the Ropsten Ethernet test network.
* User4000
* User4001
* User4002
* User4003 

If you choose other ports, you have to create wallets by yourself and put them here: `wallets/wallet_User<port>.json`

User Interface
--------------

![User Interface](./docs/ui.PNG)

### Add a Friend

1. Click on the friend icon and enter the username of your friend (e.g. User4001)
2. The friend request appears in the incoming requests. When you click on the item, a popup appears to either accept or reject the friend request.

![Friend Request](./docs/friend-request.PNG)

3. If the request is accepted, it appears in the contact list. You can start chatting with him by selecting it.
4. You can remove the friend by right clicking the contact item.

### Create a Group

1. Click on the group icon. 
2. Enter the group name and select the members you want to add.
3. Group can also be removed by right clicking the group.

### Send Notary Messages

![Notary messages](https://user-images.githubusercontent.com/3751899/47953523-32beb600-df7f-11e8-9c7a-ccdca33aee43.png)

1. Notary messages can be sent just like normal messages
2. When a notary message is submitted, it will be first added to the blockchain (Contract: https://ropsten.etherscan.io/address/0x59cd1f59b9cf96baaeea7a1b82e73d1f5d1831ad)
3. Only if the transaction is successful, the message will be sent to the selected friend via p2p.
4. The recipient can accept or reject the message by right clicking on it
5. The status checks by pull mechanism, so the sender should right click on the message to check the status.
