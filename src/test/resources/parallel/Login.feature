Feature: Login Page Feature 

Scenario: Customer login to the app and validate Forgot Password Link chrome 
Given user is on login page
When user enter Username 
And user enter Password 
Then forgot your password link should be displayed
