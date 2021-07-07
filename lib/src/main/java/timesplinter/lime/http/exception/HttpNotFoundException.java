package timesplinter.lime.http.exception;

import timesplinter.lime.http.RequestInterface;

public class HttpNotFoundException extends HttpException
{
    public HttpNotFoundException()
    {
        this(null);
    }

    public HttpNotFoundException(RequestInterface request)
    {
        super(404, request);
    }
}
