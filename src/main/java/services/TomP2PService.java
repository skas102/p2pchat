package services;

import dtos.*;
import messages.GroupInvitationMessage;
import messages.GroupJoinMessage;
import messages.GroupLeaveMessage;
import messages.Message;
import models.BootstrapPeer;
import models.Client;
import models.Group;
import models.Person;
import net.tomp2p.dht.FutureGet;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;
import net.tomp2p.storage.Data;
import util.ChatLogger;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.UUID;

public class TomP2PService implements P2PService {
    private Client client;
    private BootstrapPeer bootstrapPeer;
    private PeerDHT peerDHT;
    private Map<String, PeerAddress> peers;

    public TomP2PService(Client client, BootstrapPeer bootstrapPeer) {
        this.client = client;
        this.bootstrapPeer = bootstrapPeer;
    }

    @Override
    public PersonDTO start() throws IOException, InterruptedException {
        Number160 uniqueNodeID = Number160.createHash(client.getUniqueID().toString());
        peerDHT = new PeerBuilderDHT(new PeerBuilder(uniqueNodeID).ports(client.getPort()).start()).start();
        ChatLogger.info("Peer started at port " + client.getPort());

        if (bootstrapPeer != null) {
            ChatLogger.info(String.format(
                    "Start bootstrapping using the peer at %s %d",
                    bootstrapPeer.getIP(), bootstrapPeer.getPort()));
            peerDHT.peer()
                    .bootstrap()
                    .inetAddress(InetAddress.getByName(bootstrapPeer.getIP()))
                    .ports(bootstrapPeer.getPort())
                    .start()
                    .awaitListeners();
            ChatLogger.info("Bootstrapping completed");
        }

        return updatePersonInfoOnDHT();
    }

    @Override
    public void shutdown() {
        peerDHT.shutdown();
    }

    private PersonDTO updatePersonInfoOnDHT() throws IOException {
        PersonDTO personDTO = new PersonDTO(
                client.getUsername(),
                peerDHT.peerAddress());

        // todo on collision use domain key, e.g. UUID
        peerDHT.put(Number160.createHash(client.getUsername()))
                .data(new Data(personDTO))
                .start()
                .awaitUninterruptibly();
        ChatLogger.info("Person info is updated on DHT: " + personDTO);
        return personDTO;
    }

    @Override
    public void storeGroupInfoOnDHT(Group group) throws IOException {

        GroupDTO groupDTO = group.createGroupDTO();

        peerDHT.put(Number160.createHash(group.getUniqueId().toString()))
                .data(new Data(groupDTO))
                .start()
                .awaitUninterruptibly();
        ChatLogger.info("Group info is updated on DHT: " + groupDTO);
    }

    @Override
    public PersonDTO getPerson(String username) throws IOException, ClassNotFoundException {
        FutureGet futureGet = peerDHT.get(Number160.createHash(username))
                .start();
        futureGet.awaitUninterruptibly(); // todo This is a blocking operation, refactor code async
        return (PersonDTO) futureGet.data().object();
    }

    @Override
    public GroupDTO getGroup(UUID groupKey) throws IOException, ClassNotFoundException {
        FutureGet futureGet = peerDHT.get(Number160.createHash(groupKey.toString()))
                .start();
        futureGet.awaitUninterruptibly();
        return (GroupDTO) futureGet.data().object();
    }

    @Override
    public void sendDirectMessage(PersonDTO receiver, Message message) {
        peerDHT.peer()
                .sendDirect(receiver.getPeerAddress())
                .object(message)
                .start();
    }

    @Override
    public void receiveMessage(MessageListener listener) {
        ChatLogger.info("Started to listen for messages");

        peerDHT.peer().objectDataReply((senderAddress, request) -> {
            ChatLogger.info("Message received: Sender - " + senderAddress + " Message: " + request);
            try {
                Message m = (Message) request;
                Person sender = new Person(m.getSenderUsername(), senderAddress);
                switch (m.getType()) {
                    case FRIEND_REQUEST: {
                        listener.onFriendRequest(sender);
                        break;
                    }
                    case FRIEND_CONFIRM: {
                        listener.onFriendConfirm(sender);
                        break;
                    }
                    case FRIEND_REJECTION: {
                        listener.onFriendRejection(sender);
                        break;
                    }
                    case FRIEND_REMOVAL: {
                        listener.onFriendRemoval(sender);
                        break;
                    }
                    case GROUP_INVITATION: {
                        GroupInvitationMessage invitationMessage = (GroupInvitationMessage) m;
                        listener.onGroupInvitation(invitationMessage.getGroupKey());
                        break;
                    }
                    case GROUP_LEAVE: {
                        GroupLeaveMessage leaveMessage = (GroupLeaveMessage) m;
                        listener.onGroupLeave(sender, leaveMessage.getGroupKey());
                        break;
                    }
                    case GROUP_JOIN: {
                        GroupJoinMessage joinMessage = (GroupJoinMessage) m;
                        listener.onGroupJoin(Person.create(joinMessage.getJoiner()), joinMessage.getGroupKey());
                        break;
                    }
                    case CHAT_MESSAGE: {
                        ChatMessage message = (ChatMessage) m;
                        listener.onChatMessageReceived(message);
                    }
                    default:
                        System.out.println("Sender " + senderAddress.inetAddress() + "Message: " + request);
                }
                return request;
            } catch (Exception e) {
                ChatLogger.error("Error occured on processing incoming message: " + e.getMessage());
            }
            return request;
        });
    }
}
