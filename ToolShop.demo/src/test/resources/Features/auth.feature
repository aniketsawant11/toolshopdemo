Feature: Authentication Page
  As a shopper
  I want to log in and log out
  So that I can access account features and sign out securely

  Background:
    Given the user is on the ToolShop home page

  Scenario: Login and Logout on Authentication Page
    When the user logs in with credentials:
      | username | customer3@practicesoftwaretesting.com |
      | password | pass123          |
    Then the user should be logged in
    When the user logs out
    Then the user should be logged out
