package com.looser_taxi.messages.selection;

import com.looser_taxi.messages.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Qualifier("randomWaitingTimeRandomNextSelection")
@RequiredArgsConstructor
public class RandomWaitingTimeRandomNextMessageSelection extends RandomNextMessageSelection {

    @SneakyThrows
    @Override
    public Message selectNextMessage(Message message) {
        Thread.sleep(new Random().nextInt(5) * 1000);
        return super.selectNextMessage(message);
    }
}
