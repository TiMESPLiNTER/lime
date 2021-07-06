package timesplinter.lime.middleware;

import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.router.CompiledRouteInterface;
import timesplinter.lime.router.RequestHandlerInterface;
import timesplinter.lime.router.RouteContext;

public class MiddlewareDispatcher implements RequestHandlerInterface
{
    private RequestHandlerInterface tip;

    public MiddlewareDispatcher()
    {
        this(request -> {
            CompiledRouteInterface route = (CompiledRouteInterface) request.getAttribute(RouteContext.ROUTE);

            return route.getRouteDefinition().getHandler().handle(request);
        });
    }
    
    public MiddlewareDispatcher(RequestHandlerInterface kernel)
    {
        this.tip = kernel;
    }

    public void add(MiddlewareInterface middleware)
    {
        RequestHandlerInterface prev = this.tip;


        this.tip = request -> middleware.process(request, prev);
    }

    public ResponseInterface handle(RequestInterface request) throws Exception
    {
        return this.tip.handle(request);
    }
}
