package timesplinter.lime;

import timesplinter.lime.container.Container;
import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseFactory;
import timesplinter.lime.http.ResponseFactoryInterface;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.middleware.DefaultRequestHandler;
import timesplinter.lime.middleware.ErrorMiddleware;
import timesplinter.lime.middleware.MiddlewareDispatcher;
import timesplinter.lime.middleware.MiddlewareInterface;
import timesplinter.lime.middleware.RoutingMiddleware;
import timesplinter.lime.router.RequestHandlerInterface;
import timesplinter.lime.router.RouteCollectorInterface;
import timesplinter.lime.router.RouteCollectorProxy;
import timesplinter.lime.router.RouteCollectorProxyInterface;
import timesplinter.lime.router.RouteGroupInterface;
import timesplinter.lime.router.RouteInterface;
import timesplinter.lime.router.RouteProviderInterface;
import timesplinter.lime.router.Router;

public class App implements RequestHandlerInterface, RouteCollectorProxyInterface
{
    final private RouteCollectorProxyInterface routeCollectorProxy;

    final private MiddlewareDispatcher middlewareStack;

    final private ResponseFactoryInterface responseFactory;

    public App(Container container)
    {
        this.middlewareStack = this.getMiddlewareDispatcher(container);
        this.routeCollectorProxy = new RouteCollectorProxy();
        this.responseFactory = this.getResponseFactory(container);
    }

    public ResponseInterface handle(RequestInterface request) throws Exception
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
    
    public RoutingMiddleware addDefaultRoutingMiddleware()
    {
        var routingMiddleware = new RoutingMiddleware(new Router(this.getRouteCollector()));

        this.add(routingMiddleware);
        
        return routingMiddleware;
    }

    public ErrorMiddleware addDefaultErrorMiddleware(boolean displayErrorDetails)
    {
        var errorMiddleware = new ErrorMiddleware(this.responseFactory, displayErrorDetails);

        this.add(errorMiddleware);

        return errorMiddleware;
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

    @Override
    public RouteInterface patch(String path, RequestHandlerInterface handler)
    {
        return this.routeCollectorProxy.patch(path, handler);
    }

    @Override
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

    @Override
    public RouteGroupInterface group(String path, RouteProviderInterface group)
    {
        return this.routeCollectorProxy.group(path, group);
    }

    private MiddlewareDispatcher getMiddlewareDispatcher(Container container)
    {
        String middlewareDispatcherServiceId = MiddlewareDispatcher.class.getName();

        if (!container.has(middlewareDispatcherServiceId)) {
            return new MiddlewareDispatcher(new DefaultRequestHandler());
        }

        return (MiddlewareDispatcher) container.get(middlewareDispatcherServiceId);
    }

    private ResponseFactoryInterface getResponseFactory(Container container)
    {
        String responseFactoryServiceId = ResponseFactoryInterface.class.getName();

        if (!container.has(responseFactoryServiceId)) {
            return new ResponseFactory();
        }

        return (ResponseFactoryInterface) container.get(responseFactoryServiceId);
    }
}
