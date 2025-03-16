package com.memorycards;

public class Tracker {
    private int moves = 0;
    public int timeLimit = 120; // 120 seconds -> 2 minutes
    public int countDown = 60;
    public int timePlayed = 0;
    public int trackMoves(Card firstCard, Card secondCard) {
        if (firstCard != null && secondCard != null)
            moves++;
        return moves;
    }

    public int getTimer() {
       if (countDown <= 0)
           countDown = 60 ;
       return countDown;
    }
}
