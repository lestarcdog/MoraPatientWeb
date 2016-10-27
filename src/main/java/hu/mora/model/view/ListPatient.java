package hu.mora.model.view;

import hu.mora.model.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ListPatient {
    private Integer id;
    private String name;
    private LocalDate birthDate;
    private String birthPlace;
    private String phone;
    private LocalDateTime lastModified;

    public ListPatient(Patient patient) {
        this.id = patient.getId();
        this.name = patient.getName();
        this.birthDate = patient.getBirthDate();
        this.birthPlace = patient.getBirthPlace();
        this.phone = patient.getPhone();
        this.lastModified = patient.getLastModified();
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

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListPatient that = (ListPatient) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (birthDate != null ? !birthDate.equals(that.birthDate) : that.birthDate != null) return false;
        if (birthPlace != null ? !birthPlace.equals(that.birthPlace) : that.birthPlace != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        return lastModified != null ? lastModified.equals(that.lastModified) : that.lastModified == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (birthPlace != null ? birthPlace.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
        return result;
    }
}
