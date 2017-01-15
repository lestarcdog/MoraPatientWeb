package hu.mora.web.model.novadb;

import hu.mora.web.model.novadb.beap.Person;

import java.time.LocalDate;

public class NovaPatient {
    private Integer id;
    private String name;
    private LocalDate birthDate;
    private Integer moraPatientId;

    public NovaPatient() {
    }

    public NovaPatient(Person person) {
        this.id = person.getUserId();
        this.name = person.fullHungarianName();
        this.birthDate = person.getGeburtsDatum();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getMoraPatientId() {
        return moraPatientId;
    }

    public void setMoraPatientId(Integer moraPatientId) {
        this.moraPatientId = moraPatientId;
    }
}
