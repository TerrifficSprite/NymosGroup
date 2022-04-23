package com.alphabetas.nymosgroup.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
@Data
@Entity
public class Messages {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chat;
    private String message;
    private String author;
    private String date;
    @Transient
    private String currentAuthor;
    public Messages(String chat, String message, String author) {
        this.chat = chat;
        this.message = message;
        this.author = author;
        this.date = getCurrentDate();
    }

    public Messages(String chat, String message, String author, String currentAuthor) {
        this.chat = chat;
        this.message = message;
        this.author = author;
        this.date = getCurrentDate();
        this.currentAuthor = currentAuthor;
    }

    private static String getCurrentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date_d = new Date();
        return formatter.format(date_d);
    }
}
