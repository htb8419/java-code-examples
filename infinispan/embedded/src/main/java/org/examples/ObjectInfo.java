package org.examples;

import java.util.Objects;

public class ObjectInfo {
    final String key;
    final String className;
    final int hashCode;

    public ObjectInfo(Object object) {
        this.className = object.getClass().getSimpleName();
        this.hashCode = object.hashCode();
        this.key = className + hashCode;
    }

    public String getKey() {
        return key;
    }

    public String getClassName() {
        return className;
    }

    public int getHashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "ObjectInfo{" +
                "key='" + key + '\'' +
                ", className='" + className + '\'' +
                ", hashCode=" + hashCode +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ObjectInfo that = (ObjectInfo) object;
        return hashCode == that.hashCode && Objects.equals(key, that.key) && Objects.equals(className, that.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, className, hashCode);
    }
}
