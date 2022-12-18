package com.looser_taxi.messages.selection;

import com.looser_taxi.messages.Message;
import com.looser_taxi.messages.NextMessageSelection;
import com.looser_taxi.messages.output.MessageOutputService;
import com.looser_taxi.messages.scanner.UserInputScanner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("userOptionSelection")
public class UserOptionSelection extends NextMessageSelection {

    @Qualifier("outputService")
    private final MessageOutputService outputService;

    @Qualifier("intScannerService")
    private final UserInputScanner<Integer> inputScanner;

    public UserOptionSelection(MessageOutputService outputService, UserInputScanner<Integer> inputScanner) {
        this.outputService = outputService;
        this.inputScanner = inputScanner;
    }

    @Override
    public Message selectNextMessage(Message message) {
        outputService.ignorePrintingNextMessage();
        return super.selectNextMessage(message);
    }

    @Override
    protected void processUserReply(Message message) {
        int counter = 1;
        for (Message child : message.getChildren()) {
            outputService.print(counter + " - " + child.getMessage());
            counter ++;
        }
    }

    @Override
    protected int getNextMessageIndex() {
        return inputScanner.scanUserReply() - 1;
    }
}
