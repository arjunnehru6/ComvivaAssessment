package com.accolite.assessment.gc;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class objRef {

    private Object object;
    private Set<objRef> references;

    public objRef(Object object) {
        this.object = object;
        references = new HashSet<>();
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Set<objRef> getReferences() {
        return references;
    }

    public void addReference(objRef reference) {
        this.references.add(reference);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        objRef reference = (objRef) o;
        return Objects.equals(object, reference.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object);
    }
}
