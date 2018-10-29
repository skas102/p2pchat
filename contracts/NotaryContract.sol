pragma solidity ^0.4.0;

contract NotaryContract{
    mapping(address => string) public messages;

    function sendMessage(string message) public {
        messages[msg.sender] = message;
    }
}