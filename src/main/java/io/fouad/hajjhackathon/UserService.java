package io.fouad.hajjhackathon;

import io.fouad.hajjhackathon.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;

@ApplicationScoped
public class UserService
{
    @PersistenceContext(unitName = "hajj_hackathon_db")
    private EntityManager em;

    public void createUser(User user)
    {
        em.persist(user);
    }

    public User getUserByUsername(String username)
    {
        TypedQuery<User> namedQuery = em.createNamedQuery("User.getUserByUsername", User.class);
        namedQuery.setParameter("username", username);
        try
        {
            return namedQuery.getSingleResult();
        }
        catch(NoResultException e)
        {
            return null;
        }
    }
}
