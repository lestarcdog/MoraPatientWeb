package hu.mora.web.model.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONFIG")
public class Config {

    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "VALUE")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Config config = (Config) o;

        if (name != null ? !name.equals(config.name) : config.name != null) return false;
        return value != null ? value.equals(config.value) : config.value == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Config{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
