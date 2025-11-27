Feature: User API

  Scenario: Create a new user
    Given the user details are
      | id | username | email           |
      | 1  | JohnDoe  | john@example.com |
    When the user is created
    Then the user should be successfully created with username "JohnDoe"
