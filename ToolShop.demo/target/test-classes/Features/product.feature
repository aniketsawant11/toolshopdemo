Feature: Product Page
  As a shopper
  I want to view a product and add it to cart
  So that I can choose items to purchase

  Background:
    Given the user is on the Tool Shop home page

  Scenario: View product details and add to cart on Product Page
    When the user searches for "hammer"
    And the user selects the first product from search results
    Then the product details page should be displayed
    When the user adds the product to the cart
    Then a success message for adding to cart should be shown
