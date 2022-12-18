package com.looser_taxi.messages;

import java.util.List;

public interface Message {
    String getMessage();
    List<Message> getChildren();

    void addChild(Message child);
    void addChildren(Message... children);
}
