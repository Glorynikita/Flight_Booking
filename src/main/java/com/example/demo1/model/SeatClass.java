package com.example.demo1.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SeatClass {
    BUSINESS,
    ECONOMY;

    @JsonCreator
    public static SeatClass fromValue(String input) {
        if(input == null) return null;
        switch (input.trim()) {
            case "business": case "Business": case "BUSINESS": return BUSINESS;
            case "economy": case "Economy": case "ECONOMY": return ECONOMY;
            default: throw new IllegalArgumentException("Invalid SeatClass value: " + input);

        }
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
