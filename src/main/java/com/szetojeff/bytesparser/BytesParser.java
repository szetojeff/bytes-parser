package com.szetojeff.bytesparser;

import com.szetojeff.bytesparser.annotation.ElementField;
import com.szetojeff.bytesparser.datatype.ParsableType;
import com.szetojeff.bytesparser.datatype.ParsableTypeProvider;
import com.szetojeff.bytesparser.datatype.ParsedResult;

import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A parser knows how to parse bytes into a specific type
 * @author Jeff Szeto
 */
public class BytesParser {
    private static final Logger LOG = Logger.getLogger(BytesParser.class.getName());

    ParsableTypeProvider typeProvider = new ParsableTypeProvider();
    // TODO - make this byte order to be part of class or field level attribute
    private ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;

    public BytesParser(ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
    }

    public <T> T parse(byte[] bytes, Class<T> type) {
        List<Field> sortedField = getParsableFieldsSortedByIndex(type);
        if (sortedField.isEmpty()) {
            throw new IllegalArgumentException("No field annotated with ElementField is found in ["
                + type.getName() + "]");
        }

        T object = newInstance(type);

        int beginPos = 0;
        for (Field field : sortedField){
            if (beginPos >= bytes.length) {
                LOG.warning("Ran out of bytes to parse, total byte size=["
                        + bytes.length + " but trying to parse field[" + field + "]");
                break;
            }

            ParsedResult result = parseFieldValue(type, bytes, field, beginPos);
            setFieldValue(object, field, result.getValue());
            beginPos += result.getBytesUsed();
            // TODO - need to support bit used
        }
        return object;
    }

    private ParsedResult parseFieldValue(Class<?> type, byte[] bytes, Field field, int beginPos) {
        ParsableType pdt = typeProvider.getParsableType(type, field, byteOrder);
        return pdt.parse(field, bytes, beginPos);
    }

    private static List<Field> getParsableFieldsSortedByIndex(Class<?> type) {
        return Stream.of(type.getDeclaredFields())
                .filter(f->f.getDeclaredAnnotation(ElementField.class)!=null)
                .sorted((f1, f2)-> {
                        int i1 = f1.getDeclaredAnnotation(ElementField.class).index();
                        int i2 = f2.getDeclaredAnnotation(ElementField.class).index();
                        if (f1 != f2 && i1 == i2) {
                            throw new IllegalArgumentException("Fields cannot have the same index:["
                                + f1.getName() + ", " + f2.getName() + "]");
                        }
                        return Integer.compare(i1, i2);
                    }).collect(Collectors.toList());
    }

    private static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Fail to instantiate " + clazz.getName()
                    + ", an empty constructor is required.", e);
        }
    }

    private static void setFieldValue(Object object, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Fail to set field value[" + field.getName()
                    + "] to [" + object + "]", e);
        }
    }
}
