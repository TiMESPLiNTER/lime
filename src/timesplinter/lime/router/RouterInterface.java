package timesplinter.lime.router;

public interface RouterInterface
{
    Route match(
            String requestMethod,
            String requestPath
    ) throws NotFoundRoutingException, MethodNotAllowedRoutingException;
}
