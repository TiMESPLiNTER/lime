package timesplinter.lime.http.exception;

import timesplinter.lime.http.RequestInterface;

public class HttpException extends Exception
{
    final private int statusCode;
    
    final private RequestInterface request;
    
    public HttpException(int statusCode, String message, RequestInterface request, Throwable cause)
    {
        super(message, cause);
        this.statusCode = statusCode;
        this.request = request;
    }

    public HttpException(int statusCode, RequestInterface request, Throwable cause)
    {
        this(statusCode, "", request, cause);
    }
    
    public HttpException(int statusCode)
    {
        this(statusCode, "", null, null);
    }

    public HttpException(int statusCode, RequestInterface request)
    {
        this(statusCode, "", request, null);
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public RequestInterface getRequest()
    {
        return request;
    }
}
