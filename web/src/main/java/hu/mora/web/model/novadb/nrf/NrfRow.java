package hu.mora.web.model.novadb.nrf;

import com.google.common.base.MoreObjects;

public class NrfRow {

    private Integer substanceId;
    private Byte jn;
    private Byte modus;
    private Short potenz;
    private Byte kanal;
    private Byte ausgesetzt;
    private Short wert;
    private Byte wabe;
    private Integer verstearkung;

    public Integer getSubstanceId() {
        return substanceId;
    }

    public void setSubstanceId(Integer substanceId) {
        this.substanceId = substanceId;
    }

    public Byte getJn() {
        return jn;
    }

    public void setJn(Byte jn) {
        this.jn = jn;
    }

    public Byte getModus() {
        return modus;
    }

    public void setModus(Byte modus) {
        this.modus = modus;
    }

    public Short getPotenz() {
        return potenz;
    }

    public void setPotenz(Short potenz) {
        this.potenz = potenz;
    }

    public Byte getKanal() {
        return kanal;
    }

    public void setKanal(Byte kanal) {
        this.kanal = kanal;
    }

    public Byte getAusgesetzt() {
        return ausgesetzt;
    }

    public void setAusgesetzt(Byte ausgesetzt) {
        this.ausgesetzt = ausgesetzt;
    }

    public Short getWert() {
        return wert;
    }

    public void setWert(Short wert) {
        this.wert = wert;
    }

    public Byte getWabe() {
        return wabe;
    }

    public void setWabe(Byte wabe) {
        this.wabe = wabe;
    }

    public Integer getVerstearkung() {
        return verstearkung;
    }

    public void setVerstearkung(Integer verstearkung) {
        this.verstearkung = verstearkung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NrfRow nrfRow = (NrfRow) o;

        return substanceId != null ? substanceId.equals(nrfRow.substanceId) : nrfRow.substanceId == null;
    }

    @Override
    public int hashCode() {
        return substanceId.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("substanceId", substanceId)
                .add("jn", jn)
                .add("modus", modus)
                .add("potenz", potenz)
                .add("kanal", kanal)
                .add("ausgesetzt", ausgesetzt)
                .add("wert", wert)
                .add("wabe", wabe)
                .add("verstearkung", verstearkung)
                .toString();
    }
}
