package timesplinter.lime.unit.middleware;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timesplinter.lime.http.HttpOutputStreamInterface;
import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseFactoryInterface;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.http.exception.HttpException;
import timesplinter.lime.middleware.ErrorMiddleware;
import timesplinter.lime.router.RequestHandlerInterface;

public class ErrorMiddlewareTest {
    @Test
    public void testItGeneratesErrorResponseForGenericException() throws Exception {
        var requestMock = Mockito.mock(RequestInterface.class);
        var responseMock = Mockito.mock(ResponseInterface.class);
        var nextRequestHandlerMock = Mockito.mock(RequestHandlerInterface.class);
        var responseFactoryMock = Mockito.mock(ResponseFactoryInterface.class);
        var errorMiddleware = new ErrorMiddleware(responseFactoryMock, false);
        var expectedStatusCode = 500;

        Mockito.when(responseFactoryMock.create()).thenReturn(responseMock);
        Mockito.when(responseMock.setStatusCode(expectedStatusCode)).thenReturn(responseMock);
        Mockito.when(nextRequestHandlerMock.handle(requestMock)).thenThrow(new RuntimeException("foo"));

        var response = errorMiddleware.process(requestMock, nextRequestHandlerMock);

        Assertions.assertSame(responseMock, response);

        Mockito.verify(responseMock, Mockito.times(1)).setStatusCode(expectedStatusCode);
    }

    @Test
    public void testItGeneratesErrorResponseForHttpException() throws Exception {
        var requestMock = Mockito.mock(RequestInterface.class);
        var responseMock = Mockito.mock(ResponseInterface.class);
        var nextRequestHandlerMock = Mockito.mock(RequestHandlerInterface.class);
        var responseFactoryMock = Mockito.mock(ResponseFactoryInterface.class);
        var errorMiddleware = new ErrorMiddleware(responseFactoryMock, false);
        var expectedStatusCode = 400;

        Mockito.when(responseFactoryMock.create()).thenReturn(responseMock);
        Mockito.when(responseMock.setStatusCode(expectedStatusCode)).thenReturn(responseMock);
        Mockito.when(nextRequestHandlerMock.handle(requestMock)).thenThrow(new HttpException(expectedStatusCode));

        var response = errorMiddleware.process(requestMock, nextRequestHandlerMock);

        Assertions.assertSame(responseMock, response);

        Mockito.verify(responseMock, Mockito.times(1)).setStatusCode(expectedStatusCode);
    }

    @Test
    public void testItGeneratesErrorResponseWithErrorDetails() throws Exception {
        var requestMock = Mockito.mock(RequestInterface.class);
        var responseBodyMock = Mockito.mock(HttpOutputStreamInterface.class);
        var responseMock = Mockito.mock(ResponseInterface.class);
        var nextRequestHandlerMock = Mockito.mock(RequestHandlerInterface.class);
        var responseFactoryMock = Mockito.mock(ResponseFactoryInterface.class);
        var errorMiddleware = new ErrorMiddleware(responseFactoryMock, true);
        var expectedStatusCode = 500;

        Mockito.when(responseFactoryMock.create()).thenReturn(responseMock);
        Mockito.when(responseMock.getBody()).thenReturn(responseBodyMock);
        Mockito.when(responseMock.setStatusCode(expectedStatusCode)).thenReturn(responseMock);
        Mockito.when(nextRequestHandlerMock.handle(requestMock)).thenThrow(new RuntimeException("foo"));

        var response = errorMiddleware.process(requestMock, nextRequestHandlerMock);

        Assertions.assertSame(responseMock, response);

        Mockito.verify(responseMock, Mockito.times(1)).setHeader("Content-Type", "text/plain");
        Mockito.verify(responseMock, Mockito.times(1)).setStatusCode(expectedStatusCode);
        Mockito.verify(responseBodyMock, Mockito.times(1)).write(Mockito.startsWith(
            "java.lang.RuntimeException: foo\n\tat timesplinter.lime.middleware.ErrorMiddleware.process"
        ));
    }
}
