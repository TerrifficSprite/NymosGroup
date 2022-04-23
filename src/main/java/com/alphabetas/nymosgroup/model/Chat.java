package com.alphabetas.nymosgroup.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Data
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String code;
    String chat_name;
    String chatInvite;
    Integer nums_of_joins;

    public Chat(String code, String chat_name, String chatInvite, Integer nums_of_joins) {
        this.code = code;
        this.chat_name = chat_name;
        this.chatInvite = chatInvite;
        this.nums_of_joins = nums_of_joins;
    }
}