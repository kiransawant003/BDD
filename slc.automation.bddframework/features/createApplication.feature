Feature: Data Creation
@Tag1
Scenario Outline: Data Creation for Client Data
Given user load configuration file "<FileName>"
Examples:
|FileName|
|BTL_QA_ClientData_1|

@Tag1
Scenario Outline: BTL New User ESign using EFT for Employed
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on BTL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
Then Apply Contact Consent
And Apply ESign with EFT
Examples:
|FileName|PaymentMode|
|BTL_QA_ClientData_1|Electronic Fund Transfer|

@Tag1
Scenario Outline: BTL New User ESign using Credit Card for Employed
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on BTL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
Then Apply Contact Consent
And Apply ESign with EFT
Examples:
|FileName|PaymentMode|
|BTL_QA_ClientData_1|Credit Card|

@Tag1
Scenario Outline: BTL New User ESign using EFT for Other Income Source
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on BTL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Others with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
Then Apply Contact Consent
And Apply ESign with EFT
Examples:
|FileName|PaymentMode|
|BTL_QA_ClientData_1|Electronic Fund Transfer|


#validation for BTL ABoutyou, Banking  page and ECF used
#@Tag1
#Scenario Outline: BTL New User ESign using EFT for Other Income Source
#Given user load configuration file "<FileName>"
#When user fetch the information
#Then user is on BTL homepage
#And Specify information on AboutYou Page_All validation
#And Specify information on Income Page for Others
#And Specify information on Banking Page_All validation using "<PaymentMode>"
#Then Apply Contact Consent
#And Apply ESign
#Examples:
#|FileName|PaymentMode|
#|BTL_QA_ClientData_1|Electronic Fund Transfer|

@Tag1
Scenario Outline: BTL New User ESign using Credit Card for Other Income Source
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on BTL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Others with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
Then Apply Contact Consent
And Apply ESign with EFT
Examples:
|FileName|PaymentMode|
|BTL_QA_ClientData_1|Credit Card|


#################MaxLend Cases##################
@Tag1
Scenario Outline: Maxlend New User ESign using EFT for Employed
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
Then Apply Contact Consent
And Apply ESign with EFT
Examples:
|FileName|PaymentMode|
|MLL_QA_ClientData_1|Electronic Fund Transfer|
@Tag1
Scenario Outline: Maxlend New User ESign using Credit Card for Employed
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
Then Apply Contact Consent
And Apply ESign with EFT
Examples:
|FileName|PaymentMode|
|MLL_QA_ClientData_1|Credit Card|

@Tag1
Scenario Outline: Maxlend New User ESign using EFT for Other Income Source
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Others with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
Then Apply Contact Consent
And Apply ESign with EFT
Examples:
|FileName|PaymentMode|
|MLL_QA_ClientData_1|Electronic Fund Transfer|

@Tag1
Scenario Outline: Maxlend New User ESign using Credit Card for Other Income Source
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Others with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
Then Apply Contact Consent
And Apply ESign with EFT
Examples:
|FileName|PaymentMode|
|MLL_QA_ClientData_1|Credit Card|

#Maxlend  validation for About page and Banking
@Tag1
Scenario Outline: Maxlend New User ESign using EFT for Other Income Source
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page_All validation
And Specify information on Income Page for Others
And Specify information on Banking Page_All validation using "<PaymentMode>"
Then Apply Contact Consent
And Apply ESign
Examples:
|FileName|PaymentMode|
|MLL_QA_ClientData_1|Electronic Fund Transfer|