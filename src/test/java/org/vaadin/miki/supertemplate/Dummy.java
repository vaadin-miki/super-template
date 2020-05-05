package org.vaadin.miki.supertemplate;

import java.util.Objects;

/**
 * A dummy data type.
 */
public class Dummy {

    private String contents;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dummy dummy = (Dummy) o;
        return Objects.equals(getContents(), dummy.getContents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContents());
    }
}
