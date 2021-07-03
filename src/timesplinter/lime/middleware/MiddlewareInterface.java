package timesplinter.lime.middleware;

import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseInterface;
import timesplinter.lime.router.RequestHandlerInterface;

import java.io.IOException;

public interface MiddlewareInterface
{
    ResponseInterface process(RequestInterface request, RequestHandlerInterface next) throws IOException;
}
