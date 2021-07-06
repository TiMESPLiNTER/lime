package timesplinter.lime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import timesplinter.lime.LazyRequestHandler;
import timesplinter.lime.LazyRequestHandlerFactory;
import timesplinter.lime.container.Container;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class LazyRequestHandlerFactoryTest
{
    @Test
    public void testSomething() throws IOException
    {
        var serviceId = "myServiceId";
        var container = mock(Container.class);
        var lazyRequestHandlerFactory = new LazyRequestHandlerFactory(container);

        var lazyRequestHandler = lazyRequestHandlerFactory.create(serviceId);

        Assertions.assertEquals(
            lazyRequestHandler.getClass().getName(),
            LazyRequestHandler.class.getName()
        );
    }
}
