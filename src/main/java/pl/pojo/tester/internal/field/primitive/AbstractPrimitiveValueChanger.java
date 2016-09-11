package pl.pojo.tester.internal.field.primitive;

import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import java.util.List;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

public abstract class AbstractPrimitiveValueChanger<T> extends AbstractFieldValueChanger<T> {

    public static final AbstractFieldValueChanger INSTANCE = new BooleanValueChanger().attachNext(new BooleanValueChanger())
                                                                                      .attachNext(new ByteValueChanger())
                                                                                      .attachNext(new CharacterValueChanger())
                                                                                      .attachNext(new DoubleValueChanger())
                                                                                      .attachNext(new IntegerValueChanger())
                                                                                      .attachNext(new LongValueChanger())
                                                                                      .attachNext(new ShortValueChanger())
                                                                                      .attachNext(new FloatValueChanger());

    private static final List<Class<?>> PRIMITIVE_CLASSES = Lists.newArrayList(Float.class,
                                                                               Integer.class,
                                                                               Long.class,
                                                                               Float.class,
                                                                               Double.class,
                                                                               Byte.class,
                                                                               Short.class,
                                                                               Boolean.class,
                                                                               Character.class);
    private static final String FIELD_WITH_PRIMITIVE_CLASS_REFERENCE = "TYPE";

    @Override
    public boolean areDifferentValues(final T sourceValue, final T targetValue) {
        if (sourceValue == targetValue) {
            return false;
        }
        if (sourceValue == null || targetValue == null) {
            return true;
        } else {
            return areDifferent(sourceValue, targetValue);
        }
    }


    protected abstract boolean areDifferent(T sourceValue, T targetValue);

    @Override
    protected boolean canChange(final Field field) {
        return isPrimitive(field) && isCompatibleWithPrimitive(field)
               || isWrappedPrimitive(field) && isCompatibleWithWrappedPrimitive(field);
    }

    @Override
    protected T increaseValue(final T value, final Class<?> type) {
        return increaseValue(value);
    }

    protected abstract T increaseValue(final T value);

    private boolean isPrimitive(final Field field) {
        return field.getType()
                    .isPrimitive();
    }

    private boolean isCompatibleWithPrimitive(final Field field) {
        try {
            return getGenericTypeClass().getField(FIELD_WITH_PRIMITIVE_CLASS_REFERENCE)
                                        .get(null)
                                        .equals(field.getType());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }

    private boolean isWrappedPrimitive(final Field field) {
        final Class<?> clazz = field.getType();
        return PRIMITIVE_CLASSES.contains(clazz);
    }

    private boolean isCompatibleWithWrappedPrimitive(final Field field) {
        try {
            final Object fieldPrimitiveType = field.getType()
                                                   .getField(FIELD_WITH_PRIMITIVE_CLASS_REFERENCE)
                                                   .get(null);
            return getGenericTypeClass().getField(FIELD_WITH_PRIMITIVE_CLASS_REFERENCE)
                                        .get(null)
                                        .equals(fieldPrimitiveType);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }
}
