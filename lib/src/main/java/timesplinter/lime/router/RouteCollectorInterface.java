package timesplinter.lime.router;

import java.util.Map;

public interface RouteCollectorInterface
{
    
    /**
     * Get the base path used in pathFor()
     */
    String getBasePath();

    /**
     * Set the base path used in pathFor()
     */
    RouteCollectorInterface setBasePath(String basePath);

    /**
     * Get route objects
     */
    Map<String, RouteInterface> getRoutes();

    /**
     * Get named route object
     *
     * @throws RuntimeException If named route does not exist
     */
    RouteInterface getNamedRoute(String name);

    /**
     * Remove named route
     *
     * @throws RuntimeException   If named route does not exist
     */
    RouteCollectorInterface removeNamedRoute(String name);

    /**
     * Lookup a route via the route's unique identifier
     *
     * @throws RuntimeException   If route of identifier does not exist
     */
    RouteInterface lookupRoute(String identifier);

    /**
     * Add route group
     */
    RouteGroupInterface group(String pattern, RouteProviderInterface callable);

    /**
     * Add route
     */
    RouteInterface map(String[] methods, String pattern, RequestHandlerInterface handler);
}
