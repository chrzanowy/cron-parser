package com.chrzanowy.cron.elements;

import lombok.Getter;

@Getter
public enum Element {

    MINUTE(0, 59),
    HOUR(0, 23),
    DAY(1, 31),
    MONTH(1, 12),
    DAY_OF_WEEK(1, 7);

    private final int lowerBoundary;

    private final int topBoundary;

    Element(int lowerBoundary, int topBoundary) {
        this.lowerBoundary = lowerBoundary;
        this.topBoundary = topBoundary;
    }

    public boolean isInValidRange(int intValue) {
        return intValue >= lowerBoundary && intValue <= topBoundary;
    }

}
