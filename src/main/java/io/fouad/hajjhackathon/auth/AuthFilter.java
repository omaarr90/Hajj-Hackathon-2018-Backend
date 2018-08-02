package io.fouad.hajjhackathon.auth;

import io.fouad.hajjhackathon.JwtService;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.security.Principal;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter
{
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Inject
    private JwtService jwtService;

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext)
    {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if(!isTokenBasedAuthentication(authorizationHeader))
        {
            abortWithUnauthorized(requestContext);
            return;
        }

        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        boolean verified = jwtService.verifyJwt(token);
        String username = jwtService.extractUsername(token);
        String userRole = jwtService.extractRole(token);

        if(verified)
        {
            SecurityContext currentSecurityContext = requestContext.getSecurityContext();
            requestContext.setSecurityContext(new SecurityContext()
            {
                @Override
                public Principal getUserPrincipal()
                {
                    return () -> username;
                }

                @Override
                public boolean isUserInRole(String role)
                {
                    return role != null && role.equals(userRole);
                }

                @Override
                public boolean isSecure()
                {
                    return currentSecurityContext.isSecure();
                }

                @Override
                public String getAuthenticationScheme()
                {
                    return AUTHENTICATION_SCHEME;
                }
            });

            Method method = resourceInfo.getResourceMethod();

            // @DenyAll on the method takes precedence over @RolesAllowed and @PermitAll
            if(method.isAnnotationPresent(DenyAll.class))
            {
                refuseRequest(requestContext);
                return;
            }

            // @RolesAllowed on the method takes precedence over @PermitAll
            RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
            if(rolesAllowed != null)
            {
                performAuthorization(rolesAllowed.value(), requestContext);
                return;
            }

            // @PermitAll on the method takes precedence over @RolesAllowed on the class
            if(method.isAnnotationPresent(PermitAll.class))
            {
                // Do nothing
                return;
            }

            // @DenyAll can't be attached to classes

            // @RolesAllowed on the class takes precedence over @PermitAll on the class
            rolesAllowed = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);
            if(rolesAllowed != null)
            {
                performAuthorization(rolesAllowed.value(), requestContext);
            }
        }
        else abortWithUnauthorized(requestContext);
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader)
    {
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext)
    {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }

    private void performAuthorization(String[] rolesAllowed, ContainerRequestContext requestContext)
    {
        for(String role : rolesAllowed)
        {
            System.out.println(requestContext.getSecurityContext().isUserInRole(role));

            if(requestContext.getSecurityContext().isUserInRole(role))
            {
                return;
            }
        }

        refuseRequest(requestContext);
    }

    private void refuseRequest(ContainerRequestContext requestContext)
    {
        requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
    }
}