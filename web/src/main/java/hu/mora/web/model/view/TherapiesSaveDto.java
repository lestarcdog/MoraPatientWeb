package hu.mora.web.model.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hu.mora.web.dao.MoraDao;
import hu.mora.web.exception.MoraException;
import hu.mora.web.model.Therapist;
import hu.mora.web.model.Therapy;

import java.time.LocalDate;
import java.util.List;

public class TherapiesSaveDto {
    private List<TherapyUpload> therapies;
    private Integer therapistId;

    public List<TherapyUpload> getTherapies() {
        return therapies;
    }

    public void setTherapies(List<TherapyUpload> therapies) {
        this.therapies = therapies;
    }


    public Integer getTherapistId() {
        return therapistId;
    }

    public void setTherapistId(Integer therapistId) {
        this.therapistId = therapistId;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TherapyUpload {
        private Integer id;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate therapyDate;
        private String therapy;
        private String treatment;
        private String interview;
        private String result;
        private Therapist therapist;

        public Therapy toTherapyEntity(MoraDao dao) {
            Therapy t;
            if (id != null) {
                t = dao.findTherapy(id).orElseThrow(() -> new MoraException("Nem létező terápia volt módosítva."));
            } else {
                t = new Therapy();
            }
            t.setInterview(interview);
            t.setTherapy(therapy);
            t.setTreatment(treatment);
            t.setResult(result);
            t.setTherapyDate(therapyDate);
            Therapist therapist = dao.findTherapist(this.therapist.getId()).orElseThrow(() -> new MoraException("Nem létező terapeuta"));
            t.setTherapist(therapist);

            return t;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public LocalDate getTherapyDate() {
            return therapyDate;
        }

        public void setTherapyDate(LocalDate therapyDate) {
            this.therapyDate = therapyDate;
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
    }
}
