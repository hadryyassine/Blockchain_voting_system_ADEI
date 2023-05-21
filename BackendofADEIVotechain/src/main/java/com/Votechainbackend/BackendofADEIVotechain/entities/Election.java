package com.Votechainbackend.BackendofADEIVotechain.entities;


import javax.persistence.*;
import java.util.Date;

import java.util.List;


@Entity
@Table(name = "elections")

public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "year")
    private int year;
    @Column(name = "start_date")

    private Date startDate;

    @Column(name = "end_date")

    private Date endDate;

    @Column(name = "is_open")
    private boolean isOpen;


    // Getters and setters

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public Election() {

    }

    public Election(Long id, int year, Date startDate, Date endDate, boolean isOpen) {
        this.id = id;
        this.year = year;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isOpen = isOpen;
    }




}
