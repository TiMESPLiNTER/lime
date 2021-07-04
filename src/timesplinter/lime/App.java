package timesplinter.lime;

import timesplinter.lime.container.Container;
import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseFactory;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.middleware.MiddlewareInterface;
import timesplinter.lime.middleware.MiddlewareDispatcher;
import timesplinter.lime.middleware.RoutingMiddleware;
import timesplinter.lime.router.*;

import java.io.*;

public class App implements RequestHandlerInterface, RouteCollectorProxyInterface
{
    final private RouteCollectorProxyInterface routeCollectorProxy;

    final private MiddlewareDispatcher middlewareStack;

    public App(Container container)
    {
        this.middlewareStack = new MiddlewareDispatcher();
        this.routeCollectorProxy = new RouteCollectorProxy();
    }

    public ResponseInterface handle(RequestInterface request) throws IOException
    {
        return this.middlewareStack.handle(request);
    }

    public App add(MiddlewareInterface middleware)
    {
        this.middlewareStack.add(middleware);

        return this;
    }
    
    public App addMiddleware(MiddlewareInterface middleware)
    {
        this.add(middleware);
        
        return this;
    }
    
    public App addDefaultRoutingMiddleware()
    {
        this.add(new RoutingMiddleware(new Router(this.getRouteCollector()), new ResponseFactory()));
        
        return this;
    }

    @Override
    public RouteInterface map(String[] methods, String path, RequestHandlerInterface handler)
    {
        return this.routeCollectorProxy.map(methods, path, handler);
    }

    @Override
    public RouteCollectorInterface getRouteCollector()
    {
        return this.routeCollectorProxy.getRouteCollector();
    }

    @Override
    public String getBasePath()
    {
        return this.routeCollectorProxy.getBasePath();
    }

    @Override
    public RouteCollectorProxyInterface setBasePath(String basePath)
    {
        return this.routeCollectorProxy.setBasePath(basePath);
    }

    @Override
    public RouteInterface get(String path, RequestHandlerInterface handler)
    {
        return this.routeCollectorProxy.get(path, handler);
    }

    @Override
    public RouteInterface post(String path, RequestHandlerInterface handler)
    {
        return this.routeCollectorProxy.post(path, handler);
    }

    @Override
    public RouteInterface put(String pattern, RequestHandlerInterface handler)
    {
        return this.routeCollectorProxy.put(pattern, handler);
    }

    public RouteInterface patch(String path, RequestHandlerInterface handler)
    {
        return this.routeCollectorProxy.patch(path, handler);
    }

    public RouteInterface delete(String path, RequestHandlerInterface handler)
    {
        return this.routeCollectorProxy.delete(path, handler);
    }

    @Override
    public RouteInterface options(String pattern, RequestHandlerInterface handler)
    {
        return this.routeCollectorProxy.options(pattern, handler);
    }

    @Override
    public RouteInterface any(String pattern, RequestHandlerInterface handler) 
    {
        return this.routeCollectorProxy.any(pattern, handler);
    }

    public RouteGroupInterface group(String path, RouteGroupCallableInterface group)
    {
        return this.routeCollectorProxy.group(path, group);
    }
}
