package edu.upi.mobprogproject.adapter.data;

/**
 * Created by amaceh on 08/11/17.
 * A class for modelling data in Chat Header
 * Might be replaced somehow if chat really working tho
 */

public class ChatHeader {
    private String nama, chat, jam;

    public ChatHeader() {

    }

    public ChatHeader(String nama, String chat, String jam) {
        this.nama = nama;
        this.chat = chat;
        this.jam = jam;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getNama() {
        return nama;
    }

    public String getChat() {
        return chat;
    }

    public String getJam() {
        return jam;
    }
}
