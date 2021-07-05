package timesplinter.lime.http;

import java.util.List;
import java.util.Map;

public interface RequestInterface
{
    String getMethod();

    String getPath();

    Object getAttribute(String name);

    void setAttribute(String name, Object value);

    Map<String, List<String>> getHeaders();

    List<String> getHeader(String name);
}
