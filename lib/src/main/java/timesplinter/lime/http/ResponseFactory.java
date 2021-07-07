package timesplinter.lime.http;

import java.io.ByteArrayOutputStream;

public class ResponseFactory implements ResponseFactoryInterface
{
    @Override
    public ResponseInterface create()
    {
        return new Response(new HttpOutputStream(new ByteArrayOutputStream()));
    }
}
