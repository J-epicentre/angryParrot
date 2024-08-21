package com.project2.angry_parrot.ws;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private Map<String, ChatRoom> chatRooms = new HashMap<>();
    private Map<String, String> sessionIdToUsername = new HashMap<>();

    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoom createRoom(String name) {
        String roomId = String.valueOf(chatRooms.size() + 1);
        ChatRoom chatRoom = new ChatRoom(roomId, name);
        chatRooms.put(roomId, chatRoom);
        return chatRoom;
    }

    public void addUser(String sessionId, String username) {
        sessionIdToUsername.put(sessionId, username);
    }
}
