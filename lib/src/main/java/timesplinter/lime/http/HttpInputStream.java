package timesplinter.lime.http;

import java.io.*;

public class HttpInputStream extends FilterInputStream implements HttpInputStreamInterface {
    public HttpInputStream(InputStream in) {
        super(in);
    }

    public String toString() {
        try {
            var content = this.in.readAllBytes();

            this.in = new ByteArrayInputStream(content);

            return new String(content);
        } catch (IOException e) {
            return null;
        }
    }
}
