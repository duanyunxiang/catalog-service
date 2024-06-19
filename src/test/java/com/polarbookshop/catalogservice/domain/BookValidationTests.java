package com.polarbookshop.catalogservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * BookValidation单元测试
 */
public class BookValidationTests {
    private static Validator validator;

    @BeforeAll
    public static void setUp(){
        ValidatorFactory factory= Validation.buildDefaultValidatorFactory();
        validator=factory.getValidator();
    }

    //直接运行报错时，设置：File -> Settings -> 检索Gradle -> Run tests using 改为 Intellij IDEA
    @Test
    public void whenAllFieldsCorrectThenValidationSucceeds(){
        var book=Book.of("1234567890","Title","Author",9.90);
        Set<ConstraintViolation<Book>> violations=validator.validate(book);
        //断言没有error
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    public void whenIsBnDefinedButIncorrectThenValidationFails(){
        var book=Book.of("a234567890","Title","Author",9.90);
        Set<ConstraintViolation<Book>> violations=validator.validate(book);
        //断言有1个error
        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(violations.iterator().next().getMessage()).isEqualTo("The ISBN format must be valid.");
    }
}
