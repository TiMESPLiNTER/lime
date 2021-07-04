package timesplinter.lime.router;

public class RouteCollectorProxy implements RouteCollectorProxyInterface
{
    protected RouteCollectorInterface routeCollector;

    protected String groupPattern;

    public RouteCollectorProxy(
        RouteCollectorInterface routeCollector,
        String groupPattern
    ) {
        this.routeCollector = routeCollector;
        this.groupPattern = groupPattern;
    }

    public RouteCollectorProxy(String groupPattern)
    {
        this(new RouteCollector(), groupPattern);
    }
    
    public RouteCollectorProxy()
    {
        this(new RouteCollector(), "");
    }

    public RouteCollectorInterface getRouteCollector()
    {
        return this.routeCollector;
    }

    public String getBasePath()
    {
        return this.routeCollector.getBasePath();
    }

    public RouteCollectorProxyInterface setBasePath(String basePath)
    {
        this.routeCollector.setBasePath(basePath);

        return this;
    }

    public RouteInterface get(String pattern, RequestHandlerInterface handler)
    {
        return this.map(new String[]{"GET"}, pattern, handler);
    }

    public RouteInterface post(String pattern, RequestHandlerInterface handler)
    {
        return this.map(new String[]{"POST"}, pattern, handler);
    }

    public RouteInterface put(String pattern, RequestHandlerInterface handler)
    {
        return this.map(new String[]{"PUT"}, pattern, handler);
    }

    public RouteInterface patch(String pattern, RequestHandlerInterface handler)
    {
        return this.map(new String[]{"PATCH"}, pattern, handler);
    }

    public RouteInterface delete(String pattern, RequestHandlerInterface handler)
    {
        return this.map(new String[]{"DELETE"}, pattern, handler);
    }

    public RouteInterface options(String pattern, RequestHandlerInterface handler)
    {
        return this.map(new String[]{"OPTIONS"}, pattern, handler);
    }

    public RouteInterface any(String pattern, RequestHandlerInterface handler)
    {
        return this.map(new String[]{"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"}, pattern, handler);
    }

    public RouteInterface map(String[] methods, String pattern, RequestHandlerInterface handler)
    {
        pattern = this.groupPattern + pattern;

        return this.routeCollector.map(methods, pattern, handler);
    }

    public RouteGroupInterface group(String pattern, RouteGroupCallableInterface callable)
    {
        pattern = this.groupPattern + pattern;

        return this.routeCollector.group(pattern, callable);
    }

    /*public RouteInterface redirect(String from, String to, int status = 302)
    {
        $responseFactory = $this->responseFactory;

        $handler = function () use ($to, $status, $responseFactory) {
            $response = $responseFactory->createResponse($status);
            return $response->withHeader('Location', (string) $to);
        };

        return $this->get($from, $handler);
    }*/
}
