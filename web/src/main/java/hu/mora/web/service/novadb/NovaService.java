package hu.mora.web.service.novadb;

import hu.mora.web.dao.ConfigDao;
import hu.mora.web.exception.MoraException;
import hu.mora.web.model.novadb.NovaResult;
import hu.mora.web.model.novadb.NovaResultChild;
import hu.mora.web.model.novadb.SatelitElhElement;
import hu.mora.web.model.novadb.beap.Ergebnis;
import hu.mora.web.model.novadb.beap.Person;
import hu.mora.web.model.novadb.nrf.NrfResult;
import hu.mora.web.model.novadb.nrf.NrfRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class NovaService {

    private static final Logger LOG = LoggerFactory.getLogger(NovaService.class);

    @Inject
    NovaDbConnector novaDb;

    @Inject
    ConfigDao configDao;


    public List<Person> allPatients() {
        return novaDb.allPatients(false);
    }

    public List<Ergebnis> resultsOfPatient(Integer id) {
        return novaDb.patientResults(id);
    }

    public NovaResult patientResult(Integer userId, Integer resultId) {
        List<Ergebnis> ergebnisse = novaDb.patientResults(userId);
        Optional<Ergebnis> ergebnisOptional = ergebnisse.stream().filter(e -> e.getId().equals(resultId)).findFirst();
        Ergebnis ergebnis = ergebnisOptional.orElseThrow(() -> new MoraException("A beteghez nem tartozik ilyen mérési eredmény."));

        NrfResult nrfResult = novaDb.readUserResult(userId, resultId);

        // merge the result together
        NovaResult result = new NovaResult();
        LocalDateTime ergebnisTime = ergebnis.getDatum().atTime(ergebnis.getUhrZeit());
        result.setResultDateTime(ergebnisTime);
        if (!nrfResult.getRows().isEmpty()) {
            result.setRoot(makeHieararchicalResult(nrfResult.getRows()));
        }

        return result;
    }

    private NovaResultChild makeHieararchicalResult(List<NrfRow> rows) {
        List<Integer> ids = rows.stream().map(NrfRow::getSubstanceId).collect(Collectors.toList());
        List<SatelitElhElement> elements = configDao.satelitElementWithParent(ids);

        Map<Integer, NovaResultChild> hierarchy = new HashMap<>();
        // do the hierarchical ordering here
        NovaResultChild root = null;
        for (SatelitElhElement element : elements) {
            // i am not the root so I have a parent
            if (element.getParentId() != null) {
                NovaResultChild parent = hierarchy.getOrDefault(element.getParentId(), new NovaResultChild());
                // put myself in the hierarchy and under my parent
                NovaResultChild me = hierarchy.getOrDefault(element.getId(), new NovaResultChild());
                mapSatelitToNovaChild(element, me);
                hierarchy.put(element.getId(), me);
                parent.addChild(me);

                hierarchy.put(element.getParentId(), parent);
            } else {
                root = hierarchy.getOrDefault(element.getId(), new NovaResultChild());
                mapSatelitToNovaChild(element, root);
                hierarchy.put(element.getId(), root);
            }
        }

        return root;

    }

    private NovaResultChild mapSatelitToNovaChild(SatelitElhElement element, NovaResultChild child) {
        child.setSubstanceId(element.getId());
        child.setNameEng(element.getNameEng());
        return child;
    }
}
