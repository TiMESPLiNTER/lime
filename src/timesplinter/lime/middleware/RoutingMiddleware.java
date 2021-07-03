package timesplinter.lime.middleware;

import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseFactoryInterface;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.router.*;

import java.io.IOException;
import java.util.regex.Matcher;

public class RoutingMiddleware implements MiddlewareInterface
{
    final private RouterInterface router;

    final private ResponseFactoryInterface responseFactory;

    public RoutingMiddleware(RouterInterface router, ResponseFactoryInterface responseFactory)
    {
        this.router = router;
        this.responseFactory = responseFactory;
    }

    public ResponseInterface process(RequestInterface request, RequestHandlerInterface next) throws IOException
    {
        try {
            Route route = this.router.match(request.getMethod(), request.getPath());

            this.addRequestParametersFromRoute(request, route);

            request.setAttribute(RouteContext.ROUTE, route);
            
            return next.handle(request);
        } catch (NotFoundRoutingException|MethodNotAllowedRoutingException e) {
            ResponseInterface response = this.responseFactory.create();
            response.setStatusCode(e instanceof NotFoundRoutingException ? 404 : 405);

            return response;
        }
    }

    private void addRequestParametersFromRoute(RequestInterface request, Route route)
    {
        Matcher matcher = route.getPattern().matcher(request.getPath());

        if (!matcher.find()) {
            return;
        }

        for (int i = 0; i < matcher.groupCount(); i++) {
            request.setAttribute(route.getParamNames()[i], matcher.group(i+1));
        }
    }
}
