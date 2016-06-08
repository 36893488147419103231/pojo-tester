package org.pojo.tester;

import org.junit.Test;
import test.GoodPojo_Equals_HashCode_ToString;
import test.tostring.ToStringWithoutField;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.util.Lists.newArrayList;
import static org.pojo.tester.FieldPredicate.exclude;
import static org.pojo.tester.FieldPredicate.include;


public class ToStringTesterTest {

    @Test
    public void Should_Pass_All_ToString_Tests() {
        // given
        final Class[] classesToTest = {GoodPojo_Equals_HashCode_ToString.class};
        final ToStringTester hashCodeTester = new ToStringTester();

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.test(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_ToString_Tests_Excluding_Fields() {
        // given
        final ToStringTester hashCodeTester = new ToStringTester();
        final Class<?> clazz = ToStringWithoutField.class;
        final List<String> excludedFields = newArrayList("testEnum");

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.test(clazz, exclude(excludedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_ToString_Tests_Including_Fields() {
        // given
        final ToStringTester hashCodeTester = new ToStringTester();
        final Class<?> clazz = ToStringWithoutField.class;
        final List<String> includedFields = newArrayList("a", "b", "obj");

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.test(clazz, include(includedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Fail_All_ToString_Tests() {
        // given
        final Class[] classesToTest = {ToStringWithoutField.class};
        final ToStringTester hashCodeTester = new ToStringTester();

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.test(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Fail_All_ToString_Tests_Excluding_Fields() {
        // given
        final ToStringTester hashCodeTester = new ToStringTester();
        final Class<?> clazz = ToStringWithoutField.class;
        final List<String> excludedFields = newArrayList("a");

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.test(clazz, exclude(excludedFields)));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Fail_All_ToString_Tests_Including_Fields() {
        // given
        final ToStringTester hashCodeTester = new ToStringTester();
        final Class<?> clazz = ToStringWithoutField.class;
        final List<String> includedFields = newArrayList("a", "b");

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.test(clazz, include(includedFields)));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }


}
