package timesplinter.lime.http;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final public class Request implements RequestInterface
{
    final private String method;

    final private UriInterface uri;

    final private Map<String, Object> parameters = new HashMap<>();

    final private Map<String, List<String>> headers;

    final private InputStream body;

    public Request(String method, UriInterface uri, Map<String, List<String>> headers, InputStream body)
    {
        this.method = method;
        this.uri = uri;
        this.headers = headers;
        this.body = body;
    }

    public String getMethod()
    {
        return this.method;
    }

    public UriInterface getUri()
    {
        return this.uri;
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

    public InputStream getBody() {
        return this.body;
    }
}
