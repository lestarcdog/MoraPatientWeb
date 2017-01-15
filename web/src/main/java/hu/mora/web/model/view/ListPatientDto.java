package hu.mora.web.model.view;

import hu.mora.web.model.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ListPatientDto {
    private Integer id;
    private String name;
    private LocalDate birthDate;
    private String city;
    private String phone;
    private LocalDateTime lastModified;
    private Integer novaPatientId;

    public ListPatientDto(Patient patient) {
        this.id = patient.getId();
        this.name = patient.getName();
        this.birthDate = patient.getBirthDate();
        this.city = patient.getCity();
        this.phone = patient.getPhone();
        this.lastModified = patient.getLastModified();
        this.novaPatientId = patient.getNovaPatientId();
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getNovaPatientId() {
        return novaPatientId;
    }

    public void setNovaPatientId(Integer novaPatientId) {
        this.novaPatientId = novaPatientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListPatientDto that = (ListPatientDto) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
