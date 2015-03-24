package com.github.nmorel.example.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Timer;
import com.codahale.metrics.jetty9.InstrumentedConnectionFactory;
import com.codahale.metrics.jetty9.InstrumentedHandler;
import com.codahale.metrics.jetty9.InstrumentedQueuedThreadPool;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author Nicolas Morel
 */
public class EntryPoint {

    public static final MetricRegistry registry = new MetricRegistry();

    public static void main(String[] args) throws Exception {

        registry.registerAll(new MemoryUsageGaugeSet());
        registry.registerAll(new GarbageCollectorMetricSet());

        final Slf4jReporter reporter = Slf4jReporter.forRegistry(registry)
                .outputTo(LoggerFactory.getLogger("com.github.nmorel.metrics"))
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.MINUTES);

        ServletHolder sh = new ServletHolder(ServletContainer.class);
        sh.setInitParameter("javax.ws.rs.Application", "com.github.nmorel.example.metrics.resources.RestApplication");
        sh.setInitParameter("jersey.config.server.provider.packages", "com.github.nmorel.example.metrics.resources");

        Server server = new Server(new InstrumentedQueuedThreadPool(registry));
        server.setHandler(new InstrumentedHandler(registry));
        ServerConnector connector = new ServerConnector(server, new InstrumentedConnectionFactory(new HttpConnectionFactory(), new Timer()));
        connector.setPort(8080);
        server.setConnectors(new Connector[]{connector});

        ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        context.addServlet(sh, "/*");

        server.start();
        server.join();
    }
}
