package hu.mora.web.model.novadb.beap;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Ergebnis {

    private static final Logger LOG = LoggerFactory.getLogger(Ergebnis.class);

    private static final DateTimeFormatter DATUM_F = DateTimeFormatter.ofPattern("d.M.yyyy");
    private static final DateTimeFormatter UHRZEIT_F = DateTimeFormatter.ofPattern("HH:mm:ss");

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

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public void setUhrZeit(String uhrZeit) {
        this.uhrZeit = uhrZeit;
    }

    public LocalDate getDatum() {
        try {
            return Strings.isNullOrEmpty(datum) ? null : LocalDate.parse(datum, DATUM_F);
        } catch (Exception e) {
            LOG.warn("Could not parse datum {} to ergebnis {}", datum, this.toString());
            return null;
        }
    }

    public LocalTime getUhrZeit() {
        try {
            return Strings.isNullOrEmpty(uhrZeit) ? null : LocalTime.parse(uhrZeit, UHRZEIT_F);
        } catch (Exception e) {
            LOG.warn("Could not parse uhrzeit {} to ergebnis {}", uhrZeit, this.toString());
            return null;
        }
    }

    public LocalDateTime getDateTime() {
        if (getDatum() != null && getUhrZeit() != null) {
            return getDatum().atTime(getUhrZeit());
        } else {
            return null;
        }
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
