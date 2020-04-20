package org.vaadin.miki.supertemplate;

import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.jsoup.nodes.Element;

import java.lang.reflect.Field;

/**
 * Marker interface for objects that perform extra configuration on the field.
 * @author miki
 * @since 2020-04-20
 */
@FunctionalInterface
public interface TemplateFieldConfigurator {

    /**
     * Configures the value of the field. All attributes of {@code value} supported by default are already set to whatever was in {@code element} by the time this method is called.
     * @param field Field currently being processed.
     * @param value Value of the field.
     * @param template Template object.
     * @param element The html element that corresponds to the field.
     */
    void configureFieldValue(Field field, Object value, PolymerTemplate<?> template, Element element);

}
