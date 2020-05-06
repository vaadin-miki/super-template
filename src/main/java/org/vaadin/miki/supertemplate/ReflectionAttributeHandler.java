package org.vaadin.miki.supertemplate;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;

public class ReflectionAttributeHandler implements ObjectAttributeHandler {
    @Override
    public boolean updateObject(Object object, Attribute attribute, Element element) {
        return false;
    }
}
