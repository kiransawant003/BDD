Feature: eSignLoan
@Tag1
Scenario Outline: Perform ESign and Validate ESign Link for BlueTrust Merchant Site
Given user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by "<SearchType>" having "<ApplNo>"
Then verify Customer Details has Not E-sign
And Logout of LMS Page
Given user is on BTL homepage
When user enters login details in BTL with "<Email>" and "<SSN>"
Then verify "<Email>" in BTL and perform e-sign on page
Given user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by "<SearchType>" having "<ApplNo>"
Then verify Customer Details has is E-sign
And Logout of LMS Page
Examples:
|SearchType|ApplNo|Email|SSN|
|Appl#|300254588|Delores.Rafalowitz@slc01.com|300168829|

@Tag1
Scenario Outline: Perform ESign and Validate ESign Link for Maxlend Merchant Site
Given user is on LMS homepage
When user enters login details in LMS for MaxLend Loan
Then verify login to TRANS LMS is successful
And In Review App Search by "<SearchType>" having "<ApplNo>"
Then verify Customer Details has Not E-sign
And Logout of LMS Page
Given user is on MLL homepage
When user enters login details in MLL with "<Email>" and "<SSN>"
Then verify "<Email>" in MLL and perform e-sign on page
Given user is on LMS homepage
When user enters login details in LMS for MaxLend Loan
Then verify login to TRANS LMS is successful
And In Review App Search by "<SearchType>" having "<ApplNo>"
Then verify Customer Details has is E-sign
And Logout of LMS Page
Examples:
|SearchType|ApplNo|Email|SSN|
|Appl#|300252796|Cristian.Sheahan@slc01.com|842717157|

@Tag1
Scenario Outline: Perform DB Connection
Given user connects to "<DBName>"
Examples:
|DBName|
|BankA|