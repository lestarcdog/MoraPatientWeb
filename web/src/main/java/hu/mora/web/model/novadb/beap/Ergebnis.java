package hu.mora.web.model.novadb.beap;

import com.google.common.base.MoreObjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Ergebnis {

    @XmlElement(name = "ID")
    private Integer id;

    @XmlElement(name = "DATUM")
    private String datum;

    @XmlElement(name = "UHRZEIT")
    private String uhrZeit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getUhrZeit() {
        return uhrZeit;
    }

    public void setUhrZeit(String uhrZeit) {
        this.uhrZeit = uhrZeit;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("datum", datum)
                .add("uhrZeit", uhrZeit)
                .toString();
    }
}
