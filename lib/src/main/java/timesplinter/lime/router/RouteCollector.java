package timesplinter.lime.router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RouteCollector is used to collect routes and route groups
 * as well as generate paths and URLs relative to its environment
 */
final class RouteCollector implements RouteCollectorInterface
{
    /**
     * Base path used in pathFor()
     */
    protected String basePath = "";

    /**
     * Routes
     */
    protected Map<String, RouteInterface> routes = new HashMap<>();

    /**
     * Route groups
     */
    protected List<RouteGroupInterface> routeGroups = new ArrayList<>();

    /**
     * Route counter incrementer
     */
    protected int routeCounter = 0;

    public String getBasePath()
    {
        return this.basePath;
    }

    /**
     * Set the base path used in urlFor()
     */
    public RouteCollectorInterface setBasePath(String basePath)
    {
        this.basePath = basePath;

        return this;
    }

    public Map<String, RouteInterface> getRoutes()
    {
        return this.routes;
    }

    public RouteCollectorInterface removeNamedRoute(String name)
    {
        var route = this.getNamedRoute(name);
        this.routes.remove(route);
        return this;
    }

    public RouteInterface getNamedRoute(String name)
    {
        for (String routeIdentifier : this.routes.keySet()) {
            var route = this.routes.get(routeIdentifier);
            if (route.getName().equals(name)) {
                return route;
            }
        }
        throw new RuntimeException("Named route does not exist for name: " + name);
    }

    public RouteInterface lookupRoute(String identifier)
    {
        if (!this.routes.containsKey(identifier)) {
            throw new RuntimeException("Route not found, looks like your route cache is stale.");
        }
        return this.routes.get(identifier);
    }

    public RouteGroupInterface group(String pattern, RouteProviderInterface callable)
    {
        var routeCollectorProxy = new RouteCollectorProxy(
            this,
            pattern
        );

        var routeGroup = new RouteGroup(pattern, callable, routeCollectorProxy);
        this.routeGroups.add(routeGroup);

        routeGroup.collectRoutes();
        this.routeGroups.remove(this.routeGroups.size() - 1);

        return routeGroup;
    }

    public RouteInterface map(String[] methods, String pattern, RequestHandlerInterface handler)
    {
        var route = this.createRoute(methods, pattern, handler);
        this.routes.put(route.getIdentifier(), route);
        this.routeCounter++;

        return route;
    }

    protected RouteInterface createRoute(String[] methods, String pattern, RequestHandlerInterface callable)
    {
        return new Route(
            methods,
            pattern,
            callable,
            List.copyOf(this.routeGroups),
            this.routeCounter
        );
    }
}
