package timesplinter.lime.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpInputStream extends InputStream {
    private InputStream inputStream;

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
            var content = this.inputStream.readAllBytes();

            this.inputStream = new ByteArrayInputStream(content);

            return new String(content);
        } catch (IOException e) {
            return null;
        }
    }
}
