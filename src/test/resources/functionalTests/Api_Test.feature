Feature: API Testing for Rover Photos
  Description: These are the test assessment exercises using BDD with Cucumber

  Background: The API Key is previously generated

    Scenario: Number of photos returned by the request filtered by page equals 25.
      When I request the first page
      Then The number of photos is 25

    Scenario: Photos returned by the MAST camera were taken only by the Curiosity Rover.
      When I request the photos from the MAST camera
      Then I confirm all photos were taken by the Curiosity Rover
