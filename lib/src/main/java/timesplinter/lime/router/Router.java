package timesplinter.lime.router;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Router implements RouterInterface
{
    final private Map<String, CompiledRouteInterface> compiledRouteCache = new HashMap<>();
    
    final private RouteCollectorInterface routeCollector;
    
    public Router(RouteCollectorInterface routeCollector)
    {
        this.routeCollector = routeCollector;
    }
    
    public CompiledRouteInterface compileRoute(RouteInterface route)
    {
        Pattern routeParserPattern = Pattern.compile(
            "\\{(?<name>[^:}]+)(?:\\:(?<pattern>[^}]+))?\\}",
            Pattern.CASE_INSENSITIVE
        );

        Matcher routePatternMatcher = routeParserPattern.matcher(route.getPattern());

        List<String> paramNames = new ArrayList<>();

        String routeRegex = routePatternMatcher.replaceAll(matchResult -> {
            paramNames.add(matchResult.group(1));

            return "(" + (3 == matchResult.groupCount() ? matchResult.group(2) : "[^/]+") + ")";
        });

        return new CompiledRoute(
            route,
            Pattern.compile(routeRegex, Pattern.CASE_INSENSITIVE),
            paramNames.toArray(new String[0])
        );
    }

    public CompiledRouteInterface match(
        String requestMethod,
        String requestPath
    ) throws RoutingException {
        AtomicBoolean foundPath = new AtomicBoolean(false);

        return this.routeCollector.getRoutes().values().stream()
            .map(this::getCachedRoute)
            .filter(route -> {
                var matched = route.getCompiledPattern().matcher(requestPath).matches();

                if (matched) {
                    foundPath.set(true);
                }

                return matched;
            })
            .filter(route -> Arrays.asList(route.getRouteDefinition().getMethods()).contains(requestMethod))
            .findFirst()
            .orElseThrow(() -> {
                if (!foundPath.get()) {
                    return new NotFoundRoutingException();
                } else {
                    return new MethodNotAllowedRoutingException();
                }
            });
    }

    private CompiledRouteInterface getCachedRoute(RouteInterface route) {
        var routeId = route.getIdentifier();
        var compiledRoute = this.compiledRouteCache.get(routeId);;

        if (null == compiledRoute) {
            compiledRoute = this.compileRoute(route);
            this.compiledRouteCache.put(routeId, compiledRoute);
        }

        return compiledRoute;
    }
}
