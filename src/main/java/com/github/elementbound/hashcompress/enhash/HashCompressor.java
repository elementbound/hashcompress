package com.github.elementbound.hashcompress.enhash;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface to specify the hash compressor API.
 */
public interface HashCompressor {
    /**
     * Compress the contents of an input stream.
     *
     * @param in  data source to compress
     * @param out data sink for output
     * @throws IOException in case of IO error
     */
    void compress(InputStream in, OutputStream out) throws IOException;

    /**
     * Decompress the contents of an input stream.
     *
     * @param in  data source to decompress
     * @param out data sink for output
     * @throws IOException in case of IO error
     */
    void decompress(InputStream in, OutputStream out) throws IOException;
}
