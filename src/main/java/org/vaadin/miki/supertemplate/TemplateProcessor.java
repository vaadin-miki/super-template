package org.vaadin.miki.supertemplate;

import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

import java.util.Collection;

/**
 * Marker interface for objects that process a {@link PolymerTemplate} and update its state.
 * @author miki
 * @since 2020-04-20
 */
@FunctionalInterface
public interface TemplateProcessor {

    /**
     * Processes given template.
     * @param template Template to process. Never {@code null}.
     * @param configurators Extra {@link TemplateFieldConfigurator}s to apply.
     */
    void processTemplate(PolymerTemplate<?> template, Collection<TemplateFieldConfigurator> configurators);

}
