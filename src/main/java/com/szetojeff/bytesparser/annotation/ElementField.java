package com.szetojeff.bytesparser.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Represents an parsable element field
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementField {
    int index();
}
