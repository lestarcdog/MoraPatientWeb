package hu.mora.web.model.novadb.beap;

import com.google.common.base.MoreObjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

    @XmlElement(name = "VORNAME")
    private String vorname;
    @XmlElement(name = "NAME")
    private String name;
    @XmlElement(name = "UserID")
    private String UserId;

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

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String fullHungarianName() {
        return name + " " + vorname;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("vorname", vorname)
                .add("name", name)
                .add("UserId", UserId)
                .toString();
    }
}
