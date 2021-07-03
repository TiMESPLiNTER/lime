package timesplinter.lime;

import timesplinter.lime.container.Container;

public class LazyRequestHandlerFactory 
{
    private final Container container;

    public LazyRequestHandlerFactory(Container container) 
    {
        this.container = container;
    }
    
    public LazyRequestHandler create(String serviceId)
    {
        return new LazyRequestHandler(this.container, serviceId);
    }
}
