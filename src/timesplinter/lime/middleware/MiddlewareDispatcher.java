package timesplinter.lime.middleware;

import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.router.RequestHandlerInterface;
import timesplinter.lime.router.Route;
import timesplinter.lime.router.RouteContext;

import java.io.IOException;

public class MiddlewareDispatcher
{
    private RequestHandlerInterface tip;

    public MiddlewareDispatcher()
    {
        this.tip = request -> {
            Route route = (Route) request.getAttribute(RouteContext.ROUTE);

            return route.getHandler().handle(request);
        };
    }

    public void add(MiddlewareInterface middleware)
    {
        RequestHandlerInterface prev = this.tip;

        this.tip = request -> middleware.process(request, prev);
    }

    public ResponseInterface handle(RequestInterface request) throws IOException
    {
        return this.tip.handle(request);
    }
}
