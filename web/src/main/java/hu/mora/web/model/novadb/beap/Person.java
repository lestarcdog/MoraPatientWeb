package hu.mora.web.model.novadb.beap;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

    private static final Logger LOG = LoggerFactory.getLogger(Person.class);

    private static final DateTimeFormatter GEBURTSDATUM_F = DateTimeFormatter.ofPattern("d.M.yyyy");

    public Person() {
    }

    public Person(Integer userIdInt) {
        this.userIdInt = userIdInt;
    }

    @XmlElement(name = "UserID")
    private String userId;

    @XmlTransient
    private Integer userIdInt;

    @XmlElement(name = "VORNAME")
    private String vorname;

    @XmlElement(name = "GEBDAT")
    private String geburtsDatum;

    @XmlElement(name = "NAME")
    private String name;

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        if (userIdInt == null) {
            userIdInt = Integer.valueOf(userId);
        }
        return userIdInt;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String fullHungarianName() {
        return name + " " + vorname;
    }

    public LocalDate getGeburtsDatum() {
        try {
            return Strings.isNullOrEmpty(geburtsDatum) ? null : LocalDate.parse(this.geburtsDatum, GEBURTSDATUM_F);
        } catch (Exception e) {
            LOG.warn("Could not parse geburtsdatum {} to person {}", geburtsDatum, this.toString());
            return null;
        }
    }

    public void setGeburtsDatum(String geburtsDatum) {
        this.geburtsDatum = geburtsDatum;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("vorname", vorname)
                .add("geburtsDatum", geburtsDatum)
                .add("name", name)
                .add("userId", userId)
                .toString();
    }
}
