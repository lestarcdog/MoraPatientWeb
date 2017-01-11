package hu.mora.web.dao;

import hu.mora.web.model.HunCity;
import hu.mora.web.model.config.Config;
import hu.mora.web.model.novadb.SatelitElhElement;
import hu.mora.web.service.config.Configs;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;

@Stateless
public class ConfigDao {

    public static final String SQL_QUERY = "SELECT c FROM Config c WHERE c.name = :key";

    @PersistenceContext(unitName = "MoraPU")
    private EntityManager em;

    public String getValue(Configs config) {
        TypedQuery<Config> query = em.createQuery(SQL_QUERY, Config.class);
        query.setParameter("key", config.name());
        return query.getSingleResult().getValue();
    }

    public List<HunCity> hunCities() {
        TypedQuery<HunCity> query = em.createQuery("SELECT c FROM HunCity c ORDER BY c.name", HunCity.class);
        return query.getResultList();
    }

    public List<SatelitElhElement> satelitElhElements(@NotNull List<Integer> elhIds) {
        TypedQuery<SatelitElhElement> query = em.createQuery("SELECT e FROM SatelitElhElement e WHERE e.id IN :ids", SatelitElhElement.class);
        query.setParameter("ids", elhIds);

        return query.getResultList();
    }


}
