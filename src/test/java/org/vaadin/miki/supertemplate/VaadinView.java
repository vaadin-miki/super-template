package org.vaadin.miki.supertemplate;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("test-view")
@JsModule("./test-view.js")
public class VaadinView extends PolymerTemplate<TestView.TestModel> {

    @Id("a-text-field")
    private TextField textField;

    @Id("a-span")
    private Span span;

    TextField getTextField() {
        return textField;
    }

    Span getSpan() {
        return span;
    }

    public interface TestModel extends TemplateModel {} // nothing here

}
