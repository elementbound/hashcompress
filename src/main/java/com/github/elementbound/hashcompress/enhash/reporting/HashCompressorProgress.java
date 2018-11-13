package com.github.elementbound.hashcompress.enhash.reporting;

public class HashCompressorProgress {
    private final int blocksDone;
    private final boolean done;

    public HashCompressorProgress(int blocksDone, boolean done) {
        this.blocksDone = blocksDone;
        this.done = done;
    }

    public int getBlocksDone() {
        return blocksDone;
    }

    public boolean isDone() {
        return done;
    }
}
