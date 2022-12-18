package com.looser_taxi.messages.scanner;

public class IntConsoleUserInputScanner extends BaseConsoleUserInputScanner<Integer> {

    @Override
    public Integer scanUserReply() {
        return scanner.nextInt();
    }
}
