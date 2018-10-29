pragma solidity ^0.4.25;

contract Mortal {
    address owner;

    constructor () public { owner = msg.sender; }

    function kill() public { if (msg.sender == owner) selfdestruct(owner); }
}

contract NotaryServiceContract {

    struct State {
        Status status;
        bool isSet;
    }

    enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    mapping(bytes32 => State) states;
    mapping(bytes32 => address) recipients;

    function addMessage(bytes32 hash, address recipient) public {
        require(states[hash].isSet == false);
        states[hash] = State(Status.PENDING, true);
        recipients[hash] = recipient;
    }

    function acceptMessage(bytes32 hash) public {
        require(states[hash].isSet == true);
        require(states[hash].status == Status.PENDING);
        require(recipients[hash] == msg.sender);
        states[hash].status = Status.ACCEPTED;
    }

    function rejectMessage(bytes32 hash) public {
        require(states[hash].isSet == true);
        require(states[hash].status == Status.PENDING);
        require(recipients[hash] == msg.sender);
        states[hash].status = Status.REJECTED;
    }

    function getMessageState(bytes32 hash) public returns (Status) {
        return states[hash].status;
    }

}
