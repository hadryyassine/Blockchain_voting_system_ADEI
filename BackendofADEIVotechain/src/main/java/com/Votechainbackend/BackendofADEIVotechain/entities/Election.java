package com.example.VotingSystem.entities;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "elections")

public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "year")
    private int year;
    @Column(name = "proposal_start_date")

    private Date pstartDate;

    @Column(name = "proposal_end_date")

    private Date pendDate;

    @Column(name = "Election_start_date")

    private Date startDate;

    @Column(name = "Duration")
    private int duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getPstartDate() {
        return pstartDate;
    }

    public void setPstartDate(Date pstartDate) {
        this.pstartDate = pstartDate;
    }

    public Date getPendDate() {
        return pendDate;
    }

    public void setPendDate(Date pendDate) {
        this.pendDate = pendDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
       isOpen = open;
    }

    @Column(name = "is_open")
    private boolean isOpen;


}
