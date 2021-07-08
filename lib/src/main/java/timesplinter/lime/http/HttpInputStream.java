package timesplinter.lime.http;

import java.io.IOException;
import java.io.InputStream;

public class HttpInputStream extends InputStream {
    final private InputStream inputStream;

    public HttpInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public int read() throws IOException {
        return this.inputStream.read();
    }

    @Override
    public int available() throws IOException {
        return this.inputStream.available();
    }

    public String toString() {
        try {
            return new String(this.inputStream.readAllBytes());
        } catch (IOException e) {
            return null;
        }
    }
}
