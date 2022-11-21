package com.example.speax.messages;

public class MessagesList {
    private String name, surname, lastMessage, profileImage;
    private int unseenMessages;

    public MessagesList(String name, String surname, String lastMessage, String profileImage, int unseenMessages) {
        this.name = name;
        this.surname = surname;
        this.lastMessage = lastMessage;
        this.profileImage = profileImage;
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

    public String getProfileImage() {
        return profileImage;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }
}
