package com.looser_taxi.messages;

public abstract class NextMessageSelection {

    public Message selectNextMessage(Message message) {
        processUserReply(message);
        int nextMessageIndex = getNextMessageIndex();
        return message.getChildren().get(nextMessageIndex);
    }

    protected void processUserReply(Message message) {}

    protected int getNextMessageIndex() {
        return 0;
    }

}
