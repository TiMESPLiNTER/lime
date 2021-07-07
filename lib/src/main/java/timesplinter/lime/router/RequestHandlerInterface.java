package timesplinter.lime.router;

import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseInterface;

@FunctionalInterface
public interface RequestHandlerInterface
{
    ResponseInterface handle(RequestInterface request) throws Exception;
}
