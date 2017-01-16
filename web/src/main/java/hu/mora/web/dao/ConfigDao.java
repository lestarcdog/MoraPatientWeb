package hu.mora.web.dao;

import com.google.common.base.Joiner;
import hu.mora.web.model.HunCity;
import hu.mora.web.model.config.Config;
import hu.mora.web.model.novadb.SatelitElhElement;
import hu.mora.web.service.config.Configs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;

@Stateless
public class ConfigDao {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigDao.class);

    private static final String SQL_QUERY = "SELECT c FROM Config c WHERE c.name = :key";

    @PersistenceContext(unitName = "MoraPU")
    private EntityManager em;

    public Config getValue(Configs config) {
        TypedQuery<Config> query = em.createQuery(SQL_QUERY, Config.class);
        query.setParameter("key", config.name());
        return query.getSingleResult();
    }

    public void setValue(Configs config, String newValue) {
        Config entity = new Config(config.name(), newValue);
        em.merge(entity);
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

    public List<SatelitElhElement> allSatelitElhelemets() {
        TypedQuery<SatelitElhElement> query = em.createQuery("SELECT e FROM SatelitElhElement e ", SatelitElhElement.class);
        return query.getResultList();
    }

    public List<SatelitElhElement> satelitElementWithParent(@NotNull List<Integer> ids) {
        String sql = "WITH RECURSIVE T(ID,NAME_ENG,PARENT_ID,VISIBLE) AS ( " +
                "    SELECT ID,NAME_ENG,PARENT_ID,VISIBLE FROM SATELIT_ELHELEMENT WHERE ID IN(" + Joiner.on(",").join(ids) + ")" +
                "    UNION ALL " +
                "    SELECT e.ID,e.NAME_ENG,e.PARENT_ID,e.VISIBLE FROM T t " +
                "    INNER JOIN SATELIT_ELHELEMENT e ON t.PARENT_ID = e.ID " +
                ") " +
                "SELECT DISTINCT * FROM T";
        Query query = em.createNativeQuery(sql, SatelitElhElement.class);
        return (List<SatelitElhElement>) query.getResultList();
    }


}
