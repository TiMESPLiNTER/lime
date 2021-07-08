package timesplinter.lime.unit.middleware;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.middleware.MiddlewareDispatcher;
import timesplinter.lime.middleware.MiddlewareInterface;
import timesplinter.lime.router.RequestHandlerInterface;

public class MiddlewareDispatcherTest {
    @Test
    public void testHandleCallsRequestHandlersHandle() throws Exception {
        var requestHandlerMock = Mockito.mock(RequestHandlerInterface.class);
        var responseMock = Mockito.mock(ResponseInterface.class);
        var requestMock = Mockito.mock(RequestInterface.class);
        var middlewareDispatcher = new MiddlewareDispatcher(requestHandlerMock);

        Mockito.when(requestHandlerMock.handle(requestMock)).thenReturn(responseMock);

        var response = middlewareDispatcher.handle(requestMock);

        Assertions.assertSame(responseMock, response);
        Mockito.verify(requestHandlerMock, Mockito.times(1)).handle(requestMock);
    }

    @Test
    public void testAddedMiddlewareGetsTriggered() throws Exception {
        var requestHandlerMock = Mockito.mock(RequestHandlerInterface.class);
        var middlewareMock = Mockito.mock(MiddlewareInterface.class);
        var responseMock = Mockito.mock(ResponseInterface.class);
        var requestMock = Mockito.mock(RequestInterface.class);
        var middlewareDispatcher = new MiddlewareDispatcher(requestHandlerMock);

        Mockito.when(middlewareMock.process(requestMock, requestHandlerMock)).thenReturn(responseMock);

        middlewareDispatcher.add(middlewareMock);
        var response = middlewareDispatcher.handle(requestMock);

        Assertions.assertSame(responseMock, response);
        Mockito.verify(middlewareMock, Mockito.times(1)).process(requestMock, requestHandlerMock);
    }
}
