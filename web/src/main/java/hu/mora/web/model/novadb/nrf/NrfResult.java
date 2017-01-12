package hu.mora.web.model.novadb.nrf;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;

public class NrfResult {

    private List<NrfRow> rows = new ArrayList<>();

    public List<NrfRow> getRows() {
        return rows;
    }

    public void add(NrfRow row) {
        rows.add(row);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("rows", rows)
                .toString();
    }
}
