package timesplinter.lime.router;

public interface RouterInterface
{
    CompiledRouteInterface match(
            String requestMethod,
            String requestPath
    ) throws NotFoundRoutingException, MethodNotAllowedRoutingException;
}
