package org.pojo.tester.assertion;

import org.junit.Test;
import test.GoodPojo_Equals_HashCode_ToString;
import test.hashcode.BadPojoHashCode_DifferentObjects;
import test.hashcode.BadPojoHashCode_NotConsistent;
import test.hashcode.BadPojoHashCode_SameObjects;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


public class HashCodeAssertionsTest {

    @Test
    public void shouldFail_WhenHashCodeMethodIsNotConsistent() {
        // given
        final BadPojoHashCode_NotConsistent objectUnderAssert = new BadPojoHashCode_NotConsistent();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final HashCodeAssertions hashCodeAssertions = new HashCodeAssertions(resultBuilder, objectUnderAssert);

        // when
        hashCodeAssertions.isConsistent();

        // then
        verify(resultBuilder).fail(eq(BadPojoHashCode_NotConsistent.class), anyString(), anyString());
    }

    @Test
    public void shouldFail_WhenHashCodeMethodReturnsDifferentHashCodeForSameObjects() {
        // given
        final BadPojoHashCode_SameObjects objectUnderAssert = new BadPojoHashCode_SameObjects();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final HashCodeAssertions hashCodeAssertions = new HashCodeAssertions(resultBuilder, objectUnderAssert);

        // when
        hashCodeAssertions.returnsSameValueFor(objectUnderAssert);

        // then
        verify(resultBuilder).fail(eq(BadPojoHashCode_SameObjects.class), anyString(), anyString());
    }

    @Test
    public void shouldFail_WhenHashCodeMethodReturnsSameHashCodeForDifferentObjects() {
        // given
        final BadPojoHashCode_DifferentObjects objectUnderAssert1 = new BadPojoHashCode_DifferentObjects(1);
        final BadPojoHashCode_DifferentObjects objectUnderAssert2 = new BadPojoHashCode_DifferentObjects(2);
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final HashCodeAssertions hashCodeAssertions = new HashCodeAssertions(resultBuilder, objectUnderAssert1);

        // when
        hashCodeAssertions.returnsDifferentValueFor(objectUnderAssert2);

        // then
        verify(resultBuilder).fail(eq(BadPojoHashCode_DifferentObjects.class), anyString(), anyString());
    }

    @Test
    public void shouldPass_WhenHashCodeMethodReturnsDifferentHashCodeForDifferentObjects() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert1 = new GoodPojo_Equals_HashCode_ToString();
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert2 = new GoodPojo_Equals_HashCode_ToString();
        objectUnderAssert2.booleanType = true;
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final HashCodeAssertions hashCodeAssertions = new HashCodeAssertions(resultBuilder, objectUnderAssert1);

        // when
        hashCodeAssertions.returnsDifferentValueFor(objectUnderAssert2);

        // then
        verify(resultBuilder).pass(eq(GoodPojo_Equals_HashCode_ToString.class), anyString());
    }

    @Test
    public void shouldPass_WhenHashCodeMethodReturnsSameHashCodeForSameObjects() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final HashCodeAssertions hashCodeAssertions = new HashCodeAssertions(resultBuilder, objectUnderAssert);

        // when
        hashCodeAssertions.returnsSameValueFor(objectUnderAssert);

        // then
        verify(resultBuilder).pass(eq(GoodPojo_Equals_HashCode_ToString.class), anyString());
    }

    @Test
    public void shouldPass_WhenHashCodeMethodIsConsistent() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final HashCodeAssertions hashCodeAssertions = new HashCodeAssertions(resultBuilder, objectUnderAssert);

        // when
        hashCodeAssertions.isConsistent();

        // then
        verify(resultBuilder).pass(eq(GoodPojo_Equals_HashCode_ToString.class), anyString());
    }

}
