package timesplinter.lime;

import org.junit.jupiter.api.Test;
import timesplinter.lime.container.Container;
import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.router.RequestHandlerInterface;

import static org.mockito.Mockito.*;

public class LazyRequestHandlerTest
{
    @Test
    public void testThatItCallsContainerWithServiceIdAndExecutesRequestHandler() throws Exception
    {
        String serviceId = "myServiceId";

        RequestHandlerInterface requestHandler = mock(RequestHandlerInterface.class);
        RequestInterface request = mock(RequestInterface.class);
        Container container = mock(Container.class);

        when(container.get(serviceId)).thenReturn(requestHandler);

        var lazyRequestHandler = new LazyRequestHandler(container, serviceId);

        lazyRequestHandler.handle(request);

        verify(container).get(serviceId);
        verify(requestHandler).handle(request);
    }
}
