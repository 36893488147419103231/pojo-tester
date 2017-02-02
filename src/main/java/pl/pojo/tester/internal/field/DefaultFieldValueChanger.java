package pl.pojo.tester.internal.field;


import pl.pojo.tester.internal.field.collections.CollectionsFieldValueChanger;
import pl.pojo.tester.internal.field.math.BigDecimalValueChanger;
import pl.pojo.tester.internal.field.math.BigIntegerValueChanger;
import pl.pojo.tester.internal.field.primitive.AbstractPrimitiveValueChanger;


public final class DefaultFieldValueChanger {

    public static final AbstractFieldValueChanger INSTANCE = new EnumValueChanger()
            .attachNext(AbstractPrimitiveValueChanger.INSTANCE)
            .attachNext(CollectionsFieldValueChanger.INSTANCE)
            .attachNext(new StringValueChanger())
            .attachNext(new UUIDValueChanger())
            .attachNext(new BigDecimalValueChanger())
            .attachNext(new BigIntegerValueChanger());

    private DefaultFieldValueChanger() {
    }
}
