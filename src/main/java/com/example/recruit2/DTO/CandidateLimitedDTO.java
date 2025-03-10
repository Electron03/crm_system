package com.example.recruit2.DTO;

public class CandidateLimitedDTO {
    private int id;
    private String fullname;
    private String meetdate;
    private String dateNote;

    // Геттеры и сеттеры

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMeetdate() {
        return meetdate;
    }

    public void setMeetdate(String meetdate) {
        this.meetdate = meetdate;
    }

    public String getDateNote() {
        return dateNote;
    }

    public void setDateNote(String dateNote) {
        this.dateNote = dateNote;
    }
}
