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