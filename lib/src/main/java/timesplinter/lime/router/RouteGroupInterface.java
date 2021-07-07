package timesplinter.lime.router;

import timesplinter.lime.middleware.MiddlewareDispatcher;
import timesplinter.lime.middleware.MiddlewareInterface;

public interface RouteGroupInterface
{
    RouteGroupInterface collectRoutes();

    /**
     * Add middleware to the route group
     */
    RouteGroupInterface add(MiddlewareInterface middleware);

    /**
     * Add middleware to the route group
     */
    RouteGroupInterface addMiddleware(MiddlewareInterface middleware);

    /**
     * Append the group's middleware to the MiddlewareDispatcher
     */
    RouteGroupInterface appendMiddlewareToDispatcher(MiddlewareDispatcher dispatcher);

    String getPattern();
}
