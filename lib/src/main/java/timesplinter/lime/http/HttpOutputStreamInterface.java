package timesplinter.lime.http;

import java.io.IOException;
import java.io.OutputStream;

public interface HttpOutputStreamInterface
{
    void write(String str) throws IOException;

    long transferTo(OutputStream out) throws IOException;

    long available();

    void close() throws IOException;
}
