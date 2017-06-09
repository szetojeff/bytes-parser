package com.szetojeff.bytesparser.datatype;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

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
public class ParsableTypeProviderTest {

    Byte value;

    @Test
    public void test() throws Exception
    {
        System.out.println("Test");
        /*
        ParsableTypeProvider provider = new ParsableTypeProvider();
        ParsableType type = provider.getParsableType(null,
                ParsableTypeProviderTest.class.getDeclaredField("value"), null);
        System.out.println( ((JavaDataType)type).getBitSize());
        */
        /*
        JavaDataType.JAVA_DATA_TYPES
                .getOrDefault(field.getType(), Arrays.asList())
                .stream().filter(t-> byteOrder==null || t.getByteOrder()==byteOrder).findFirst()
                .orElseThrow(()->
                        new UnsupportedOperationException("field[" + field.getName()
                                + "] with type[" + field.getType().getName() + "] is unsupported"));
        */
    }
}