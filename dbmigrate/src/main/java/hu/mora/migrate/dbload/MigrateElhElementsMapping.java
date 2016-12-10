package hu.mora.migrate.dbload;

import java.util.List;

public class MigrateElhElementsMapping {

    private Integer oldPatientId;
    private Integer oldTestId;

    public Integer getOldPatientId() {
        return oldPatientId;
    }

    public void setOldPatientId(Integer oldPatientId) {
        this.oldPatientId = oldPatientId;
    }

    public Integer getOldTestId() {
        return oldTestId;
    }

    public void setOldTestId(Integer oldTestId) {
        this.oldTestId = oldTestId;
    }


    public static MigrateElhElementsMapping createFromRow(List<String> row) {
        MigrateElhElementsMapping e = new MigrateElhElementsMapping();
        e.setOldPatientId(Double.valueOf(row.get(0)).intValue());
        e.setOldTestId(Double.valueOf(row.get(3)).intValue());
        return e;
    }
}
