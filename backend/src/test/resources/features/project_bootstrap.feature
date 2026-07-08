Feature: Project bootstrap

  Scenario: Start the backend test context
    Given the Spring application context is available
    Then the project bootstrap succeeds
