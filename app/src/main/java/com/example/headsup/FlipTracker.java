package com.example.headsup;

public class FlipTracker {
    private int heads = 0;
    private int tails = 0;

    public boolean flip() {
        boolean isHeads = Math.random() < 0.5;
        if (isHeads) heads++;
        else tails++;
        return isHeads;
    }

    public int getHeads() {
        return heads;
    }

    public int getTails() {
        return tails;
    }

    public void reset() {
        heads = 0;
        tails = 0;
    }

    public void setHeads(int heads) {
        this.heads = heads;
    }

    public void setTails(int tails) {
        this.tails = tails;
    }
}