package timesplinter.lime.unit.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timesplinter.lime.http.HttpInputStream;

import java.io.IOException;
import java.io.InputStream;

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
    public void testAvailableCallsUnderlyingStream() throws IOException {
        var availableBytes = 42;
        var inputStreamMock = Mockito.mock(InputStream.class);

        Mockito.when(inputStreamMock.available()).thenReturn(availableBytes);

        var httpInputStream = new HttpInputStream(inputStreamMock);

        Assertions.assertEquals(availableBytes, httpInputStream.available());
        Mockito.verify(inputStreamMock, Mockito.times(1)).available();
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
