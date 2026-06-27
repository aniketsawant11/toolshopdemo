Feature: Cart Page
  As a shopper
  I want to view and manage my cart
  So that I can review items before checkout

  Background:
    Given the user has at least a product in the cart

  Scenario: View cart contents on Cart Page
    When the user views the cart
    Then the cart page should show the product list
    And the cart total should be displayed
