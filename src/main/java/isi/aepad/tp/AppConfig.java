package isi.aepad.tp;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jersey2.InstrumentedResourceMethodApplicationListener;
import isi.aepad.tp.web.MyMetricsServletContextListener;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;

@javax.ws.rs.ApplicationPath("/api")
public class AppConfig extends ResourceConfig {

	private Set<Class<?>> resources = new HashSet<Class<?>>();
    private static final Logger logger = Logger.getLogger(AppConfig.class.getName());


    /*  @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }*/
    public AppConfig() {
        this.initializeApplication();
    }

    private void initializeApplication() {
        registerListeners(); // Register listeners
    }

    private void registerListeners() {
        //final MetricRegistry metricRegistry = new MetricRegistry();
        MetricRegistry reg = MyMetricsServletContextListener.METRIC_REGISTRY;
        register(new InstrumentedResourceMethodApplicationListener(reg));

        ConsoleReporter.forRegistry(reg)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build()
                .start(1, TimeUnit.MINUTES);
        logger.info("Console reporter is enabled successfully!");
        register(isi.aepad.tp.services.EchoResource.class);
        register(isi.aepad.tp.services.ProductoResource.class);
        register(isi.aepad.tp.services.DataConfigResource.class);
    }



    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(isi.aepad.tp.services.EchoResource.class);
        resources.add(isi.aepad.tp.services.ProductoResource.class);
        resources.add(isi.aepad.tp.services.DataConfigResource.class);
    }
}
