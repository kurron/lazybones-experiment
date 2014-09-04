@api
Feature: Echo Service
  As an API user
  In order to display messages in the UI
  I need to be able to upload and retrieve messages

  Scenario: Retrieve A Message
    Given a previously stored message
    When I call the fetch message service
    Then my message should be returned to me

  Scenario: Message Retrieval Failure
    Given a reference to an invalid message
    When I call the fetch message service
    Then I should get an explanation of the failure
