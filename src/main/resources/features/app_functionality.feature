Feature: App functionality

  Scenario Outline: Login with different credentials
    Given I open the app
    When I login with username "<username>" and password "<password>"
    Then I should <result>

    Examples:
      | username | password | result                      |
      | wrong    | wrong    | see an error message        |
      | admin    | admin    | see the todo list           |

  Scenario: Login with valid credentials
    Given I open the app
    When I login with username "admin" and password "admin"
    Then I should see the todo list

  Scenario: Add a new todo item
    Given I am logged in
    When I add a todo with text "My first task"
    Then I should see "My first task" in the list

  Scenario: Edit a todo item
    Given I am logged in
    And a todo "My first task" exists
    When I edit the todo "My first task" to "Updated task"
    Then I should see "Updated task" in the list

  Scenario: Delete a todo item
    Given I am logged in
    And a todo "Updated task" exists
    When I delete the todo "Updated task"
    Then I should not see "Updated task" in the list