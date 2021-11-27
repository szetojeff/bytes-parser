package com.szetojeff.bytesparser.datatype;

import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * @author Jeff
 */
public class ParsableTypeProvider {

    public ParsableType getParsableType(Class<?> clazz, Field field, ByteOrder byteOrder) {
        // TODO: only support Java data type for now
        return JavaDataType.JAVA_DATA_TYPES
                .getOrDefault(field.getType(), Arrays.asList())
                .stream().filter(t-> t.getByteOrder()==null || t.getByteOrder()==byteOrder).findFirst()
                .orElseThrow(()->
                    new UnsupportedOperationException("field[" + field.getName()
                        + "] with type[" + field.getType().getName() + "] is unsupported"));
    }

}
