package com.looser_taxi.messages.scanner;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

public class UserInputScannerFactory {

    @Bean("intScanner")
    @Profile("console")
    public static UserInputScanner<Integer> createIntScanner() {
        return new IntConsoleUserInputScanner();
    }
}
