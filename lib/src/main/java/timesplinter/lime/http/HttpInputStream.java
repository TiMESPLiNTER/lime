package timesplinter.lime.http;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

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
