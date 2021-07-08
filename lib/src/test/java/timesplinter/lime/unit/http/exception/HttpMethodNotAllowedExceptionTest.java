package timesplinter.lime.unit.http.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.exception.HttpMethodNotAllowedException;

public class HttpMethodNotAllowedExceptionTest {
    @Test
    public void testWithEmptyConstructor() {
        var exception = new HttpMethodNotAllowedException();

        Assertions.assertEquals(405, exception.getStatusCode());
        Assertions.assertNull(exception.getRequest());
        Assertions.assertNull(exception.getCause());
    }

    @Test
    public void testWithRequestConstructor() {
        var requestMock = Mockito.mock(RequestInterface.class);

        var exception = new HttpMethodNotAllowedException(requestMock);

        Assertions.assertEquals(405, exception.getStatusCode());
        Assertions.assertSame(requestMock, exception.getRequest());
        Assertions.assertNull(exception.getCause());
    }
}
