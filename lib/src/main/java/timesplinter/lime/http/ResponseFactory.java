package timesplinter.lime.http;

public class ResponseFactory implements ResponseFactoryInterface
{
    @Override
    public ResponseInterface create()
    {
        return new Response(new HttpOutputStream());
    }
}
