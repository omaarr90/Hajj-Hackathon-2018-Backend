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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("vm")
@RequestScoped
@Transactional
public class VendingMachineResource
{
    @Inject
    private VendingMachineService vendingMachineService;

    @Inject
    private FoodService foodService;

    @Inject
    private WithdrawService withdrawService;

    @Context
    private SecurityContext securityContext;

    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVendingMachines()
    {
        List<VendingMachineFood> vendingMachineFoodList = new ArrayList<>();
        List<VendingMachine> vendingMachines = vendingMachineService.getAll();
        vendingMachines.forEach(vendingMachine ->
        {
            List<Food> foodListByVendingMachine = foodService.getFoodListByVendingMachine(vendingMachine.getId());
            vendingMachineFoodList.add(new VendingMachineFood(vendingMachine.getId(), vendingMachine.getName(),
                    vendingMachine.getAddressAr(), vendingMachine.getAddressEn(), vendingMachine.getLongitude(),
                    vendingMachine.getLatitude(), vendingMachine.getTimestamp(), foodListByVendingMachine));
        });

        return Response.ok().entity(ApiResponse.success(vendingMachineFoodList)).build();
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVendingMachines(@PathParam("id") int id)
    {
        VendingMachine vendingMachine = vendingMachineService.getById(id);
        if(vendingMachine == null) return Response.ok().entity(ApiResponse.success(null)).build();

        List<Food> foodListByVendingMachine = foodService.getFoodListByVendingMachine(vendingMachine.getId());
        VendingMachineFood vmf = new VendingMachineFood(vendingMachine.getId(), vendingMachine.getName(),
                vendingMachine.getAddressAr(), vendingMachine.getAddressEn(), vendingMachine.getLongitude(),
                vendingMachine.getLatitude(), vendingMachine.getTimestamp(), foodListByVendingMachine);

        return Response.ok().entity(ApiResponse.success(vmf)).build();
    }

    @Path("supply")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    @RolesAllowed({"admin", "vm"})
    public Response supplyFood(FoodSupplyRequest foodSupplyRequest)
    {
        foodService.supplyFood(foodSupplyRequest);
        return Response.ok().entity(ApiResponse.success(null)).build();
    }

    @Path("withdraw")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    @RolesAllowed({"admin", "vm"})
    public Response withdrawFood(WithdrawRequest withdrawRequest)
    {
        int foodId = withdrawRequest.getFoodId();
        int customerId = withdrawRequest.getCustomerId();
        int vmId = withdrawRequest.getVmId();

        withdrawService.withdrawFood(foodId, customerId, vmId);
        foodService.withdrawFood(foodId, customerId);

        return Response.ok().entity(ApiResponse.success(null)).build();
    }

    @Path("location")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    @RolesAllowed({"admin", "vm"})
    public Response updateLocation(VmLocationRequest vmLocationRequest)
    {
        int vmId = vmLocationRequest.getVmId();
        int longitude = vmLocationRequest.getLongitude();
        int latitude = vmLocationRequest.getLatitude();

        vendingMachineService.updateLocation(vmId, longitude, latitude);

        return Response.ok().entity(ApiResponse.success(null)).build();
    }
}