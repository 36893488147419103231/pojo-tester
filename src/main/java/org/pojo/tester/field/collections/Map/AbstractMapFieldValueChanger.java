package org.pojo.tester.field.collections.map;


import org.pojo.tester.field.AbstractFieldValueChanger;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

public abstract class AbstractMapFieldValueChanger<T extends Map> extends AbstractFieldValueChanger<T> {

    public static final AbstractFieldValueChanger INSTANCE = new HashMapValueChanger().register(new HashtableValueChanger())
                                                                                      .register(new LinkedHashMapValueChanger())
                                                                                      .register(new MapValueChanger())
                                                                                      .register(new SortedMapValueChanger())
                                                                                      .register(new TreeMapValueChanger());

    @Override
    public boolean areDifferentValues(final T sourceValue, final T targetValue) {
        if (sourceValue == targetValue) {
            return false;
        }
        if (sourceValue == null || targetValue == null || haveDifferentSizes(sourceValue, targetValue)) {
            return true;
        } else {
            targetValue.forEach(sourceValue::remove);
            return sourceValue.size() != 0;
        }
    }

    @Override
    protected boolean canChange(final Field field) {
        return field.getType()
                    .isAssignableFrom(getGenericTypeClass2());
    }
    
    protected Class<T> getGenericTypeClass2() {
        return (Class<T>) ((ParameterizedTypeImpl) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0])
                .getRawType();
    }

    private boolean haveDifferentSizes(final T sourceValue, final T targetValue) {
        return sourceValue.size() != targetValue.size();
    }
}
