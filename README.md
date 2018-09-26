P2PChat - Distributed Chat Application
=======================================

Usage: `java Main <client-port> <bootstrap-peer-ip> <bootstrap-peer-port>`

All three parameters are optional. Default client-port is 4000.

Start the application
---------------------

* Start the first application as follows.

    java Main

> It starts the application at port 4000. Since it is the first peer, there is no bootstrap peer.

* Then, start another application.

    java Main 4001 127.0.0.1 4000

Second application starts at port 4001 and communicates the peer at 127.0.0.1:4000 for bootstrapping.