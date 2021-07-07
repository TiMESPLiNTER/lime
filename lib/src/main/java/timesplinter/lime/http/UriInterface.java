package timesplinter.lime.http;

public interface UriInterface
{
    String getScheme();

    String getHost();

    String getPath();

    int getPort();

    String getQuery();
}
