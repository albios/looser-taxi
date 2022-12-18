package com.looser_taxi.messages.config;

import com.looser_taxi.messages.Message;
import com.looser_taxi.messages.NextMessageSelection;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class GameConfigGameConfig {
    public static float FAILURE_POSSIBILITY_VALUE = 0.5f;

    public static Map<Message, NextMessageSelection> selectionTypeByMessage = new HashMap<>();
}
