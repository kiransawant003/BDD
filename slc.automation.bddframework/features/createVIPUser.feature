Feature: Create new VIP User

  @CreateVIPUser  @CheckUser1
  Scenario Outline: Create new VIP User for merchant Blue Trust
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then check try with another lender for BTL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with EFT
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address
    And update paydate for Normal user
    And Change due date
    Then Check if ReSign required for "<Merchant>" and "<PaymentMode>"
    And Originate Application
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And complete the payment using payment mode as Paid in Full using CASH
    And Capture the Cust VIP Level
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
     And Verify the application status as Appl Paid Off
    Then user is on BTL homepage
    And user enters login details for loan in BTL
    And Create Password for ssn Information for BTL
    Then Apply for loan
    Then Specify paydates information
    And Specify information on Banking Page using "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with EFT
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address
    And Capture the Cust VIP Level
   # Then Copy VIP data to database table for Blue Trust
    And Apply till user becomes VIP for BTL with payment mode as EFT
    Then Copy VIP data to database table for Blue Trust
    

    Examples: 
      | FileName            | PaymentMode              | Merchant |
      | BTL_QA_ClientData_1 | Electronic Fund Transfer | BTL      |

      
      
      @CreateVIPUser @test987548
  Scenario Outline: Create new VIP User for merchant Max Lend
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with EFT
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    And In Review App Search by Email_Address
    And update paydate for Normal user
    And Change due date
    Then Check if ReSign required for "<Merchant>" and "<PaymentMode>"
    And Originate Application
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And complete the payment using payment mode as Paid in Full using CASH
    And Capture the Cust VIP Level
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
     And Verify the application status as Appl Paid Off
    Then user is on MLL homepage
    And user enters login details for loan in MLL
    And Create Password for ssn Information for MLL
    Then Apply for loan
    Then Specify paydates information
    And Specify information on Banking Page using "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with EFT
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    And In Review App Search by Email_Address
    And Capture the Cust VIP Level
    And Apply till user becomes VIP for MLL with payment mode as EFT
    Then Copy VIP data to database table for Max Lend
   # And Continue to apply loan and pay off untiill user becomes VIP for BTL "<PaymentMode>"
    

    Examples: 
      | FileName            | PaymentMode              |Merchant |
      | MLL_QA_ClientData_1 | Electronic Fund Transfer |MLL	|