package com.looser_taxi.messages.config;

import com.looser_taxi.messages.output.ConsoleMessageOutputService;
import com.looser_taxi.messages.output.MessageOutputService;
import com.looser_taxi.messages.scanner.IntConsoleUserInputScanner;
import com.looser_taxi.messages.scanner.StringConsoleUserInputScanner;
import com.looser_taxi.messages.scanner.UserInputScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class BeanConfig {

    @Bean("outputService")
    @Profile("console")
    public MessageOutputService consoleOutputService() {
        return new ConsoleMessageOutputService();
    }

    @Bean("intScannerService")
    @Profile("console")
    public UserInputScanner<Integer> consoleIntScannerService() {
        return new IntConsoleUserInputScanner();
    }

    @Bean("stringScannerService")
    @Profile("console")
    public UserInputScanner<String> consoleStringScannerService() {
        return new StringConsoleUserInputScanner();
    }
}
