package com.looser_taxi.messages.selection;

import com.looser_taxi.messages.Message;
import com.looser_taxi.messages.scanner.UserInputScanner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("stringInputStrictlyNextSelection")
public class StringInputStrictNextMessageSelection extends StrictNextMessageSelection {

    private final UserInputScanner<String> inputScanner;

    public StringInputStrictNextMessageSelection(
            @Qualifier("stringScannerService") UserInputScanner<String> inputScanner) {
        this.inputScanner = inputScanner;
    }

    @Override
    public Message selectNextMessage(Message message) {
        return super.selectNextMessage(message);
    }

    @Override
    protected void processUserReply(Message message) {
        inputScanner.scanUserReply();
    }
}
