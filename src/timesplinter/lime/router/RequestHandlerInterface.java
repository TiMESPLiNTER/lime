package timesplinter.lime.router;

import timesplinter.lime.http.RequestInterface;
import timesplinter.lime.http.ResponseInterface;

import java.io.IOException;

@FunctionalInterface
public interface RequestHandlerInterface
{
    ResponseInterface handle(RequestInterface request) throws IOException;
}
