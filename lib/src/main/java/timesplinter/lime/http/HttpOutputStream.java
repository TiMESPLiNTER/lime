package timesplinter.lime.http;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

final public class HttpOutputStream extends FilterOutputStream implements HttpOutputStreamInterface
{
    public HttpOutputStream(ByteArrayOutputStream out) {
        super(out);
    }

    public void write(String str) throws IOException
    {
        this.out.write(str.getBytes());
    }

    public long transferTo(OutputStream out) throws IOException
    {
        ((ByteArrayOutputStream) this.out).writeTo(out);
        
        return this.available();
    }

    public long available()
    {
        return ((ByteArrayOutputStream) this.out).size();
    }
}
