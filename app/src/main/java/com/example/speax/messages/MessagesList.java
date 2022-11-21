package com.example.speax.messages;

public class MessagesList {
    private String name, surname, lastMessage;
    private int unseenMessages;

    public MessagesList(String name, String surname, String lastMessage, int unseenMessages) {
        this.name = name;
        this.surname = surname;
        this.lastMessage = lastMessage;
        this.unseenMessages = unseenMessages;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }
}
