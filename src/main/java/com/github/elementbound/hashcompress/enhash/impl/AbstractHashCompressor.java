package com.github.elementbound.hashcompress.enhash.impl;

import com.github.elementbound.hashcompress.enhash.ReportingHashCompressor;
import com.github.elementbound.hashcompress.enhash.reporting.HashCompressorProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractHashCompressor implements ReportingHashCompressor {
    private final List<Consumer<HashCompressorProgress>> subscribers = new ArrayList<>();

    @Override
    public void onProgress(Consumer<HashCompressorProgress> consumer) {
        subscribers.add(consumer);
    }

    protected void broadcastProgress(HashCompressorProgress progress) {
        subscribers.forEach(consumer -> consumer.accept(progress));
    }
}
