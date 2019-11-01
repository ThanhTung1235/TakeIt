package com.takeIt.util;

import java.lang.reflect.Field;

public class ObjectUtil {
    // clone attribute from object2 to object1.
    public static void cloneObject(Object object1, Object object2) {
        for (Field fieldObject1 :
                object1.getClass().getDeclaredFields()) {
            try {
                fieldObject1.setAccessible(true);
                Field fieldObject2 = object2.getClass().getDeclaredField(fieldObject1.getName());
                fieldObject2.setAccessible(true);
                Object fieldValue = fieldObject2.get(object2);
                if (fieldValue != null) {
                    fieldObject1.set(object1, fieldValue);
                }
            } catch (Exception ex) {
                continue;
            }
        }
    }
}
