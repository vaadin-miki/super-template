package org.vaadin.miki.supertemplate;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.jsoup.nodes.Element;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * {@link TemplateFieldConfigurator} ensuring that a {@link Button} has an {@link Icon}.
 * @author miki
 * @since 2020-04-20
 */
class UpdateButtonIconConfigurator implements TemplateFieldConfigurator {

    /**
     * Constructs a VaadinIcon from icon element. Helper for buttons.
     * @param iconElement Element to create Icon from.
     * @return An instance of a Vaadin Icon, or null if the icon is not a Vaadin Icon.
     */
    private Icon constructIcon(Element iconElement) {
        if(iconElement.attr("icon").startsWith("vaadin:")) {
            Icon icon = VaadinIcon.valueOf(iconElement.attr("icon").substring(7).toUpperCase().replace("-", "_")).create();
            // icon may have a colour (hidden in style), id and class
            if (iconElement.hasAttr("style")) {
                // there may be other styles
                Arrays.stream(iconElement.attr("style").split(";"))
                        .map(s -> s.split(":"))
                        .filter(def -> "color".equals(def[0]))
                        .findFirst()
                        .ifPresent(def -> icon.setColor(def[1]));
            }
            if (iconElement.hasAttr("id"))
                icon.setId(iconElement.attr("id"));
            if (iconElement.hasAttr("class"))
                icon.addClassName(iconElement.attr("class"));
            return icon;
        }
        else return null;
    }

    @Override
    public void configureFieldValue(Field field, Object value, PolymerTemplate<?> template, Element element) {
        if(value instanceof Button && !element.getElementsByTag("iron-icon").isEmpty()) {
            Element iconElement = element.getElementsByTag("iron-icon").get(0);
            // only Vaadin Icons are supported for now
            ((Button) value).setIcon(constructIcon(iconElement));
        }
    }
}
