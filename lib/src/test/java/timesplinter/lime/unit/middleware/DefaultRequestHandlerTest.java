package timesplinter.lime.unit.middleware;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.middleware.DefaultRequestHandler;
import timesplinter.lime.router.RequestHandlerInterface;

public class DefaultRequestHandlerTest {
    @Test
    public void testHandleThrowsExceptionIfRequestHandlerAttributeIsMissing() throws Exception {
        var requestMock = Mockito.mock(RequestInterface.class);
        var requestHandler = new DefaultRequestHandler();

        var exception = Assertions.assertThrows(
            RuntimeException.class,
            () -> requestHandler.handle(requestMock)
        );

        var expectedMessage = "Attribute '__requestHandler__' missing in request's attributes";
        var actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testHandleCallsRequestHandlerOfRequest() throws Exception {
        var responseMock = Mockito.mock(ResponseInterface.class);
        var requestHandlerMock = Mockito.mock(RequestHandlerInterface.class);
        var requestMock = Mockito.mock(RequestInterface.class);

        Mockito.when(requestMock.getAttribute("__requestHandler__")).thenReturn(requestHandlerMock);
        Mockito.when(requestHandlerMock.handle(requestMock)).thenReturn(responseMock);

        var response = (new DefaultRequestHandler()).handle(requestMock);

        Assertions.assertSame(responseMock, response);

        Mockito.verify(requestMock, Mockito.times(1)).getAttribute("__requestHandler__");
    }
}
