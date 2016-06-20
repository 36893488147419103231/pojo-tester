package org.pojo.tester.field;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.internal.WhiteboxImpl.getInternalState;

@RunWith(MockitoJUnitRunner.class)
public class AbstractFieldValueChangerTest {

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private static AbstractFieldValueChanger abstractFieldValueChanger;

    @Test
    public void Should_Register_First_Value_Changer() {
        // given

        // when
        abstractFieldValueChanger.attachNext(abstractFieldValueChanger);
        final AbstractFieldValueChanger result = getInternalState(abstractFieldValueChanger, "next");

        // then
        assertThat(result).isNotNull();
    }

    @Test
    public void Should_Register_Value_Changer_To_Already_Registered_One() {
        // given
        final AbstractFieldValueChanger first = mock(AbstractFieldValueChanger.class, Mockito.CALLS_REAL_METHODS);
        final AbstractFieldValueChanger second = mock(AbstractFieldValueChanger.class, Mockito.CALLS_REAL_METHODS);

        // when
        abstractFieldValueChanger.attachNext(first)
                                 .attachNext(second);

        // then
        verify(first).attachNext(second);
    }

}
