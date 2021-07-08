package timesplinter.lime.unit.http.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.exception.HttpException;

public class HttpExceptionTest {
    @Test
    public void testWithStatusCodeAndRequest() {
        var requestMock = Mockito.mock(RequestInterface.class);

        var exception = new HttpException(404, requestMock);

        Assertions.assertEquals(404, exception.getStatusCode());
        Assertions.assertSame(requestMock, exception.getRequest());
    }

    @Test
    public void testWithStatusCode() {
        var exception = new HttpException(404);

        Assertions.assertEquals(404, exception.getStatusCode());
    }

    @Test
    public void testWithStatusCodeAndRequestAndCause() {
        var requestMock = Mockito.mock(RequestInterface.class);
        var causeMock = Mockito.mock(Exception.class);

        var exception = new HttpException(404, requestMock, causeMock);

        Assertions.assertEquals(404, exception.getStatusCode());
        Assertions.assertSame(requestMock, exception.getRequest());
        Assertions.assertSame(causeMock, exception.getCause());
    }
}
