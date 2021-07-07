package timesplinter.lime.http;

import java.util.List;
import java.util.Map;

public interface ResponseInterface
{
    HttpOutputStreamInterface getBody();

    ResponseInterface setStatusCode(int statusCode);

    int getStatusCode();

    ResponseInterface setHeader(String name, String value);

    Map<String, List<String>> getHeaders();
}
