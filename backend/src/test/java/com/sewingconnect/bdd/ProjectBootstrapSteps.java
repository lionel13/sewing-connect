package com.sewingconnect.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectBootstrapSteps {

    private final ApplicationContext applicationContext;

    ProjectBootstrapSteps(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Given("the Spring application context is available")
    void theSpringApplicationContextIsAvailable() {
        assertThat(applicationContext).isNotNull();
    }

    @Then("the project bootstrap succeeds")
    void theProjectBootstrapSucceeds() {
        assertThat(applicationContext.getEnvironment().getProperty("spring.application.name"))
            .isEqualTo("sewing-connect");
    }
}
