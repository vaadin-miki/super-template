package org.vaadin.miki.supertemplate;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.data.binder.BeanPropertySet;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

/**
 * Configures bean type of a field of type {@link Grid}.

 * @author miki
 * @since 2020-05-05
 */
public class GridBeanTypeConfigurator implements TemplateFieldConfigurator {

    private static final Logger LOGGER = LoggerFactory.getLogger(GridBeanTypeConfigurator.class);

    @Override
    public void configureFieldValue(Field field, Object value, PolymerTemplate<?> template, Element element) {
        if(value instanceof Grid) {
            try {
                Class<?> dataType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                LOGGER.info("Discovered field {} to be of type Grid<{}>", field.getName(), dataType.getName());

                Field beanTypeField = Grid.class.getDeclaredField("beanType");
                if(beanTypeField.trySetAccessible())
                    beanTypeField.set(value, dataType);

                Field propertySetField = Grid.class.getDeclaredField("propertySet");
                propertySetField.setAccessible(true);
                propertySetField.set(value, BeanPropertySet.get(dataType));
            }
            catch(ClassCastException cce) {
                LOGGER.info("Could not discover bean type for field {}. Please make it Grid<YourDataType>.", field.getName());
            } catch (IllegalAccessException | NoSuchFieldException e) {
                LOGGER.warn("Could not access Grid.beanType and Grid.propertySet. Field {} will not work properly.", field.getName());
            }
        }
    }
}
