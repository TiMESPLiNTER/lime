package timesplinter.lime.http;

import java.io.*;

final public class HttpOutputStream extends PipedOutputStream
{
    final private PipedInputStream inputStream;

    public HttpOutputStream(PipedInputStream inputStream) throws IOException
    {
        super(inputStream);

        this.inputStream = inputStream;
    }

    public static HttpOutputStream create() throws IOException
    {
        return new HttpOutputStream(new PipedInputStream());
    }

    public void write(String str) throws IOException
    {
        super.write(str.getBytes());
    }

    public long transferTo(OutputStream out) throws IOException
    {
        // We need to close the output stream first, for whatever reason,
        // to be able to transfer the input stream to the target
        super.close();
        long bytes = this.inputStream.transferTo(out);
        super.connect(this.inputStream);
        
        return bytes;
    }
    
    public long available() throws IOException
    {
        return this.inputStream.available();
    }

    public void close() throws IOException
    {
        super.close();
        this.inputStream.close();
    }
}
