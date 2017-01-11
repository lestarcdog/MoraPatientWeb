package hu.mora.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IS_MALE")
    private Boolean isMale;

    @Column(name = "BIRTH_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Column(name = "BIRTH_PLACE")
    private String birthPlace;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CITY")
    private String city;

    @Column(name = "ZIP")
    private String zip;

    @Column(name = "STREET")
    private String street;

    @Column(name = "LAST_MODIFIED")
    private Timestamp lastModified;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("therapyDate desc")
    @JoinColumn(name = "PATIENT_ID", referencedColumnName = "ID")
    private Set<Therapy> therapies;

    @OneToMany(fetch = FetchType.EAGER)
    @OrderBy("id asc")
    @JoinTable(name = "ELHELEMENTS_PATIENTS",
            joinColumns = {@JoinColumn(name = "PATIENT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ELHELEMENTS_ID")})
    private Set<ElhElement> elhElements;


    @Column(name = "NOVA_PATIENT_ID")
    private Integer novaDbPatientId;


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

    public Boolean getMale() {
        return isMale;
    }

    public void setMale(Boolean male) {
        isMale = male;
    }

    public LocalDate getBirthDate() {
        return birthDate.toLocalDate();
    }

    public void setBirthDate(@NotNull LocalDate birthDate) {
        this.birthDate = Date.valueOf(birthDate);
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public Integer getNovaDbPatientId() {
        return novaDbPatientId;
    }

    public void setNovaDbPatientId(Integer novaDbPatientId) {
        this.novaDbPatientId = novaDbPatientId;
    }

    public LocalDateTime getLastModified() {
        if (lastModified != null) {
            return lastModified.toLocalDateTime();
        } else {
            return null;
        }
    }

    public void setLastModified(LocalDateTime lastModified) {
        if (lastModified != null) {
            this.lastModified = Timestamp.valueOf(lastModified);
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Set<Therapy> getTherapies() {
        return therapies;
    }

    public void setTherapies(Set<Therapy> therapies) {
        this.therapies = therapies;
    }

    public Set<ElhElement> getElhElements() {
        return elhElements;
    }

    public void setElhElements(Set<ElhElement> elhElements) {
        this.elhElements = elhElements;
    }

    @PrePersist
    @PreUpdate
    public void preHook() {
        setLastModified(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isMale=" + isMale +
                ", birthDate=" + birthDate +
                ", birthPlace='" + birthPlace + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", street='" + street + '\'' +
                ", lastModified=" + lastModified +
                ", therapies=" + therapies +
                ", elhElements=" + elhElements +
                '}';
    }

}
