package timesplinter.lime.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpInputStreamTest {
    @Test
    public void testToStringReturnsUnreadContentOfStream() throws IOException {
        var data = "hello world";
        var inputStreamMock = Mockito.mock(InputStream.class);

        Mockito.when(inputStreamMock.readAllBytes()).thenReturn(data.getBytes());

        var httpInputStream = new HttpInputStream(inputStreamMock);

        Assertions.assertEquals(data, httpInputStream.toString());
        Mockito.verify(inputStreamMock, Mockito.times(1)).readAllBytes();
    }

    @Test
    public void testReadCallsUnderlyingStream() throws IOException {
        var sampleByte = 4;
        var inputStreamMock = Mockito.mock(InputStream.class);

        Mockito.when(inputStreamMock.read()).thenReturn(sampleByte);

        var httpInputStream = new HttpInputStream(inputStreamMock);

        Assertions.assertEquals(sampleByte, httpInputStream.read());
        Mockito.verify(inputStreamMock, Mockito.times(1)).read();
    }

    @Test
    public void testToStringReturnsNullOnIOExceptionOfUnderlyingStream() throws IOException {
        var inputStreamMock = Mockito.mock(InputStream.class);

        Mockito.when(inputStreamMock.readAllBytes()).thenThrow(new IOException());

        var httpInputStream = new HttpInputStream(inputStreamMock);

        Assertions.assertNull(httpInputStream.toString());
        Mockito.verify(inputStreamMock, Mockito.times(1)).readAllBytes();
    }
}
