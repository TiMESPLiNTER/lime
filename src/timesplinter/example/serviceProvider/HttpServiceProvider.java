package timesplinter.example.serviceProvider;

import timesplinter.lime.container.Container;
import timesplinter.lime.container.ServiceProviderInterface;
import timesplinter.lime.http.ResponseFactory;
import timesplinter.lime.http.ResponseFactoryInterface;

final public class HttpServiceProvider implements ServiceProviderInterface
{
    @Override
    public void register(Container container)
    {
        container.set(ResponseFactoryInterface.class.getName(), ResponseFactory::new);
    }
}
