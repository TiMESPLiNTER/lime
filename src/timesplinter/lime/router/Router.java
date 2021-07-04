package timesplinter.lime.router;

import java.util.*;
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

            return "(" + (3 == matchResult.groupCount() ? matchResult.group(2) : "[^/]+")  + ")";
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
    ) throws NotFoundRoutingException, MethodNotAllowedRoutingException {
        /*List<CompiledRouteInterface> routes = this.routeCollector.getRoutes().entrySet().stream()
            .map(e -> {
                String routeId = e.getKey();
                CompiledRouteInterface r;

                if (!this.compiledRouteCache.containsKey(routeId)) {
                    r = this.compileRoute(e.getValue());
                    this.compiledRouteCache.put(routeId, r);
                } else {
                    r = this.compiledRouteCache.get(routeId);
                }

                return r;
            })
            .filter(e -> e.getCompiledPattern().matcher(requestPath).matches())
            .collect(Collectors.toList());

        if (routes.size() == 0) {
            throw new NotFoundRoutingException();
        }

        CompiledRouteInterface r = routes.stream()
            .filter(e -> Arrays.asList(e.getRouteDefinition().getMethods()).contains(requestMethod))
            .findFirst()
            .orElseThrow(MethodNotAllowedRoutingException::new);*/

        boolean foundPath = false;

        for (Map.Entry<String, RouteInterface> entry : this.routeCollector.getRoutes().entrySet()) {
            String routeId = entry.getKey();
            CompiledRouteInterface r;

            if (!this.compiledRouteCache.containsKey(routeId)) {
                r = this.compileRoute(entry.getValue());
                this.compiledRouteCache.put(routeId, r);
            } else {
                r = this.compiledRouteCache.get(routeId);
            }

            Matcher matcher = r.getCompiledPattern().matcher(requestPath);

            if (!matcher.matches()) {
                continue;
            }

            foundPath = true;

            if (Arrays.stream(r.getRouteDefinition().getMethods()).noneMatch(x -> x.equals(requestMethod))) {
                continue;
            }

            return r;
        }

        if (!foundPath) {
            throw new NotFoundRoutingException();
        } else {
            throw new MethodNotAllowedRoutingException();
        }
    }
}
