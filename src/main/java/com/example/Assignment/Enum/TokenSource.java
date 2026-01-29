package com.example.Assignment.Enum;


import lombok.Getter;

@Getter
public enum TokenSource {
    EMERGENCY(5),
    PAID(4),
    FOLLOW_UP(3),
    ONLINE(2),
    WALK_IN(1);

    private final int priority;

    TokenSource(int priority) {
        this.priority = priority;
    }
}
