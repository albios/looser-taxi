package com.looser_taxi.messages.scanner;

public class StringConsoleUserInputScanner extends BaseConsoleUserInputScanner<String> {
    @Override
    public String scanUserReply() {
        return scanner.next();
    }
}
