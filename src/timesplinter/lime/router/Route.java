package timesplinter.lime.router;

import java.util.List;

final public class Route implements RouteInterface
{
    final private String[] methods;

    final private String pattern;

    final private RequestHandlerInterface handler;

    final private List<RouteGroupInterface> groups;

    final private String identifier;

    public Route(
        String[] methods,
        String pattern,
        RequestHandlerInterface handler,
        List<RouteGroupInterface> groups,
        int identifier
    ) {
        this.methods = methods;
        this.handler = handler;
        this.pattern = pattern;
        this.groups = groups;
        this.identifier = "route" + identifier;
    }

    public String[] getMethods()
    {
        return this.methods;
    }

    @Override
    public String getName()
    {
        return null;
    }

    @Override
    public String getIdentifier()
    {
        return this.identifier;
    }

    public String getPattern()
    {
        return this.pattern;
    }

    public List<RouteGroupInterface> getGroups()
    {
        return this.groups;
    }

    public RequestHandlerInterface getHandler()
    {
        return handler;
    }
}
