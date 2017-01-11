package hu.mora.web.model.novadb.beap;

import com.google.common.base.MoreObjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Benutzer {

    @XmlElement(name = "PERSON")
    private Person person;

    @XmlElementWrapper(name = "ERGEBNISSE")
    @XmlElement(name = "ERGEBNIS")
    private List<Ergebnis> ergebnisse;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Ergebnis> getErgebnisse() {
        return ergebnisse;
    }

    public void setErgebnisse(List<Ergebnis> ergebnisse) {
        this.ergebnisse = ergebnisse;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("person", person)
                .add("ergebnisse", ergebnisse)
                .toString();
    }
}
