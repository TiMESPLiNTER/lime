package timesplinter.lime.http;

import java.io.*;

final public class HttpOutputStream extends ByteArrayOutputStream implements HttpOutputStreamInterface
{
    public void write(String str) throws IOException
    {
        this.write(str.getBytes());
    }

    public long transferTo(OutputStream out) throws IOException
    {
        byte[] bytes = super.toByteArray();

        out.write(bytes);
        
        return bytes.length;
    }

    public long available()
    {
        return super.size();
    }
}
