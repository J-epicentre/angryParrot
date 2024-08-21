package com.project2.angry_parrot.ws;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoom {
    private String id;
    private String name;

    public ChatRoom(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
