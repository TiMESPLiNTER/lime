package timesplinter.lime.middleware;

import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseFactoryInterface;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.http.exception.HttpMethodNotAllowedException;
import timesplinter.lime.http.exception.HttpNotFoundException;
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

    public ResponseInterface process(RequestInterface request, RequestHandlerInterface next) throws Exception
    {
        try {
            CompiledRouteInterface route = this.router.match(request.getMethod(), request.getUri().getPath());

            this.addRequestParametersFromRoute(request, route);

            request.setAttribute(RouteContext.ROUTE, route);

            return route.getMiddlewareDispatcher(next).handle(request);
        } catch (NotFoundRoutingException e) {
            throw new HttpNotFoundException(request);
        } catch (MethodNotAllowedRoutingException e) {
            throw new HttpMethodNotAllowedException(request);
        }
    }

    private void addRequestParametersFromRoute(RequestInterface request, CompiledRouteInterface route)
    {
        Matcher matcher = route.getCompiledPattern().matcher(request.getUri().getPath());

        if (!matcher.find()) {
            return;
        }

        for (int i = 0; i < matcher.groupCount(); i++) {
            request.setAttribute(route.getParameterNames()[i], matcher.group(i+1));
        }
    }
}
