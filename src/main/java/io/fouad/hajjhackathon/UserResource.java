package io.fouad.hajjhackathon;

import io.fouad.hajjhackathon.auth.Secured;
import io.fouad.hajjhackathon.entity.ApiResponse;
import io.fouad.hajjhackathon.entity.LoginBean;
import io.fouad.hajjhackathon.entity.LoginRequest;
import io.fouad.hajjhackathon.entity.User;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;

@Path("user")
@RequestScoped
@Transactional
public class UserResource
{
    @Inject
    private UserService userService;

    @Inject
    private PasswordService passwordService;

    @Inject
    private JwtService jwtService;

    @Context
    private SecurityContext securityContext;

    @Path("create")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Secured
    @RolesAllowed("admin")
    public void createUser(@FormParam("username") String username, @FormParam("password") String password,
                           @FormParam("email") String email, @FormParam("displayName") String displayName) throws GeneralSecurityException
    {
        User user = new User();

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordService.hashAndEncryptPassword(password));
        user.setDisplayName(displayName);
        user.setRoleId("user");
        user.setRegistrationDatetime(LocalDateTime.now());

        userService.createUser(user);
    }

    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Context SecurityContext sec, LoginRequest loginRequest)
    {
        User user = userService.getUserByUsername(loginRequest.getUsername());

        if(user == null) return Response.status(Response.Status.UNAUTHORIZED).build();

        byte[] encryptedPassword = user.getPassword();
        if(encryptedPassword == null) return null;

        boolean verified;
        try
        {
            verified = passwordService.verifyPassword(loginRequest.getPassword(), encryptedPassword);
        }
        catch(GeneralSecurityException e)
        {
            return Response.serverError().entity(ApiResponse.failure("S001")).build();
        }
        String role = user.getRoleId();

        if(verified) return Response.ok().entity(ApiResponse.success(new LoginBean(user.getDisplayName(),
                jwtService.generateJwt(loginRequest.getUsername(), role)))).build();
        else return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}