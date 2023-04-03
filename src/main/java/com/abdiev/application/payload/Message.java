package com.abdiev.application.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String message;
    private String sender;
    private String time;

    public Message(String message, String sender) {
        this.message = message;
        this.sender = sender;
        this.time = new SimpleDateFormat("HH:mm").format(new Date());
    }
}