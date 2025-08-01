@ui
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
    When I select an Item and add it to Cart"
    Then I should go to cart and see my item in the cart
    
  Scenario: Update and Delete Item
    Given I am logged in
    And I want to edit or delete an existing item in my cart
    Then Click Take a Lot home page to add another item