package com.chrzanowy.cron.operator;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Operator {
    ANY('*'),
    SEPARATOR(','),
    RANGE('-'),
    STEP('/');

    private final char operatorSign;

    Operator(char operatorSign) {
        this.operatorSign = operatorSign;
    }

    public static boolean supports(char c) {
        return Arrays.stream(Operator.values()).anyMatch(x -> x.operatorSign == c);
    }

    public static Operator parse(char c) {
        return Arrays.stream(Operator.values()).filter(x -> x.getOperatorSign() == c).findFirst().orElseThrow();
    }
}
