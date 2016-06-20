package org.pojo.tester.field.primitive;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pojo.tester.field.AbstractFieldValueChanger;
import test.fields.AllFiledTypes;
import test.fields.AllFiledTypes_Wrapped;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitParamsRunner.class)
public class CharacterValueChangerTest {

    private final AbstractFieldValueChanger<Character> charValueChanger = new CharacterValueChanger();

    @Test
    @Parameters(method = "getValuesForTest")
    public void Should_Change_Primitive_Value(final Character value) {
        // given
        final AllFiledTypes helpClass1 = new AllFiledTypes(value);
        final AllFiledTypes helpClass2 = new AllFiledTypes(value);

        // when
        charValueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
        final Character result1 = getInternalState(helpClass1, "characterType");
        final Character result2 = getInternalState(helpClass2, "characterType");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    @Parameters(method = "getValuesForTest")
    public void Should_Change_Wrapped_Value(final Character value) {
        // given
        final AllFiledTypes_Wrapped helpClass1 = new AllFiledTypes_Wrapped(value);
        final AllFiledTypes_Wrapped helpClass2 = new AllFiledTypes_Wrapped(value);

        // when
        charValueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes_Wrapped.class.getDeclaredFields()));
        final Character result1 = getInternalState(helpClass1, "characterType");
        final Character result2 = getInternalState(helpClass2, "characterType");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    public void Should_Return_False_If_Values_Are_Not_Different() {
        // given
        final Character value1 = 0;
        final Character value2 = 0;

        // when
        final boolean result = charValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void Should_Return_True_If_Values_Are_Different() {
        // given
        final Character value1 = 0;
        final Character value2 = 1;

        // when
        final boolean result = charValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isTrue();
    }

    private Object[] getValuesForTest() {
        return new Object[]{Character.MAX_CODE_POINT,
                            Character.MAX_HIGH_SURROGATE,
                            Character.MAX_LOW_SURROGATE,
                            Character.MAX_RADIX,
                            Character.MAX_SURROGATE,
                            Character.MAX_VALUE,
                            Character.MIN_CODE_POINT,
                            Character.MIN_HIGH_SURROGATE,
                            Character.MIN_LOW_SURROGATE,
                            Character.MIN_RADIX,
                            Character.MIN_SURROGATE,
                            Character.MIN_VALUE};
    }
}
