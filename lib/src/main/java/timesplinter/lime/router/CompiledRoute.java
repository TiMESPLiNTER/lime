package timesplinter.lime.router;

import timesplinter.lime.middleware.MiddlewareDispatcher;

import java.util.regex.Pattern;

public class CompiledRoute implements CompiledRouteInterface
{
    final private RouteInterface routeDefinition;

    final private Pattern compiledPattern;

    final private String[] parameterNames;

    private MiddlewareDispatcher middlewareDispatcher;

    public CompiledRoute(RouteInterface routeDefinition, Pattern compiledPattern, String[] parameterNames)
    {
        this.routeDefinition = routeDefinition;
        this.compiledPattern = compiledPattern;
        this.parameterNames = parameterNames;
    }

    @Override
    public RouteInterface getRouteDefinition()
    {
        return this.routeDefinition;
    }

    @Override
    public Pattern getCompiledPattern()
    {
        return this.compiledPattern;
    }

    @Override
    public String[] getParameterNames()
    {
        return this.parameterNames;
    }

    public MiddlewareDispatcher getMiddlewareDispatcher(RequestHandlerInterface tip)
    {
        if (null == this.middlewareDispatcher) {
            this.middlewareDispatcher = new MiddlewareDispatcher(tip);

            for (RouteGroupInterface group : this.routeDefinition.getGroups()) {
                group.appendMiddlewareToDispatcher(this.middlewareDispatcher);
            }
        }

        return this.middlewareDispatcher;
    }
}
