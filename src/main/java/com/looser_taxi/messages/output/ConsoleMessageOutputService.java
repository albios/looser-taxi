package com.looser_taxi.messages.output;

import com.looser_taxi.messages.Message;
import com.looser_taxi.messages.NextMessageSelection;
import com.looser_taxi.messages.config.GameConfig;

import java.util.Map;

public class ConsoleMessageOutputService implements MessageOutputService {

    private final Map<Message, NextMessageSelection> selectionTypeByMessage = GameConfig.selectionTypeByMessage;

    private boolean ignoreNextPrinting;

    @Override
    public void processMessage(Message message) {
        if (message == null) return;
        if (ignoreNextPrinting) ignoreNextPrinting = false;
        else print(message.getMessage());
        NextMessageSelection selector = selectionTypeByMessage.get(message);
        if (selector != null) {
            Message nextToCall = null;
            try {
                nextToCall = selector.selectNextMessage(message);
            } catch (IndexOutOfBoundsException e) {
                print("Будь-ласка, введіть значення, що не виходить за рамки допустимих");
                processMessage(message);
            }
            processMessage(nextToCall);
        }
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public void ignorePrintingNextMessage() {
        ignoreNextPrinting = true;
    }
}
