package timesplinter.lime.unit.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timesplinter.lime.http.HttpOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpOutputStreamTest {
    @Test
    public void testWriteWritesStringAsByteArrayToStream() throws IOException {
        var data = "hello world";
        var byteArrayOutputStreamMock = Mockito.mock(ByteArrayOutputStream.class);

        var httpOutputStream = new HttpOutputStream(byteArrayOutputStreamMock);

        httpOutputStream.write(data);

        Mockito.verify(byteArrayOutputStreamMock, Mockito.times(1)).write(data.getBytes());
    }

    @Test
    public void testCloseClosesUnderlyingStream() throws IOException {
        var byteArrayOutputStreamMock = Mockito.mock(ByteArrayOutputStream.class);

        var httpOutputStream = new HttpOutputStream(byteArrayOutputStreamMock);

        httpOutputStream.close();

        Mockito.verify(byteArrayOutputStreamMock, Mockito.times(1)).close();
    }

    @Test
    public void testAvailableReturnsSizeOfUnderlyingStream() {
        var size = 42;
        var byteArrayOutputStreamMock = Mockito.mock(ByteArrayOutputStream.class);

        Mockito.when(byteArrayOutputStreamMock.size()).thenReturn(size);

        var httpOutputStream = new HttpOutputStream(byteArrayOutputStreamMock);

        Assertions.assertEquals(size, httpOutputStream.available());
    }

    @Test
    public void testTransferToWritesBytesOfUnderlyingStreamToNewOutputStream() throws IOException {
        var size = 42;
        var byteArrayOutputStreamMock = Mockito.mock(ByteArrayOutputStream.class);
        var newOutputStreamMock = Mockito.mock(OutputStream.class);
        var httpOutputStream = new HttpOutputStream(byteArrayOutputStreamMock);

        Mockito.when(byteArrayOutputStreamMock.size()).thenReturn(size);

        var actualBytesWritten = httpOutputStream.transferTo(newOutputStreamMock);

        Assertions.assertEquals(size, actualBytesWritten);
        Mockito.verify(byteArrayOutputStreamMock, Mockito.times(1)).writeTo(newOutputStreamMock);
    }
}
