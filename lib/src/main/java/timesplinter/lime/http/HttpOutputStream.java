package timesplinter.lime.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

final public class HttpOutputStream implements HttpOutputStreamInterface
{
    final private ByteArrayOutputStream outputStream;

    public HttpOutputStream(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(String str) throws IOException
    {
        this.outputStream.write(str.getBytes());
    }

    public long transferTo(OutputStream out) throws IOException
    {
        byte[] bytes = this.outputStream.toByteArray();

        out.write(bytes);
        
        return bytes.length;
    }

    public long available()
    {
        return this.outputStream.size();
    }

    public void close() throws IOException {
        this.outputStream.close();
    }
}
