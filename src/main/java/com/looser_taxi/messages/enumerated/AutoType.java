package com.looser_taxi.messages.enumerated;

import lombok.Getter;

public enum AutoType {
    SLAVUTA("Славута зі знижкою 55%"),
    STANDART("Стандарт"),
    COMFORT("Комфорт (з додатковим місцем для ваших колінок)"),
    PREMIUM("Преміум (не думайте відкривати двері собі самостійно, для цього є водій)");

    @Getter
    private final String details;

    AutoType(String typeDetails) {
        this.details = typeDetails;
    }
}
