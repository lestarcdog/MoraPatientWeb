package hu.mora.web.model.novadb;


import com.google.common.base.MoreObjects;

import java.time.LocalDateTime;

public class NovaResult {

    private LocalDateTime resultDateTime;
    private NovaResultChild root;

    public LocalDateTime getResultDateTime() {
        return resultDateTime;
    }

    public void setResultDateTime(LocalDateTime resultDateTime) {
        this.resultDateTime = resultDateTime;
    }

    public NovaResultChild getRoot() {
        return root;
    }

    public void setRoot(NovaResultChild root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("resultDateTime", resultDateTime)
                .add("root", root)
                .toString();
    }
}
