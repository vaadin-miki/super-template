# SuperTemplate

This is an extension of Vaadin 14.X `PolymerTemplate` class. It enables server-side access to properties defined in the template file, which is normally not possible. It also attempts to solve a few general issues with templates.

## Limitations

`SuperTemplate` is most likely not production ready. Due to complexity of Vaadin Flow it will most likely never be production ready.
 
Here are the limitations:
* it only works for fields that are annotated in Java with `@Id` and have an `id` attribute set in the template file (in Designer terms - components that are linked to the Java companion file); 
* it only supports passing of attributes present in the HTML markup when there is a corresponding setter that accepts `String`; for example, `<my-component attr="value">` will call `public void setAttr(String s)` - if the method is missing, nothing will happen.

## Installation

### From Vaadin Directory

Maven dependency:
```
<dependency>
   <groupId>org.vaadin.miki</groupId>
   <artifactId>super-template</artifactId>
   <version>0.2.2</version>
</dependency>
```

Maven repository:
```
<repository>
   <id>vaadin-addons</id>
   <url>https://maven.vaadin.com/vaadin-addons</url>
</repository>
```

### From GitHub packages

Follow instructions: https://github.com/vaadin-miki/super-template/packages

## Using

### Manual

Simply extend your custom template Java class from `SuperTemplate` instead of `PolymerTemplate`.

### Vaadin Designer

This only works with Vaadin Designer version 4.4.2 and above (released 25th of March, 2020).

Locate your `.vaadin/designer/project-settings.json` file. Add the following property:

```
  "--design.companion.basetype": "org.vaadin.miki.supertemplate.SuperTemplate<{{ClassName}}.{{ClassName}}Model>"
```

More information: https://github.com/vaadin/designer/issues/2257

## How it works

The constructor of `SuperTemplate` delegates the magic of initialising the fields to `TemplateProcessor` passed as a constructor argument. Unless specified, `SimpleTemplateProcessor` will be used.

Please consult the file `SuperTemplateTest` and corresponding `TestView` and `VaadinView` to get a glimpse of how things work.

### `SimpleTemplateProcessor`

In general, the processor receives a *template object* of type `PolymerTemplate`. Then:
* it reads the *template root* - the HTML that corresponds to the template - from the proper `.js` file;
* it goes through all fields annotated with `@Id` declared in the template class.

For each field:
* it fetches the *value* of the field (by using reflection and `setAccessible` to go around `private`) - the constructor of `PolymerTemplate` creates an empty, default object of the correct type;
* it locates the corresponding HTML element inside the template root;
* it goes through each attribute of that element;
* it applies any extra configuration (see the next section).

For each attribute:
* it locates a public setter with a matching name (for attribute `foo` it will be `setFoo`) and exactly one parameter of type `String`, found in the class of the *value* (so if the field is of type `TextField`, it will look in `TextField.class`)
* it calls that setter, passing the `String` found in the attribute.

## Default behaviour

Unless changed, after setting each field according to its attributes, the object will go through extra configuration. This is defined by `TemplateFieldConfigurator` and by default those are available:
* if the value implements `HasText`, its `setText` method will be called with the text found in the element;
* if the value is an `Icon`, its server-side `Icon` will be changed to reflect the one specified in the element;
* if the value is a `Button` and the element has an icon, the icon will be set on the server side;
* if the value is a `Grid` and it is declared in the file as `Grid<BeanType>`, the field will be configured as if `new Grid<>(BeanType.class)` was called; in addition columns can be added (see below).

The default configurators can be skipped by:
* passing a `false` flag to the `SuperTemplate` constructor - for that particular template only;
* calling `TemplateFieldConfigurators.DEFAULT_CONFIGURATORS.clear()` - for all templates (does not apply retroactively to templates that were created before executing that code).

### Adding columns to `Grid<MyBean>`

Under certain conditions columns can be specified in the design. First of all, the `Grid` must be typed in Java (so `@Id("id-of-grid") private Grid<MyBean> grid`). Then:
* (**recommended**) columns from `data-columns` attribute on `vaadin-grid`, if that attribute is present; the list should be space-separated list of properties, and for each property server-side object will use `addColumn(propertyName)`
* (**experimental**) columns from `vaadin-grid-column` are taken if they have an attribute `data-column`; again, server-side object will use `addcolumn(name)`

Feel free to file an issue if there are problems with either of the approaches.

## Extending default behaviour

### For all templates

Unless changed, `TemplateProcessor` will receive all `TemplateFieldConfigurator`s present in `TemplateFieldConfigurators.DEFAULT_CONFIGURATORS`. That list is modifiable, so whatever gets added or removed will affect all templates created afterwards.

### For current template class

The constructor of `SuperTemplate` accepts a varargs parameter of `TemplateFieldConfigurator`s. Those will be added in addition to the default ones, but only for that template.

## Contributing

Feel free to create issues or PRs for missing functionality.