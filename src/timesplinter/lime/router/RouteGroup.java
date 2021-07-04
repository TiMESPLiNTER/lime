package timesplinter.lime.router;

import timesplinter.lime.middleware.MiddlewareDispatcher;
import timesplinter.lime.middleware.MiddlewareInterface;

import java.util.ArrayList;
import java.util.List;

final class RouteGroup implements RouteGroupInterface
{
    protected RouteGroupCallableInterface callable;

    protected RouteCollectorProxyInterface routeCollectorProxy;

    protected List<MiddlewareInterface> middleware = new ArrayList<>();

    protected String pattern;

    public RouteGroup(
        String pattern,
        RouteGroupCallableInterface callable,
        RouteCollectorProxyInterface routeCollectorProxy
    ) {
        this.pattern = pattern;
        this.callable = callable;
        this.routeCollectorProxy = routeCollectorProxy;
    }

    public RouteGroupInterface collectRoutes()
    {
        this.callable.collect(this.routeCollectorProxy);
        return this;
    }

    public RouteGroupInterface add(MiddlewareInterface middleware)
    {
        this.middleware.add(middleware);
        return this;
    }

    public RouteGroupInterface addMiddleware(MiddlewareInterface middleware)
    {
        return this.add(middleware);
    }

    public RouteGroupInterface appendMiddlewareToDispatcher(MiddlewareDispatcher dispatcher)
    {
        for (MiddlewareInterface middleware : this.middleware) {
            dispatcher.add(middleware);
        }

        return this;
    }

    public String getPattern()
    {
        return this.pattern;
    }
}
