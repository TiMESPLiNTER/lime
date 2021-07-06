package timesplinter.lime.http.exception;

import timesplinter.lime.http.RequestInterface;

public class HttpMethodNotAllowedException extends HttpException
{
    public HttpMethodNotAllowedException()
    {
        this(null);
    }

    public HttpMethodNotAllowedException(RequestInterface request)
    {
        super(405, request);
    }
}
