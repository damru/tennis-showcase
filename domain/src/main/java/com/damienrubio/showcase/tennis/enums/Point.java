package com.damienrubio.showcase.tennis.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by damien on 18/11/2016.
 */
@RequiredArgsConstructor
public enum Point {
    NUL("0"),
    QUINZE("15"),
    TRENTE("30"),
    QUARANTE("40"),
    DEUCE("A"),
    AVANTAGE("AV");

    @Getter
    private final String score;
}