Feature: Create and Originate the Application 

 @createOriginateBTL @CreateAndOriginateApplication
  Scenario Outline: BTL_Create and Originate application
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address
    And verify if TxtACheck button is selected
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And Originate Application for TxtACheck for Blue Trust
    And get the application number for Blue trust

    Examples: 
      | FileName            | PaymentMode |
      | BTL_QA_ClientData_1 | Txt-A-Check |

  @createOriginateMLL @CreateAndOriginateApplication
  Scenario Outline: MLL_Create and Originate application
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And Originate Application for TxtACheck for Max Lend
    And get the application number for Max Lend

    Examples: 
      | FileName            | PaymentMode |
      | MLL_QA_ClientData_1 | Txt-A-Check |

#create application with Pending status without origination

 @createWithoutOriginateBTL @CreatePendingApplication
  Scenario Outline: BTL_Create and Originate application
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
   # And Originate Application for TxtACheck for Blue Trust
    And get the application number for Blue trust

    Examples: 
      | FileName            | PaymentMode |
      | BTL_QA_ClientData_1 | Txt-A-Check |

  @createWithoutOriginateMLL @CreatePendingApplication
  Scenario Outline: MLL_Create and Originate application
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
   # And Originate Application for TxtACheck for Max Lend
    And get the application number for Max Lend

    Examples: 
      | FileName            | PaymentMode |
      | MLL_QA_ClientData_1 | Txt-A-Check |




# create loan with pending status after origination

 @createLoanBTL @CreateWithPendingStatusAndEsig
  Scenario Outline: BTL_Create and Originate application
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And get the application number for Blue trust

    Examples: 
      | FileName            | PaymentMode |
      | BTL_QA_ClientData_1 | Txt-A-Check |

  @createLoanMLL @CreateWithPendingStatusAndEsig
  Scenario Outline: MLL_Create and Originate application
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    #And Originate Application for TxtACheck for Max Lend
    And get the application number for Max Lend

    Examples: 
      | FileName            | PaymentMode |
      | MLL_QA_ClientData_1 | Txt-A-Check |