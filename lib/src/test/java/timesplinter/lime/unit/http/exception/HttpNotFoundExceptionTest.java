package timesplinter.lime.unit.http.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.exception.HttpNotFoundException;

public class HttpNotFoundExceptionTest {
    @Test
    public void testWithEmptyConstructor() {
        var exception = new HttpNotFoundException();

        Assertions.assertEquals(404, exception.getStatusCode());
        Assertions.assertNull(exception.getRequest());
        Assertions.assertNull(exception.getCause());
    }

    @Test
    public void testWithRequestConstructor() {
        var requestMock = Mockito.mock(RequestInterface.class);

        var exception = new HttpNotFoundException(requestMock);

        Assertions.assertEquals(404, exception.getStatusCode());
        Assertions.assertSame(requestMock, exception.getRequest());
        Assertions.assertNull(exception.getCause());
    }
}
