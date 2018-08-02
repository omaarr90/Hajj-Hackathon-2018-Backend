package io.fouad.hajjhackathon;

import io.fouad.hajjhackathon.auth.AuthFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class RestApplication extends Application
{
    @Override
    public Set<Object> getSingletons()
    {
        Set<Object> singletons = new HashSet<>();
        singletons.add(new UserResource());
        singletons.add(new VendingMachineResource());
        singletons.add(new CustomerResource());
        singletons.add(new AuthFilter());
        return singletons;
    }
}