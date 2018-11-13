package com.github.elementbound.hashcompress.enhash;

import com.github.elementbound.hashcompress.enhash.reporting.HashCompressorProgress;

import java.util.function.Consumer;

public interface ReportingHashCompressor extends HashCompressor {
    void onProgress(Consumer<HashCompressorProgress> consumer);
}
