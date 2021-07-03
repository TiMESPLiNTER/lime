package timesplinter.lime.router;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Router implements RouterInterface
{
    final private List<Route> routes = new ArrayList<>();

    public Route match(
        String requestMethod,
        String requestPath
    ) throws NotFoundRoutingException, MethodNotAllowedRoutingException {
        boolean foundPath = false;

        for (Route r : this.routes) {
            Matcher matcher = r.getPattern().matcher(requestPath);

            if (!matcher.matches()) {
                continue;
            }

            foundPath = true;

            if (!requestMethod.equals(r.getMethod())) {
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

    public void add(String method, String path, RequestHandlerInterface handler)
    {
        Pattern routeParserPattern = Pattern.compile(
            "\\{(?<name>[^:}]+)(?:\\:(?<pattern>[^}]+))?\\}",
            Pattern.CASE_INSENSITIVE
        );

        Matcher routePatternMatcher = routeParserPattern.matcher(path);

        List<String> paramNames = new ArrayList<>();

        String routeRegex = routePatternMatcher.replaceAll(matchResult -> {
            paramNames.add(matchResult.group(1));

            return "(" + (3 == matchResult.groupCount() ? matchResult.group(2) : "[^/]+")  + ")";
        });

        this.routes.add(new Route(
            method.toUpperCase(),
            path,
            Pattern.compile('^' + routeRegex + '$', Pattern.CASE_INSENSITIVE),
            paramNames.toArray(new String[0]),
            handler
        ));
    }
}
