package com.github.nmorel.example.metrics.resources;

import com.codahale.metrics.jersey2.InstrumentedResourceMethodApplicationListener;
import com.github.nmorel.example.metrics.EntryPoint;
import com.google.common.collect.ImmutableSet;

import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * @author Nicolas Morel
 */
public class RestApplication extends Application {

    @Override
    public Set<Object> getSingletons() {
        return ImmutableSet.<Object>of(new InstrumentedResourceMethodApplicationListener(EntryPoint.registry));
    }
}
