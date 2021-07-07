package timesplinter.lime.router;

public interface RouteCollectorProxyInterface
{
    RouteCollectorInterface getRouteCollector();

    String getBasePath();

    RouteCollectorProxyInterface setBasePath(String basePath);

    RouteInterface get(String pattern, RequestHandlerInterface handler);

    RouteInterface post(String pattern, RequestHandlerInterface handler);

    RouteInterface put(String pattern, RequestHandlerInterface handler);

    RouteInterface patch(String pattern, RequestHandlerInterface handler);

    RouteInterface delete(String pattern, RequestHandlerInterface handler);

    RouteInterface options(String pattern, RequestHandlerInterface handler);

    RouteInterface any(String pattern, RequestHandlerInterface handler);

    RouteInterface map(String[] methods, String pattern, RequestHandlerInterface handler);

    RouteGroupInterface group(String pattern, RouteProviderInterface callable);
}
