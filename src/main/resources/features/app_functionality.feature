Feature: App functionality

  Scenario Outline: Login with invalid credentials, retry with valid ones
    Given I open the app
    When I login with username "<invalidUsername>" and password "<invalidPassword>"
    Then I should "<result>" with valid username "<validUsername>" and password "<validPassword>"

    Examples:
      | invalidUsername     | invalidPassword | result               | validUsername              | validPassword |
      | bakhira88@gmail.com | b@khira88       | See an error message | gideon.mokgabodi@gmail.com | B@khira88     |

  Scenario: Add a new item
    Given I am logged in
    When I add a todo with text "My first task"
    Then I should see "My first task" in the list

  Scenario: Edit an item
    Given I am logged in
    And a todo "My first task" exists
    When I edit the todo "My first task" to "Updated task"
    Then I should see "Updated task" in the list

  Scenario: Delete anitem
    Given I am logged in
    And a todo "Updated task" exists
    When I delete the todo "Updated task"
    Then I should not see "Updated task" in the list