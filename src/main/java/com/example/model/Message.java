package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Message {
    private String from;
    private String to;
    private String subject;
    private String text;
}
