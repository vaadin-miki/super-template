package org.vaadin.miki.supertemplate;

import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An extension of {@link PolymerTemplate} that puts some properties from client side to server side.
 * Note: this class is not supposed to be constructed directly.
 * @param <M> Type of {@link TemplateModel} used.
 * @author miki
 * @since 2020-04-20
 */
public class SuperTemplate<M extends TemplateModel> extends PolymerTemplate<M> {

    /**
     * Constructs the template with given extra configurators.
     * Uses all default configurators specified in {@link TemplateFieldConfigurators#DEFAULT_CONFIGURATORS}.
     * Uses {@link SimpleTemplateProcessor#getInstance()} as {@link TemplateProcessor}.
     * @param configurators Configurators to use.
     */
    protected SuperTemplate(TemplateFieldConfigurator... configurators) {
        this(SimpleTemplateProcessor.getInstance(), false, configurators);
    }

    /**
     * Constructs the template with given {@link TemplateProcessor}, extra configurators and an option to skip default configurators.
     * @param templateProcessor An intance of {@link TemplateProcessor}.
     * @param skipRecommendedConfigurators Whether or not skip adding {@link TemplateFieldConfigurators#DEFAULT_CONFIGURATORS} to {@code configurators} parameter.
     * @param configurators Extra {@link TemplateFieldConfigurator}s to use.
     */
    protected SuperTemplate(TemplateProcessor templateProcessor, boolean skipRecommendedConfigurators, TemplateFieldConfigurator... configurators) {
        super();
        final List<TemplateFieldConfigurator> list = skipRecommendedConfigurators ?
                new ArrayList<>() :
                new ArrayList<>(TemplateFieldConfigurators.DEFAULT_CONFIGURATORS);
        list.addAll(Arrays.asList(configurators));
        templateProcessor.processTemplate(this, list);
    }

}
