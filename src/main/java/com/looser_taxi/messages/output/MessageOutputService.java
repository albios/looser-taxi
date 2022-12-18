package com.looser_taxi.messages.output;

import com.looser_taxi.messages.Message;

public interface MessageOutputService {
    void processMessage(Message message);
    void print(String message);

    void ignorePrintingNextMessage();
}
