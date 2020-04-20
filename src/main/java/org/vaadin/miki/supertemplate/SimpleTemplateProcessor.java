package org.vaadin.miki.supertemplate;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.polymertemplate.DefaultTemplateParser;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.NpmTemplateParser;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.polymertemplate.TemplateParser;
import com.vaadin.flow.server.VaadinService;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

/**
 * Reference implementation of {@link TemplateProcessor}.
 * This is a singleton (unless subclassed, of course).
 * @author miki
 * @since 2020-04-20
 */
public class SimpleTemplateProcessor implements TemplateProcessor {

    /**
     * An active instance.
     */
    private static final TemplateProcessor INSTANCE = new SimpleTemplateProcessor();

    /**
     * Returns active instance of this class. This is a singleton, so the instance is always the same.
     * @return An instance of this class.
     */
    public static TemplateProcessor getInstance() {
        return INSTANCE;
    }

    private SimpleTemplateProcessor() {} // no instances allowed

    @Override
    public void processTemplate(PolymerTemplate<?> template, Collection<TemplateFieldConfigurator> configurators) {
        TemplateParser parser = VaadinService.getCurrent().getDeploymentConfiguration()
                .isCompatibilityMode() ? DefaultTemplateParser.getInstance()
                : NpmTemplateParser.getInstance();
        VaadinService service = VaadinService.getCurrent();

        String tag = template.getClass().getAnnotation(Tag.class).value();

        @SuppressWarnings("unchecked")
        final TemplateParser.TemplateData templateData = parser.getTemplateContent((Class<? extends PolymerTemplate<?>>) template.getClass(), tag, service);

        final Element element = templateData.getTemplateElement();

        Arrays.stream(template.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class) && Component.class.isAssignableFrom(field.getType()))
                .forEach(field -> this.prepareField(field, template, element.selectFirst("#"+field.getAnnotation(Id.class).value()), configurators));

    }

    /**
     * Prepares the field by updating its value with whatever is found in the template.
     * @param field Field of the template class.
     * @param template Template object.
     * @param element HTML element that corresponds to the field.
     * @param configurators Extra configurators to use.
     */
    protected void prepareField(Field field, PolymerTemplate<?> template, Element element, Collection<TemplateFieldConfigurator> configurators) {
        boolean oldAccessible = field.isAccessible();
        field.setAccessible(true);
        try {
            Object value = field.get(template);

            // go through attributes (string-based only for now)
            element.attributes().forEach(attribute -> this.updateObject(value, attribute));

            // go through all exceptional configuration required
            configurators.forEach(configurator -> configurator.configureFieldValue(field, value, template, element));

            field.setAccessible(oldAccessible);
        } catch (IllegalAccessException e) {
            // ignore
        }
    }

    /**
     * Updates the object by calling all its setters that accept a {@link String} and have a matching name (attribute {@code foo="bar"} will result in a call to {@code object.setFoo("bar")}.
     * @param object Object to update.
     * @param attribute Attribute to take values from.
     */
    protected void updateObject(Object object, Attribute attribute) {
        String attributeName = attribute.getKey();
        try {
            final Method setter = object.getClass()
                    .getMethod("set" + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1), String.class);
            setter.invoke(object, attribute.getValue());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // ignore
        }
    }

}
