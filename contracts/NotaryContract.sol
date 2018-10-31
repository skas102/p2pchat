pragma solidity ^0.4.25;

contract NotaryContract {

    address owner;

    struct State {
        Status status;
        bool isSet;
    }

    enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    event StateChanged(
        address accepter,
        bytes32 msgHash,
        Status state
    );

    mapping(bytes32 => State) states;
    mapping(address => bytes32) recipients;

    constructor () public { owner = msg.sender; }

    function kill() public { if (msg.sender == owner) selfdestruct(owner); }

    function addMessage(bytes32 hash) public {
        require(states[hash].isSet == false);
        states[hash] = State(Status.PENDING, true);
    }

    function acceptMessage(bytes32 hash) public {
        require(states[hash].isSet == true);
        require(states[hash].status == Status.PENDING);
        recipients[msg.sender] = hash;
        states[hash].status = Status.ACCEPTED;
        emit StateChanged(msg.sender, hash, Status.ACCEPTED);
    }

    function rejectMessage(bytes32 hash) public {
        require(states[hash].isSet == true);
        require(states[hash].status == Status.PENDING);
        recipients[msg.sender] = hash;
        states[hash].status = Status.REJECTED;
        emit StateChanged(msg.sender, hash, Status.REJECTED);
    }

    function getMessageState(bytes32 hash) public view returns (Status) {
        return states[hash].status;
    }

}