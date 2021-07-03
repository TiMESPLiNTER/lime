package timesplinter.lime.http;

import java.io.IOException;

public class ResponseFactory implements ResponseFactoryInterface
{
    @Override
    public ResponseInterface create() throws IOException {
        return new Response(HttpOutputStream.create());
    }
}
