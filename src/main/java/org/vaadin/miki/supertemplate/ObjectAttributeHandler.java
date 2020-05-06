package org.vaadin.miki.supertemplate;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;

/**
 * A marker interface for objects that can update an attribute.
 */
@FunctionalInterface
public interface ObjectAttributeHandler {

    /**
     * Updates the object, based on the value found in the passed attribute.
     * @param object Object to update.
     * @param attribute Attribute.
     * @param element Element that corresponds to the object, for reference.
     * @return {@code true} when the object has been successfully updated, {@code false} otherwise.
     */
    boolean updateObject(Object object, Attribute attribute, Element element);

}
