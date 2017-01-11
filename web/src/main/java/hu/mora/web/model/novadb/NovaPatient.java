package hu.mora.web.model.novadb;

import java.time.LocalDate;
import java.util.Set;

public class NovaPatient {

    private Integer novaId;
    private String name;
    private LocalDate birthDate;
    private Set<NovaResult> novaResults;

    public Integer getNovaId() {
        return novaId;
    }

    public void setNovaId(Integer novaId) {
        this.novaId = novaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Set<NovaResult> getNovaResults() {
        return novaResults;
    }

    public void setNovaResults(Set<NovaResult> novaResults) {
        this.novaResults = novaResults;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NovaPatient that = (NovaPatient) o;

        return novaId != null ? novaId.equals(that.novaId) : that.novaId == null;
    }

    @Override
    public int hashCode() {
        return novaId != null ? novaId.hashCode() : 0;
    }
}
