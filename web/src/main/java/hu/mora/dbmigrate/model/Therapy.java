package hu.mora.dbmigrate.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "THERAPIES")
public class Therapy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "THERAPY_DATE")
    private Date therapyDate;

    @Column(name = "THERAPY")
    private String therapy;

    @Column(name = "TREATMENT")
    private String treatment;

    @Column(name = "INTERVIEW")
    private String interview;

    @Column(name = "RESULT")
    private String result;

    @OneToOne
    @JoinColumn(name = "THERAPIST_ID", referencedColumnName = "ID")
    private Therapist therapist;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getTherapyDate() {
        return therapyDate.toLocalDate();
    }

    public void setTherapyDate(LocalDate therapyDate) {
        this.therapyDate = Date.valueOf(therapyDate);
    }

    public String getTherapy() {
        return therapy;
    }

    public void setTherapy(String therapy) {
        this.therapy = therapy;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getInterview() {
        return interview;
    }

    public void setInterview(String interview) {
        this.interview = interview;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public Therapist getTherapist() {
        return therapist;
    }

    public void setTherapist(Therapist therapist) {
        this.therapist = therapist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Therapy therapy1 = (Therapy) o;

        if (id != null ? !id.equals(therapy1.id) : therapy1.id != null) return false;
        if (therapyDate != null ? !therapyDate.equals(therapy1.therapyDate) : therapy1.therapyDate != null) return false;
        if (therapy != null ? !therapy.equals(therapy1.therapy) : therapy1.therapy != null) return false;
        if (treatment != null ? !treatment.equals(therapy1.treatment) : therapy1.treatment != null) return false;
        if (interview != null ? !interview.equals(therapy1.interview) : therapy1.interview != null) return false;
        if (result != null ? !result.equals(therapy1.result) : therapy1.result != null) return false;
        return therapist != null ? therapist.equals(therapy1.therapist) : therapy1.therapist == null;

    }

    @Override
    public int hashCode() {
        int result1 = id != null ? id.hashCode() : 0;
        result1 = 31 * result1 + (therapyDate != null ? therapyDate.hashCode() : 0);
        result1 = 31 * result1 + (therapy != null ? therapy.hashCode() : 0);
        result1 = 31 * result1 + (treatment != null ? treatment.hashCode() : 0);
        result1 = 31 * result1 + (interview != null ? interview.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (therapist != null ? therapist.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "Therapy{" +
                "id=" + id +
                ", therapyDate=" + therapyDate +
                ", therapy='" + therapy + '\'' +
                ", treatment='" + treatment + '\'' +
                ", interview='" + interview + '\'' +
                ", result='" + result + '\'' +
                ", therapist=" + therapist +
                '}';
    }
}
