package timesplinter.lime.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

final public class Request implements RequestInterface
{
    final private String method;

    final private String path;

    final private Map<String, Object> parameters = new HashMap<>();

    final private Map<String, List<String>> headers;

    public Request(String method, String path, Map<String, List<String>> headers)
    {
        this.method = method;
        this.path = path;
        this.headers = headers;
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

    @Override
    public Map<String, List<String>> getHeaders()
    {
        return this.headers;
    }

    @Override
    public List<String> getHeader(String name)
    {
        return this.headers.get(name);
    }
}
