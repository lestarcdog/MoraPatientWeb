package hu.mora.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CITIES")
public class HunCity {

    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "ZIPS")
    private String zips;

    @Column(name = "SMALLCAPS")
    private String smallcaps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmallcaps() {
        return smallcaps;
    }

    public void setSmallcaps(String smallcaps) {
        this.smallcaps = smallcaps;
    }

    public String getZips() {
        return zips;
    }

    public void setZips(String zips) {
        this.zips = zips;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HunCity hunCity = (HunCity) o;

        if (name != null ? !name.equals(hunCity.name) : hunCity.name != null) return false;
        return smallcaps != null ? smallcaps.equals(hunCity.smallcaps) : hunCity.smallcaps == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (smallcaps != null ? smallcaps.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HunCity{" +
                "name='" + name + '\'' +
                ", smallcaps='" + smallcaps + '\'' +
                '}';
    }
}
