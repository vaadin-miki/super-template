package org.vaadin.miki.supertemplate;

import com.github.mvysny.kaributesting.v10.MockNpmTemplateParser;
import com.github.mvysny.kaributesting.v10.MockVaadin;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.MissingResourceException;

public class SuperTemplateTest {

    @Before
    public void setUp() {
        MockNpmTemplateParser.Companion.getCustomLoaders().add((s, s1) -> {
            try {
                return new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource(s+".js").toURI())));
            } catch (IOException | URISyntaxException e) {
                throw new MissingResourceException(s, s+".js", "polymer file");
            }
        });
        MockVaadin.setup();

    }

    @After
    public void tearDown() {
        MockVaadin.tearDown();
    }

    @Test
    public void testSuperViewHasPropertiesFromTemplate() {
        TestView superView = new TestView();
        Assert.assertNotNull(superView);
        TextField field = superView.getTextField();
        Assert.assertNotNull(field);
        Assert.assertEquals("a-text-field", field.getId().orElse(null));
        Assert.assertEquals("Hello", field.getPlaceholder());
        Span span = superView.getSpan();
        Assert.assertNotNull(span);
        Assert.assertEquals("a-span", span.getId().orElse(null));
        Assert.assertEquals("World", span.getText());
        Grid<Dummy> grid = superView.getGrid();
        Assert.assertNotNull(grid);
        Assert.assertSame(Dummy.class, grid.getBeanType());
        Assert.assertNotNull(grid.addColumn("contents")); // adding columns should work
    }

    @Test
    public void testVaadinViewDoesNotHavePropertiesFromTemplate() {
        VaadinView vaadinView = new VaadinView();
        Assert.assertNotNull(vaadinView);
        Assert.assertNotNull(vaadinView.getTextField());
        Assert.assertFalse(vaadinView.getTextField().getId().isPresent());
        Assert.assertNull(vaadinView.getTextField().getPlaceholder());
        Assert.assertFalse(vaadinView.getSpan().getId().isPresent());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testVaadinViewDoesNotHaveProperGrid() {
        VaadinView vaadinView = new VaadinView();
        Assert.assertNotNull(vaadinView);
        Assert.assertNotNull(vaadinView.getGrid());
        Assert.assertNull(vaadinView.getGrid().getBeanType());
        vaadinView.getGrid().addColumn("contents");
    }

}