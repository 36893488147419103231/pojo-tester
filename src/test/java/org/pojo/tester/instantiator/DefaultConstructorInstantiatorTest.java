package org.pojo.tester.instantiator;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultConstructorInstantiatorTest {

    @Test
    public void Should_Create_Object_Using_Default_Constructor() {
        // given
        final Class<String> classToInstantiate = String.class;
        final DefaultConstructorInstantiator instantiator = new DefaultConstructorInstantiator(classToInstantiate);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isInstanceOf(classToInstantiate);
    }

    @Test
    public void Should_Create_Object_Using_Default_Constructor_By_Qualified_Class_Name() {
        // given
        final Class<String> classToInstantiate = String.class;
        final DefaultConstructorInstantiator instantiator = new DefaultConstructorInstantiator("java.lang.String");

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isInstanceOf(classToInstantiate);
    }
}
