package timesplinter.lime.http;

import java.util.*;

public class Response implements ResponseInterface
{
    private HttpOutputStream body;

    private int responseCode;

    private Map<String, List<String>> headers = new HashMap<>();

    public Response(HttpOutputStream body)
    {
        this.body = body;
    }

    public HttpOutputStream getBody()
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

    public int getResponseCode()
    {
        return responseCode;
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
