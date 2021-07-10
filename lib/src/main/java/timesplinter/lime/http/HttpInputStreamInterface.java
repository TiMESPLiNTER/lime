package timesplinter.lime.http;

import java.io.Closeable;
import java.io.IOException;

public interface HttpInputStreamInterface extends Closeable {
    int read() throws IOException;

    int read(byte b[]) throws IOException;

    int read(byte b[], int off, int len) throws IOException;

    long skip(long n) throws IOException;

    int available() throws IOException;

    void close() throws IOException;

    void mark(int readLimit);

    void reset() throws IOException;

    boolean markSupported();

    String toString();
}
