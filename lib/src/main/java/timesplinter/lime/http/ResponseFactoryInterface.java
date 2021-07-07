package timesplinter.lime.http;

import java.io.IOException;

public interface ResponseFactoryInterface
{
    ResponseInterface create() throws IOException;
}
