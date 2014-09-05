@api
Feature: REST Service
  As an API user
  In order to display messages in the UI
  I need to be able to upload and retrieve messages

  Scenario: Retrieve A Single Message
    Given a reference to a valid message
    When I GET the echo resource
    Then I should get a status code of 200
    And the message should be returned to me

  Scenario: Message Retrieval Failure (invalid identifier)
    Given a reference to an invalid message
    When I GET the echo resource
    Then I should get a status code of 404
    And I should get an explanation of the failure

  Scenario: Retrieve All Messages
    Given there are messages in the system
    When I GET the echo collection resource
    Then I should get a status code of 200
    And all messages should be returned to me

  Scenario: Retrieve All Messages (no data in the system)
    Given there are no messages in the system
    When I GET the echo collection resource
    Then I should get a status code of 200
    And no messages should be returned to me

  Scenario: Obtaining The Insertion Template
    Given my desire to insert a new message into the system
    When I GET the insert template resource
    Then I should get a status code of 200
    And the template should be returned to me

  Scenario: Inserting A New Message
    Given my desire to insert a new message into the system
    When I GET the insert template resource
    And I fill in the template resource
    And I POST the template resource
    Then I should get a status code of 201
    And the Location header should contain the URI of the resource

  Scenario: Updating An Existing Message
    Given a reference to a valid message
    When I GET the update template resource
    And I fill in the template resource
    And I PUT the template resource
    Then I should get a status code of 200
    And the message should be returned to me
