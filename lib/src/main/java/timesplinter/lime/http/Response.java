package timesplinter.lime.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response implements ResponseInterface
{
    final private HttpOutputStreamInterface body;

    private int responseCode;

    final private Map<String, List<String>> headers = new HashMap<>();

    public Response(HttpOutputStreamInterface body)
    {
        this.body = body;
    }

    public HttpOutputStreamInterface getBody()
    {
        return body;
    }

    @Override
    public ResponseInterface setStatusCode(int statusCode)
    {
        this.responseCode = statusCode;

        return this;
    }

    public int getStatusCode()
    {
        return this.responseCode;
    }

    public ResponseInterface setHeader(String name, String value)
    {
        this.headers.put(name, Collections.singletonList(value));

        return this;
    }

    public Map<String, List<String>> getHeaders()
    {
        return this.headers;
    }
}
