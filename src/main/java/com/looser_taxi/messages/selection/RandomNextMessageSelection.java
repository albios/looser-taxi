package com.looser_taxi.messages.selection;

import com.looser_taxi.messages.Message;
import com.looser_taxi.messages.NextMessageSelection;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Qualifier("randomNextSelection")
@RequiredArgsConstructor
public class RandomNextMessageSelection extends NextMessageSelection {

    @Override
    public Message selectNextMessage(Message message) {
        return message.getChildren().get(
                new Random().nextInt(message.getChildren().size()));
    }

}
