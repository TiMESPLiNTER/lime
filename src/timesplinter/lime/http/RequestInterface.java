package timesplinter.lime.http;

import java.net.URI;
import java.util.List;
import java.util.Map;

public interface RequestInterface
{
    String getMethod();

    URI getUri();

    Object getAttribute(String name);

    void setAttribute(String name, Object value);

    Map<String, List<String>> getHeaders();

    List<String> getHeader(String name);
}
