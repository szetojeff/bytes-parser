package com.szetojeff.bytesparser;

import com.szetojeff.bytesparser.annotation.ElementField;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author Jeff
 */
public class BytesParserTest {

    public static final int DELTA = 00001;
    double doubleVal = 231212.121d;
    float floatVal = 11.295f;
    long longVal = 29129381L;
    int intVal = 123940;
    short shortVal = 382;
    char charVal = 'z';
    byte byteVal = 48;

    @Test
    public void parse_should_parseBytesInLittleEndian() throws Exception {
        parse_should_returnTypedObject(ByteOrder.LITTLE_ENDIAN);
    }

    @Test
    public void parse_should_parseBytesInBigEndian() throws Exception {
        parse_should_returnTypedObject(ByteOrder.BIG_ENDIAN);
    }

    private void parse_should_returnTypedObject(ByteOrder byteOrder) throws Exception {
        byte[] bytes = new byte[
                8 + 8 + // double
                4 + 4 + // float
                8 + 8 + // long
                4 + 4 + // int
                2 + 2 + // short
                2 + 2 +  // char
                1 + 1   // byte
                ];
        ByteBuffer.wrap(bytes).order(byteOrder)
                .putDouble(doubleVal).putDouble(doubleVal)
                .putFloat(floatVal).putFloat(floatVal)
                .putLong(longVal).putLong(longVal)
                .putInt(intVal).putInt(intVal)
                .putShort(shortVal).putShort(shortVal)
                .putChar(charVal).putChar(charVal)
                .put(byteVal).put(byteVal)
                ;

        BytesParser parser = new BytesParser(byteOrder);
        JavaDataTypeTest testObj = parser.parse(bytes, JavaDataTypeTest.class);

        Assert.assertEquals(testObj.doubleVal, doubleVal, DELTA);
        Assert.assertEquals(testObj.bigDoubleVal, doubleVal, DELTA);
        Assert.assertEquals(testObj.floatVal, floatVal, DELTA);
        Assert.assertEquals(testObj.bigFloatVal, floatVal, DELTA);

        Assert.assertEquals(testObj.longVal, longVal);
        Assert.assertEquals(testObj.bigLongVal.longValue(), longVal);
        Assert.assertEquals(testObj.intVal, intVal);
        Assert.assertEquals(testObj.bigIntVal.intValue(), intVal);
        Assert.assertEquals(testObj.shortVal, shortVal);
        Assert.assertEquals(testObj.bigShortVal.shortValue(), shortVal);

        Assert.assertEquals(testObj.charVal, charVal);
        Assert.assertEquals(testObj.bigCharVal.charValue(), charVal);

        Assert.assertEquals(testObj.byteVal, byteVal);
        Assert.assertEquals(testObj.bigByteVal.byteValue(), byteVal);
    }

    public static class JavaDataTypeTest
    {
        @ElementField(index=0)
        double doubleVal;
        @ElementField(index=1)
        Double bigDoubleVal;

        @ElementField(index=2)
        float floatVal;
        @ElementField(index=3)
        Float bigFloatVal;

        @ElementField(index=4)
        long longVal;
        @ElementField(index=5)
        Long bigLongVal;

        @ElementField(index=6)
        int intVal;
        @ElementField(index=7)
        Integer bigIntVal;

        @ElementField(index=8)
        short shortVal;
        @ElementField(index=9)
        Short bigShortVal;

        @ElementField(index=10)
        char charVal;
        @ElementField(index=11)
        Character bigCharVal;

        @ElementField(index=12)
        byte byteVal;
        @ElementField(index=13)
        Byte bigByteVal;
    }
}