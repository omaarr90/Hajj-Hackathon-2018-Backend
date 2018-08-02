package io.fouad.hajjhackathon;

import io.fouad.hajjhackathon.entity.VendingMachine;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class VendingMachineService
{
    @PersistenceContext(unitName = "hajj_hackathon_db")
    private EntityManager em;

    public List<VendingMachine> getAll()
    {
        TypedQuery<VendingMachine> namedQuery = em.createNamedQuery("VendingMachine.getAll", VendingMachine.class);

        try
        {
            return namedQuery.getResultList();
        }
        catch(NoResultException e)
        {
            return new ArrayList<>();
        }
    }

    public VendingMachine getById(int id)
    {
        TypedQuery<VendingMachine> namedQuery = em.createNamedQuery("VendingMachine.getById", VendingMachine.class);
        namedQuery.setParameter("id", id);

        try
        {
            return namedQuery.getSingleResult();
        }
        catch(NoResultException e)
        {
            return null;
        }
    }

    public void updateLocation(int vmId, double longitude, double latitude)
    {
        TypedQuery<VendingMachine> namedQuery = em.createNamedQuery("VendingMachine.getById", VendingMachine.class);
        namedQuery.setParameter("id", vmId);
        VendingMachine vm = namedQuery.getSingleResult();
        vm.setLongitude(longitude);
        vm.setLatitude(latitude);
        em.persist(vm);
    }
}
