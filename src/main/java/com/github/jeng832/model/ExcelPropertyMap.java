package com.github.jeng832.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ExcelPropertyMap {

    private final Map<Field, CellValueAndSetter> map;

    public ExcelPropertyMap() {
        this.map = new HashMap<>();
    }

    public void add(Field field, String value, Method setter) {
        this.map.put(field, new CellValueAndSetter(value, setter));
    }

    public Optional<String> getValue(Field field) {
        if (!this.map.containsKey(field)) return Optional.empty();
        return Optional.ofNullable(this.map.get(field).value);
    }

    public Optional<Method> getSetter(Field field) {
        if (!this.map.containsKey(field)) return Optional.empty();
        return Optional.ofNullable(this.map.get(field).setter);
    }

    public Set<Field> getFields() {
        return this.map.keySet();
    }

    public boolean hasSetter(Field field) {
        return this.map.containsKey(field) && this.map.get(field).setter != null;
    }
    private static class CellValueAndSetter {
        String value;
        Method setter;

        CellValueAndSetter(String value, Method setter) {
            this.value = value;
            this.setter = setter;
        }
    }
}
