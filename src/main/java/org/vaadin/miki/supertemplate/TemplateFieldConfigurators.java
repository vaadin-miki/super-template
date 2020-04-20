package org.vaadin.miki.supertemplate;

import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.icon.Icon;

import java.util.Arrays;
import java.util.Collection;

/**
 * Container class for most common {@link TemplateFieldConfigurator}s.
 * @author miki
 * @since 2020-04-20
 */
public class TemplateFieldConfigurators {

    private TemplateFieldConfigurators() {} // instances not allowed

    /**
     * If the value in the field implements {@link HasText}, sets its text to whatever the element's text is.
     */
    public static final TemplateFieldConfigurator SET_TEXT =
        (field, value, template, element) -> {
        if(value instanceof HasText && element.hasText() && !element.hasAttr("text"))
            ((HasText) value).setText(element.text());
    };

    /**
     * If the value in the field is an {@link Icon}, updates the icon image to whatever the element specifies.
     */
    public static final TemplateFieldConfigurator CREATE_STANDALONE_ICON =
        (field, value, template, element) -> {
        if(value instanceof Icon && element.hasAttr("icon")) {
            element.attributes().forEach(attribute -> {
                if("icon".equalsIgnoreCase(attribute.getKey()))
                    ((Icon) value).getElement().setAttribute("icon", element.attr("icon"));
                else ((Icon) value).getElement().setAttribute(attribute.getKey(), attribute.getValue());
            });
        }
    };

    /**
     * Updates a button to include an icon, if needed.
     */
    // buttons require extra processing, they may have an icon
    public static final TemplateFieldConfigurator UPDATE_BUTTON_ICON = new UpdateButtonIconConfigurator();

    /**
     * Contains all of the {@link TemplateFieldConfigurator}s defined as {@code public static final} fields in this class.
     */
    public static final Collection<TemplateFieldConfigurator> DEFAULT_CONFIGURATORS = Arrays.asList(
            SET_TEXT, CREATE_STANDALONE_ICON, UPDATE_BUTTON_ICON
    );

}
