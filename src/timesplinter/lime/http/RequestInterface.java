package timesplinter.lime.http;

public interface RequestInterface
{
    String getMethod();

    String getPath();

    Object getAttribute(String name);

    void setAttribute(String name, Object value);
}
