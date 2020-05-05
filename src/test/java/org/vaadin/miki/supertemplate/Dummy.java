package org.vaadin.miki.supertemplate;

import java.util.Objects;

/**
 * A dummy data type.
 */
public class Dummy {

    private String contents;

    private String description;

    private boolean flagged;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dummy dummy = (Dummy) o;
        return isFlagged() == dummy.isFlagged() &&
                Objects.equals(getContents(), dummy.getContents()) &&
                Objects.equals(getDescription(), dummy.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContents(), getDescription(), isFlagged());
    }
}
