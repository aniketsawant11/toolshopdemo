Feature: Search Product Page
  As a shopper
  I want to search for products
  So that I can find items to buy

  Background:
    Given the user is on the home page

  Scenario: Search for an existing product on Search Page
    When the user search for "Screwdriver"
    Then search results should contain at least one product
