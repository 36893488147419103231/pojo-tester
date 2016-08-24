package org.pojo.tester.instantiator;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.pojo.tester.ClassAndFieldPredicatePair;
import org.pojo.tester.field.AbstractFieldValueChanger;
import org.pojo.tester.field.DefaultFieldValueChanger;
import test.GoodPojo_Equals_HashCode_ToString;
import test.TestHelper;
import test.fields.collections.collection.Collections;
import test.fields.collections.map.Maps;
import test.instantiator.arrays.ObjectContainingArray;
import test.instantiator.arrays.ObjectContainingIterable;
import test.instantiator.arrays.ObjectContainingIterator;
import test.instantiator.arrays.ObjectContainingStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static test.TestHelper.getDefaultDisplayName;

@RunWith(JUnitPlatform.class)
public class ObjectGeneratorTest {

    private final AbstractFieldValueChanger abstractFieldValueChanger = DefaultFieldValueChanger.INSTANCE;

    @Test
    public void Should_Create_Any_Instance() {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger);
        final Class<GoodPojo_Equals_HashCode_ToString> expectedClass = GoodPojo_Equals_HashCode_ToString.class;

        // when
        final Object result = objectGenerator.createNewInstance(expectedClass);

        // then
        assertThat(result).isInstanceOf(expectedClass);
    }

    @TestFactory
    public Stream<DynamicTest> Should_Create_Same_Instance() {
        return Stream.of(new GoodPojo_Equals_HashCode_ToString(),
                         new ObjectContainingArray(),
                         new Collections(),
                         new Maps())
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Create_Same_Instance(value)));
    }

    public Executable Should_Create_Same_Instance(final Object objectToCreateSameInstance) {
        return () -> {
            // given
            final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger);

            // when
            final Object result = objectGenerator.generateSameInstance(objectToCreateSameInstance);

            // then
            assertThat(result).isEqualToComparingFieldByField(objectToCreateSameInstance);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Create_Different_Instance() {
        return Stream.of(new ObjectContainingArray(),
                         new ObjectContainingIterable(),
                         new ObjectContainingIterator(),
                         new ObjectContainingStream(),
                         new Collections(),
                         new Maps(),
                         new GoodPojo_Equals_HashCode_ToString())
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Create_Different_Instance(value)));
    }

    public Executable Should_Create_Different_Instance(final Object objectToCreateSameInstance) {
        return () -> {
            // given
            final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger);
            final List<Field> allFields = TestHelper.getAllFieldsExceptDummyJacocoField(objectToCreateSameInstance.getClass());

            // when
            final Object result = objectGenerator.generateInstanceWithDifferentFieldValues(objectToCreateSameInstance, allFields);

            // then
            assertThat(result).isNotEqualTo(objectToCreateSameInstance);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Generate_Different_Objects() {
        return Stream.of(new DifferentObjectTestCase(A.class, 4),
                         new DifferentObjectTestCase(B.class, 8),
                         new DifferentObjectTestCase(C.class, 16))
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Generate_Different_Objects(value)));
    }
    
    public Executable Should_Generate_Different_Objects(final DifferentObjectTestCase testCase) {
        return () -> {
            // given
            final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger);
            final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(testCase.clazz);

            // when
            final List<Object> result = objectGenerator.generateDifferentObjects(classAndFieldPredicatePair);

            // then
            assertThat(result).hasSize(testCase.expectedSize)
                              .doesNotHaveDuplicates();
        };
    }

    @Data
    class A {
        int a;
        int b;
    }

    @Data
    class B {
        int a;
        int b;
        int c;
    }

    @Data
    class C {
        int a;
        int b;
        int c;
        int d;
    }

    @Data
    @AllArgsConstructor
    class DifferentObjectTestCase {
        private Class<?> clazz;
        private int expectedSize;
    }
}
