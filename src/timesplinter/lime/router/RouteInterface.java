package timesplinter.lime.router;

import java.util.List;

public interface RouteInterface
{
    String getName();

    String getIdentifier();

    String getPattern();

    String[] getMethods();

    List<RouteGroupInterface> getGroups();

    RequestHandlerInterface getHandler();
}
