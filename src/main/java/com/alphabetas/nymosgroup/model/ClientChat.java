package com.alphabetas.nymosgroup.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Data
@Entity
public class ClientChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String chat;
    String client;
    String chatName;
    String chatInvite;

    public ClientChat(String chat, String client, String chatName, String chatInvite) {
        this.chat = chat;
        this.client = client;
        this.chatName = chatName;
        this.chatInvite = chatInvite;
    }
}
