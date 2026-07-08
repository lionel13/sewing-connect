package com.sewingconnect.domain.model;

import com.sewingconnect.domain.DomainException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValueObjectTest {

    @Test
    void normalizesEmailAddress() {
        EmailAddress emailAddress = new EmailAddress("  Teacher@Sewing-Connect.test  ");

        assertThat(emailAddress.value()).isEqualTo("teacher@sewing-connect.test");
    }

    @Test
    void rejectsInvalidEmailAddress() {
        assertThatThrownBy(() -> new EmailAddress("not-an-email"))
            .isInstanceOf(DomainException.class)
            .hasMessage("Email address must contain a local part and a domain.");
    }

    @Test
    void trimsPersonName() {
        PersonName name = new PersonName("  Alice Martin  ");

        assertThat(name.value()).isEqualTo("Alice Martin");
    }
}
