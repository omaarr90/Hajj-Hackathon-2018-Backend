package io.fouad.hajjhackathon;

import io.fouad.hajjhackathon.entity.Food;
import io.fouad.hajjhackathon.entity.FoodSupplyRequest;
import io.fouad.hajjhackathon.entity.VendingMachine;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class FoodService
{
    @PersistenceContext(unitName = "hajj_hackathon_db")
    private EntityManager em;

    public List<Food> getFoodListByVendingMachine(int vmId)
    {
        TypedQuery<Food> namedQuery = em.createNamedQuery("Food.getFoodListByVendingMachineId", Food.class);
        namedQuery.setParameter("vmId", vmId);

        try
        {
            return namedQuery.getResultList();
        }
        catch(NoResultException e)
        {
            return new ArrayList<>();
        }
    }

    public void supplyFood(FoodSupplyRequest foodSupplyRequest)
    {
        int vmId = foodSupplyRequest.getVmId();
        List<Food> foodList = foodSupplyRequest.getFoodList();
        foodList.forEach(food ->
        {
            food.setCustomerId(null);
            food.setVmId(vmId);
            food.setTimestamp(LocalDateTime.now().atZone(ZoneId.of("GMT+3")).toInstant().toEpochMilli() / 1000);
            em.persist(food);
        });
    }

    public void withdrawFood(String foodId, int customerId)
    {
        TypedQuery<Food> namedQuery = em.createNamedQuery("Food.getFoodById", Food.class);
        namedQuery.setParameter("id", foodId);
        Food result = namedQuery.getSingleResult();
        result.setVmId(null);
        result.setCustomerId(customerId);
        em.persist(result);
    }
}
