package timesplinter.lime.http;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;

public interface HttpOutputStreamInterface extends Flushable, Closeable
{
    void write(String str) throws IOException;

    void write(int b) throws IOException;

    void write(byte[] bytes) throws IOException;

    long transferTo(OutputStream out) throws IOException;

    long available();
}
