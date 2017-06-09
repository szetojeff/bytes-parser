package com.szetojeff.bytesparser.datatype;

import java.lang.reflect.Field;

/**
 * @author Jeff
 */
public interface ParsableType {

    ParsedResult parse(Field field, byte[] bytes, int beginPos);
}
