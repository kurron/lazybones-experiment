@api
Feature: Echo Service
    As an API user
    In order to store my messages for later retrieval
    I need to upload them to the server

Scenario: Send A Message
    Given a text message I want to store
    When I call the echo message service
    Then my message should be stored in the system
