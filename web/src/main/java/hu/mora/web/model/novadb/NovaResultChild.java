package hu.mora.web.model.novadb;

import com.google.common.base.MoreObjects;
import hu.mora.web.model.novadb.nrf.NrfRow;

import java.util.ArrayList;
import java.util.List;

public class NovaResultChild {

    private Integer substanceId;
    private String nameEng;
    private NrfRow rawdata;
    private List<NovaResultChild> children = new ArrayList<>();

    public Integer getSubstanceId() {
        return substanceId;
    }

    public void setSubstanceId(Integer substanceId) {
        this.substanceId = substanceId;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public List<NovaResultChild> getChildren() {
        return children;
    }

    public void addChild(NovaResultChild child) {
        children.add(child);
    }

    public void setChildren(List<NovaResultChild> children) {
        this.children = children;
    }

    public NrfRow getRawdata() {
        return rawdata;
    }

    public void setRawdata(NrfRow rawdata) {
        this.rawdata = rawdata;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("substanceId", substanceId)
                .add("nameEng", nameEng)
                .add("children", children)
                .toString();
    }
}
