package com.example.VotingSystem.services;

public class ElectionAlreadyExist extends Exception{
    public ElectionAlreadyExist(String s){
        super(s);
    }
}
