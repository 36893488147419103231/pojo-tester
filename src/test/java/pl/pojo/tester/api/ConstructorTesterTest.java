package pl.pojo.tester.api;

import classesForTest.ClassWithSyntheticConstructor;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.internal.assertion.constructor.ConstructorAssertionError;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;
import pl.pojo.tester.internal.field.collections.CollectionsFieldValueChanger;
import pl.pojo.tester.internal.instantiator.ClassLoader;
import pl.pojo.tester.internal.instantiator.Instantiable;
import pl.pojo.tester.internal.preconditions.ParameterPreconditions;
import pl.pojo.tester.internal.tester.ConstructorTester;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@RunWith(JUnitPlatform.class)
public class ConstructorTesterTest {

    @Test
    public void Should_Pass_All_Constructor_Tests() {
        // given
        final Class[] classesToTest = {Pojo.class,
                                       ParameterPreconditions.class,
                                       CollectionsFieldValueChanger.class,
                                       DefaultFieldValueChanger.class,
                                       Assertions.class,
                                       Instantiable.class,
                                       ClassLoader.class};
        final ConstructorTester constructorTester = new ConstructorTester(DefaultFieldValueChanger.INSTANCE);

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Fail_Constructor_Tests() {
        // given
        final Class[] classesToTest = {BadConstructorPojo.class};
        final ConstructorTester constructorTester = new ConstructorTester();

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(ConstructorAssertionError.class);
    }

    @Test
    public void Should_Use_User_Constructor_Parameters() {
        // given
        final Class[] classesToTest = {ClassWithSyntheticConstructor.class};

        final ConstructorParameters parameters = new ConstructorParameters(new Object[]{"string"},
                                                                           new Class[]{String.class});
        final HashMap<Class<?>, ConstructorParameters> constructorParameters = mock(HashMap.class);
        when(constructorParameters.get(ClassWithSyntheticConstructor.class)).thenReturn(parameters);
        when(constructorParameters.containsKey(ClassWithSyntheticConstructor.class)).thenReturn(true);

        final ConstructorTester constructorTester = new ConstructorTester();
        constructorTester.setUserDefinedConstructors(constructorParameters);

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
        verify(constructorParameters).get(ClassWithSyntheticConstructor.class);
    }

    private static class Pojo {
        public Pojo() {
        }

        public Pojo(final String x) {
        }

        public Pojo(final int y) {
        }
    }

    private static class BadConstructorPojo {
        public BadConstructorPojo() {
            throw new RuntimeException("test");
        }
    }
}