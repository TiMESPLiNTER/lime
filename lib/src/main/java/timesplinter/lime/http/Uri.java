package timesplinter.lime.http;

import java.net.URI;

public class Uri implements UriInterface {
    final private URI uri;

    public Uri(URI uri)
    {
        this.uri = uri;
    }

    public String getScheme()
    {
        return this.uri.getScheme();
    }
    
    public String getHost()
    {
        return this.uri.getHost();
    }

    public String getPath()
    {
        return this.uri.getPath();
    }

    public int getPort()
    {
        return this.uri.getPort();
    }

    public String getQuery()
    {
        return this.uri.getQuery();
    }

    public String toString()
    {
        return this.uri.toString();
    }
}
