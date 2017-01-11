package hu.mora.web.model.novadb;


import java.time.LocalDateTime;

public class NovaResult {

    private Integer resultId;
    private LocalDateTime date;
    private Integer substanceId;
    //.. jn and other stuff see NRFParser


    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getSubstanceId() {
        return substanceId;
    }

    public void setSubstanceId(Integer substanceId) {
        this.substanceId = substanceId;
    }
}
