package timesplinter.lime.router;

import timesplinter.lime.middleware.MiddlewareDispatcher;

import java.util.regex.Pattern;

public interface CompiledRouteInterface
{
    RouteInterface getRouteDefinition();

    Pattern getCompiledPattern();

    String[] getParameterNames();

    MiddlewareDispatcher getMiddlewareDispatcher(RequestHandlerInterface tip);
}
