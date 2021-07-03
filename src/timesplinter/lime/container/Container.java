package timesplinter.lime.container;

import java.util.HashMap;
import java.util.Map;

public class Container
{
    private Map<String, Object> definitions = new HashMap<>();

    public void set(String serviceId, ServiceDefinition serviceDefinition)
    {
        this.definitions.put(serviceId, serviceDefinition);
    }

    public Object get(String serviceId)
    {
        Object definition = this.definitions.get(serviceId);

        if (definition instanceof ServiceDefinition) {
            this.definitions.put(serviceId, ((ServiceDefinition) definition).define(this));
        }

        return this.definitions.get(serviceId);
    }

    public ServiceDefinition protect(ServiceDefinition definition)
    {
        return (c) -> definition;
    }

    public Container register(ServiceProvider serviceProvider)
    {
        serviceProvider.register(this);

        return this;
    }
}
