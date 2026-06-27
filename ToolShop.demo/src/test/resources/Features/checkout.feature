Feature: Checkout Page
  As a shopper
  I want to provide shipping details and confirm my order
  So that I can complete a purchase

  Background:
    Given the user has at least one product in the cart
    And the user is on the cart page

  Scenario: Fill shipping and confirm order on Checkout Page
    When the user proceeds to checkout
    And the user fills shipping details with housenumber "222",postalCode "4555"      
    Then  the user confirms the order
    And  the user choose payment method
    Then the order confirmation message should be displayed
