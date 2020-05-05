package org.vaadin.miki.supertemplate;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("test-view")
@JsModule("./test-view.js")
public class TestView extends SuperTemplate<TestView.TestModel> {

    @Id("a-text-field")
    private TextField textField;

    @Id("a-span")
    private Span span;

    @Id("a-grid")
    private Grid<Dummy> grid;

    TextField getTextField() {
        return textField;
    }

    Span getSpan() {
        return span;
    }

    Grid<Dummy> getGrid() {
        return grid;
    }

    public interface TestModel extends TemplateModel {} // nothing here

}
