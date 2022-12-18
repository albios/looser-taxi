package com.looser_taxi.messages.factory;

import com.looser_taxi.messages.BaseMessage;
import com.looser_taxi.messages.Message;

import java.util.ArrayList;
import java.util.Arrays;

public class MessageFactory {
    public static Message createMessage (String messageText, Message... children) {
        return BaseMessage.builder()
                .message(messageText)
                .children(new ArrayList<>(Arrays.asList(children)))
                .build();
    }
}
