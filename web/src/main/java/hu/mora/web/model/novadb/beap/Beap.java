package hu.mora.web.model.novadb.beap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "DATA")
@XmlAccessorType(XmlAccessType.FIELD)
public class Beap {

    @XmlElement(name = "BENUTZER")
    private List<Benutzer> benutzer;


    public List<Benutzer> getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(List<Benutzer> benutzer) {
        this.benutzer = benutzer;
    }
}
