package io.fouad.hajjhackathon;

import io.fouad.hajjhackathon.entity.Withdraw;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@ApplicationScoped
public class WithdrawService
{
    @PersistenceContext(unitName = "hajj_hackathon_db")
    private EntityManager em;

    public void withdrawFood(int foodId, int customerId, int vmId)
    {
        Withdraw withdraw = new Withdraw();
        withdraw.setFoodId(foodId);
        withdraw.setCustomerId(customerId);
        withdraw.setVmId(vmId);
        em.persist(withdraw);
    }

    public boolean isUserEligibleToWithdrawFood(int customerId)
    {
        TypedQuery<Withdraw> namedQuery = em.createNamedQuery("Withdraw.getByCustomerId", Withdraw.class);
        namedQuery.setParameter("customerId", customerId);

        try
        {
            Withdraw result = namedQuery.getSingleResult();
            LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(result.getTimestamp() * 1000), ZoneId.of("GMT+3"));
            return LocalDateTime.now().minusDays(1).isAfter(ldt);
        }
        catch(NoResultException e)
        {
            return false;
        }
    }
}
