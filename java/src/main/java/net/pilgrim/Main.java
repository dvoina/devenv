package net.pilgrim;

import static spark.Spark.*;

import java.util.Collections;
import java.util.logging.Logger;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;

public class Main {
    public static void main(String[] args) {
        final Logger LOG = Logger.getLogger(Main.class.getName());
        final Consul client = Consul.builder()
        .withHostAndPort(HostAndPort.fromParts("consul.docker", 8500)) 
        .build();
        final AgentClient agentClient = client.agentClient();

        String serviceId = "1";
        Registration service = ImmutableRegistration.builder()
            .id(serviceId)
            .name("spark")
            .address("my_app.stack_default")
            .port(4567)
            .check(Registration.RegCheck.ttl(3L)) // registers with a TTL of 3 seconds
            .tags(Collections.singletonList("tag1"))
            .meta(Collections.singletonMap("version", "1.0"))
            .build();

        agentClient.register(service);
        get("/hello", (req, res) -> {
            LOG.info("!----------!----------!"); 
            return "Hello World";
        });
    }
}
