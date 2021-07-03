package timesplinter.lime.http;

import java.util.HashMap;
import java.util.Map;

final public class Request implements RequestInterface
{
    final private String method;

    final private String path;

    final private Map<String, Object> parameters = new HashMap<>();

    public Request(String method, String path)
    {
        this.method = method;
        this.path = path;
    }

    public String getMethod()
    {
        return method;
    }

    public String getPath()
    {
        return path;
    }

    public Object getAttribute(String name)
    {
        return this.parameters.get(name);
    }

    public void setAttribute(String name, Object value)
    {
        this.parameters.put(name, value);
    }
}
