package timesplinter.lime.http;

import java.io.IOException;
import java.io.OutputStream;

public interface HttpOutputStreamInterface
{
    public void write(String str) throws IOException;

    public long transferTo(OutputStream out) throws IOException;

    public long available();
}
