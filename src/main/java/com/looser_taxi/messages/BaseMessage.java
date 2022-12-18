package com.looser_taxi.messages;

import lombok.Builder;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuperBuilder
public class BaseMessage implements Message {

    private String message;

    @Builder.Default
    private List<Message> children = new ArrayList<>();

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public List<Message> getChildren() {
        return children;
    }

    @Override
    public void addChild(Message child) {
        children.add(child);
    }

    @Override
    public void addChildren(Message... children) {
        this.children.addAll(Arrays.asList(children));
    }
}
