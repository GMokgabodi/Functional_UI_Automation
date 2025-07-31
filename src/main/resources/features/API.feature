@api
Feature: Todo API

  Scenario: Login API with valid credentials
    Given the backend is running
    When I send a POST to /login with username "admin" and password "admin"
    Then the response status code should be 200

  Scenario: Create a todo via API
    Given the backend is running
    When I send a POST to /todos with text "API task"
    Then the response status code should be 200

  Scenario: Get todos via API
    Given the backend is running
    When I send a GET request to /todos
    Then the response should contain "API task"
