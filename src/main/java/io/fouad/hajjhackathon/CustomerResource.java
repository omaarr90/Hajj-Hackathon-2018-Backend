package io.fouad.hajjhackathon;

import io.fouad.hajjhackathon.auth.Secured;
import io.fouad.hajjhackathon.entity.*;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("customer")
@RequestScoped
@Transactional
public class CustomerResource
{
    @Inject
    private WithdrawService withdrawService;

    @Context
    private SecurityContext securityContext;

    @Path("eligibility")
    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    @RolesAllowed({"admin", "vm"})
    public Response getCustomerEligibility(@QueryParam("customerId") int customerId)
    {
        boolean eligible = withdrawService.isUserEligibleToWithdrawFood(customerId);
        return Response.ok().entity(ApiResponse.success(new EligibilityBean(eligible))).build();
    }
}