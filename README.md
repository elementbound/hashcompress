# Hashcompress #

A joke algorithm with excellent compression ratio.

## Pros/Cons ##

* Very fast compression
* Compression ratio can be easily improved by increasing block size
* Slow decompression
* Unpacked file might be corrupt*

## Usage ##

```sh
$ mvn clean install
...produce an executable jar
$ java -jar target/hashcompress*.jar <compression|decompression> <blockSize> <input> <output>
...compress/decompress file
```

## How does it work ##

During compression, the input is split up into blocks of the given size. These go through a hashing algorithm, which produces a fixed size output. Thus, the compression ratio is determined by the block size and the algorithm's output size. Each block's hash is written to the output as-is.

During decompression, the block hashes are read one by one. For each, the decompression 'algorithm' will guess until it finds a block that yields the same hash value and writes it to the output.