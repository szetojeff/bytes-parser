package com.szetojeff.bytesparser.datatype;

import static java.nio.ByteOrder.*;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

/**
 * <pre>
 * Copyright ${year}
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </pre>
 *
 * @author Jeff Szeto
 */
public abstract class JavaDataType implements ParsableType {

    public static final JavaDataType DOUBLE_LITTLE_ENDIAN = new JavaDataType(64, LITTLE_ENDIAN) {
        @Override public Object parseValue(ByteBuffer byteBuffer) {
            return byteBuffer.getDouble();
        }
    };
    public static final JavaDataType DOUBLE_BIG_ENDIAN = new JavaDataType(64, BIG_ENDIAN) {
        @Override public Object parseValue(ByteBuffer byteBuffer) {
            return byteBuffer.getDouble();
        }
    };
    public static final JavaDataType FLOAT_LITTLE_ENDIAN = new JavaDataType(32, LITTLE_ENDIAN) {
        @Override public Object parseValue(ByteBuffer byteBuffer) {
            return byteBuffer.getFloat();
        }
    };
    public static final JavaDataType FLOAT_BIG_ENDIAN = new JavaDataType(32, BIG_ENDIAN) {
        @Override public Object parseValue(ByteBuffer byteBuffer) {
            return byteBuffer.getFloat();
        }
    };
    public static final JavaDataType LONG_LITTLE_ENDIAN = new JavaDataType(64, LITTLE_ENDIAN) {
        @Override public Object parseValue(ByteBuffer byteBuffer) {
            return byteBuffer.getLong();
        }
    };
    public static final JavaDataType LONG_BIG_ENDIAN = new JavaDataType(64, BIG_ENDIAN) {
        @Override public Object parseValue(ByteBuffer byteBuffer) {
            return byteBuffer.getLong();
        }
    };
    public static final JavaDataType INT_LITTLE_ENDIAN = new JavaDataType(32, LITTLE_ENDIAN) {
        @Override public Object parseValue(ByteBuffer byteBuffer) {
            return byteBuffer.getInt();
        }
    };
    public static final JavaDataType INT_BIG_ENDIAN = new JavaDataType(32, BIG_ENDIAN) {
        @Override public Object parseValue(ByteBuffer byteBuffer) {
            return byteBuffer.getInt();
        }
    };
    public static final JavaDataType SHORT_LITTLE_ENDIAN = new JavaDataType(16, LITTLE_ENDIAN) {
        @Override public Object parseValue(ByteBuffer byteBuffer) {
            return byteBuffer.getShort();
        }
    };
    public static final JavaDataType SHORT_BIG_ENDIAN = new JavaDataType(16, BIG_ENDIAN) {
        @Override public Object parseValue(ByteBuffer byteBuffer) {
            return byteBuffer.getShort();
        }
    };
    public static final JavaDataType CHAR_BIG_ENDIAN = new JavaDataType(16, BIG_ENDIAN) {
        @Override public Object parseValue(ByteBuffer byteBuffer) {
            return byteBuffer.getChar();
        }
    };
    public static final JavaDataType CHAR_LITTLE_ENDIAN = new JavaDataType(16, LITTLE_ENDIAN) {
        @Override public Object parseValue(ByteBuffer byteBuffer) {
            return byteBuffer.getChar();
        }
    };
    public static final JavaDataType BYTE = new JavaDataType(8, null) {
        @Override public Object parseValue(ByteBuffer byteBuffer) { return byteBuffer.get(); }
    };

    public static Map<Class<?>, List<JavaDataType>> JAVA_DATA_TYPES = new HashMap<>();
    static
    {
        JAVA_DATA_TYPES.put(Double.class, Arrays.asList(DOUBLE_LITTLE_ENDIAN, DOUBLE_BIG_ENDIAN));
        JAVA_DATA_TYPES.put(Double.TYPE, Arrays.asList(DOUBLE_LITTLE_ENDIAN, DOUBLE_BIG_ENDIAN));
        JAVA_DATA_TYPES.put(Float.class, Arrays.asList(FLOAT_LITTLE_ENDIAN, FLOAT_BIG_ENDIAN));
        JAVA_DATA_TYPES.put(Float.TYPE, Arrays.asList(FLOAT_LITTLE_ENDIAN, FLOAT_BIG_ENDIAN));
        JAVA_DATA_TYPES.put(Long.class, Arrays.asList(LONG_LITTLE_ENDIAN, LONG_BIG_ENDIAN));
        JAVA_DATA_TYPES.put(Long.TYPE, Arrays.asList(LONG_LITTLE_ENDIAN, LONG_BIG_ENDIAN));
        JAVA_DATA_TYPES.put(Integer.class, Arrays.asList(INT_LITTLE_ENDIAN, INT_BIG_ENDIAN));
        JAVA_DATA_TYPES.put(Integer.TYPE, Arrays.asList(INT_LITTLE_ENDIAN, INT_BIG_ENDIAN));
        JAVA_DATA_TYPES.put(Short.class, Arrays.asList(SHORT_LITTLE_ENDIAN, SHORT_BIG_ENDIAN));
        JAVA_DATA_TYPES.put(Short.TYPE, Arrays.asList(SHORT_LITTLE_ENDIAN, SHORT_BIG_ENDIAN));
        JAVA_DATA_TYPES.put(Character.TYPE, Arrays.asList(CHAR_LITTLE_ENDIAN, CHAR_BIG_ENDIAN));
        JAVA_DATA_TYPES.put(Character.class, Arrays.asList(CHAR_LITTLE_ENDIAN, CHAR_BIG_ENDIAN));
        JAVA_DATA_TYPES.put(Byte.TYPE, Arrays.asList(BYTE));
        JAVA_DATA_TYPES.put(Byte.class, Arrays.asList(BYTE));
    }

    private final int bitSize;
    private final ByteOrder byteOrder;

    public JavaDataType(int bitSize, ByteOrder byteOrder) {
        this.bitSize = bitSize;
        this.byteOrder = byteOrder;
    }

    public int getBitSize() {
        return bitSize;
    }

    public ByteOrder getByteOrder() {
        return byteOrder;
    }

    public abstract Object parseValue(ByteBuffer byteBuffer);

    @Override
    public ParsedResult parse(Field field, byte[] bytes, int beginPos) {
        // TODO: support data type that takes less than 1 byte
        ByteBuffer bb = ByteBuffer.wrap(bytes, beginPos, toBytes(bitSize));
        if (byteOrder != null)
        {
            bb.order(byteOrder);
        }

        Object val = parseValue(bb);
        return new ParsedResult(val, bitSize);
    }

    public static int toBytes(int bits) {
        return bits/8;
    }
}
