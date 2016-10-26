package de.seven.fate.bpmn.enums;

import static java.util.Optional.ofNullable;

public enum ExtensionPropertyType {

    URI, ID;

    private final String propertyName;

    ExtensionPropertyType() {
        this(null);
    }

    ExtensionPropertyType(String propertyName) {
        this.propertyName = ofNullable(propertyName).orElse(this.name().toLowerCase());
    }

    public String getName() {
        return propertyName;
    }
}
