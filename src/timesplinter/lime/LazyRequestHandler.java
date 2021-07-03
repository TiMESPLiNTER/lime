package timesplinter.lime;

import timesplinter.lime.container.Container;
import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.router.RequestHandlerInterface;

import java.io.IOException;

public class LazyRequestHandler implements RequestHandlerInterface
{
    final private String serviceId;

    final private Container container;

    public LazyRequestHandler(Container container, String serviceId)
    {
        this.serviceId = serviceId;
        this.container = container;
    }

    @Override
    public ResponseInterface handle(RequestInterface request) throws IOException
    {
        return ((RequestHandlerInterface) container.get(this.serviceId)).handle(request);
    }
}
