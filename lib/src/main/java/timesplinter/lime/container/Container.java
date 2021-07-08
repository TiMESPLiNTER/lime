package timesplinter.lime.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container
{
    final private Map<String, Object> definitions;

    public Container(Map<String, Object> values)
    {
        this.definitions = values;
    }

    public Container()
    {
        this(new HashMap<>());
    }

    public void set(String serviceId, ServiceDefinitionInterface serviceDefinition)
    {
        this.definitions.put(serviceId, serviceDefinition);
    }

    public Object get(String serviceId)
    {
        Object definition = this.definitions.get(serviceId);

        if (definition instanceof ServiceDefinitionInterface) {
            this.definitions.put(serviceId, ((ServiceDefinitionInterface) definition).define());
        }

        return this.definitions.get(serviceId);
    }

    public boolean has(String serviceId)
    {
        return this.definitions.containsKey(serviceId);
    }

    public ServiceDefinitionInterface protect(ServiceDefinitionInterface definition)
    {
        return () -> definition;
    }

    public void extend(String serviceId, ExtendServiceDefinitionInterface serviceDefinition)
    {
        var prevServiceDefinition = (ServiceDefinitionInterface) this.definitions.get(serviceId);

        this.set(serviceId, () -> serviceDefinition.define(prevServiceDefinition.define()));
    }

    public Container register(ServiceProviderInterface serviceProvider)
    {
        serviceProvider.register(this);

        return this;
    }

    public void precompile()
    {
        this.precompile(new ArrayList<>(this.definitions.keySet()));
    }

    public void precompile(List<String> serviceIds)
    {
        serviceIds.forEach(this::get);
    }
}
