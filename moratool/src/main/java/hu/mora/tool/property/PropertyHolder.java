package hu.mora.tool.property;

import javafx.beans.property.Property;

import java.util.HashSet;
import java.util.Set;

/**
 * JavaFX {@link Property} are binded with {@link javafx.beans.WeakListener} which are garbage collected if they are
 * not appear in the UI (in the stage objects). I need to reference them somewhere. That is why this holder.
 */
public class PropertyHolder {
    public static final Set<Property> L = new HashSet<>();
}
