package timesplinter.lime.router;

import java.util.regex.Pattern;

final public class Route
{
    final private String method;

    final private String path;

    final private Pattern pattern;

    final private String[] paramNames;

    final private RequestHandlerInterface handler;

    public Route(String method, String path, Pattern pattern, String[] paramNames, RequestHandlerInterface handler) {
        this.method = method;
        this.path = path;
        this.handler = handler;
        this.pattern = pattern;
        this.paramNames = paramNames;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public RequestHandlerInterface getHandler() {
        return handler;
    }
    
    public String[] getParamNames() {
        return this.paramNames;
    }
}
