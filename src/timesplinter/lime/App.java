package timesplinter.lime;

import timesplinter.lime.container.Container;
import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseFactory;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.middleware.MiddlewareInterface;
import timesplinter.lime.middleware.MiddlewareDispatcher;
import timesplinter.lime.middleware.RoutingMiddleware;
import timesplinter.lime.router.*;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

public class App {
    final private int port;

    final private Router router;

    final private MiddlewareDispatcher middlewareStack;

    public App(Container container, int port)
    {
        this.middlewareStack = new MiddlewareDispatcher();
        this.port = port;
        this.router = new Router();
    }

    public ResponseInterface handle(RequestInterface request) throws IOException
    {
        return this.middlewareStack.handle(request);
    }

    public App addMiddleware(MiddlewareInterface middlewareInterface)
    {
        this.middlewareStack.add(middlewareInterface);

        return this;
    }
    
    public App addDefaultRoutingMiddleware()
    {
        this.addMiddleware(new RoutingMiddleware(this.router, new ResponseFactory()));
        
        return this;
    }

    public App add(String method, String path, RequestHandlerInterface handler)
    {
        this.router.add(method, path, handler);

        return this;
    }

    public App get(String path, RequestHandlerInterface handler)
    {
        this.router.add("GET", path, handler);

        return this;
    }

    public App post(String path, RequestHandlerInterface handler)
    {
        this.router.add("POST", path, handler);

        return this;
    }

    public App patch(String path, RequestHandlerInterface handler)
    {
        this.router.add("PATCH", path, handler);

        return this;
    }

    public App delete(String path, RequestHandlerInterface handler)
    {
        this.router.add("DELETE", path, handler);

        return this;
    }
}
