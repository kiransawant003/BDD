Feature: Create Application and Originate for both merchant

  Background: Copy the feature file
    Given feature file is Churn

  @normaluserBTL @churnRegression
  Scenario Outline: Create and Originate Application Using BTL for Normal User
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
    And _In Review App Search by Email_Address
    And update paydate for Normal user
    And Change due date
    Then Check if ReSign required for "<Merchant>" and "<PaymentMode>"
    And Originate Application

    Examples: 
      | FileName            | PaymentMode              | Merchant |
      | BTL_QA_ClientData_1 | Electronic Fund Transfer |BTL|

  @normaluserMLL @churnRegression
  Scenario Outline: Create and Originate Application Using MaxLend for Normal User
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
    When user enters login details in LMS for MLL
    And _In Review App Search by Email_Address
    And update paydate for Normal user
    And Change due date
    Then Check if ReSign required for "<Merchant>" and "<PaymentMode>"
    And Originate Application

    Examples: 
      | FileName            | PaymentMode              | Merchant |
      | MLL_QA_ClientData_1 | Electronic Fund Transfer | MLL	|

  ####### VIP USER ########
  @VIPBTL @churnRegression
  Scenario Outline: Create and Originate Application Using BTL for VIP User
    Given user load and fetch file "<FileName>"
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And _In Review App Search for VIP User by SSN#
    And Change due date
    Then Check if ReSign required for "<Merchant>" and "<PaymentMode>"
    And Originate Application
    Then if not paid off then pay it off
    Then user is on BTL homepage
    And user enters existing login details in BTL
    And Create Password with VALID Information
    Then Login to the Application and click on Apply Now
    And _Specify information on Income Page for Employed with DirectDeposit for VIP User
    And _Specify information on Banking Page using "<PaymentMode>" for VIP User
    Then Apply Contact Consent
    And _Apply ESign with EFT for VIP
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And _In Review App Search for VIP User by SSN#
    # And Update VIP Cutomer table
    And update paydate for VIP user
    And Change due date
    Then Check if ReSign required for "<Merchant>" and "<PaymentMode>"
    And Originate Application
    And Update VIP Cutomer table

    Examples: 
      | FileName       | PaymentMode              |Merchant|
      | BTL_QA_VIPData | Electronic Fund Transfer |BTL|

  @VIPMLL @churnRegression
  Scenario Outline: Create and Originate Application Using MLL for VIP User
    Given user load and fetch file "<FileName>"
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    Then verify login to TRANS LMS is successful
    And _In Review App Search for VIP User by SSN#
    And Change due date
    And Originate Application
    Then if not paid off then pay it off
    Then user is on MLL homepage
    And user enters existing login details in MLL
    And Create Password with VALID Information
    Then Login to the Application and click on Apply Now
    And _Specify information on Income Page for Employed with DirectDeposit for VIP User
    And _Specify information on Banking Page using "<PaymentMode>" for VIP User
    Then Apply Contact Consent
    And _Apply ESign with EFT for VIP
    Then user is on LMS homepage
    When user enters login details in LMS for MLL
    And _In Review App Search for VIP User by SSN#
    #  And Update VIP Cutomer table
    And update paydate for VIP user
    And Change due date
    And Originate Application
    And Update VIP Cutomer table

    Examples: 
      | FileName       | PaymentMode              |
      | MLL_QA_VIPData | Electronic Fund Transfer |

  ## Create Application Normal User ##
  @churnRegression @createApplicationUsingBTL
  Scenario Outline: Create Application Using BTL for Normal User
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
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address

    Examples: 
      | FileName            | PaymentMode              |
      | BTL_QA_ClientData_1 | Electronic Fund Transfer |

  @churnRegression @createApplicationUsingMLL
  Scenario Outline: Create  Application Using MaxLend for Normal User
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
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address

    Examples: 
      | FileName            | PaymentMode              |
      | MLL_QA_ClientData_1 | Electronic Fund Transfer |

  #### PPC User  ##
  @PPCBTL @churnRegression
  Scenario Outline: Create and Originate Application Using BTL for PPC User
    Given Get customer details and file "<FileName>" for PPC User
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And _In Review App Search for VIP User by SSN#
    And Change due date
    And Originate Application
    Then if not paid off then pay it off
    When user is on BTL homepage and clicks on Apply Now and enters SSN Number
    Then User lands on Login page and enters email and click on login
    And Create Password with VALID Information
    Then Login to the Application and click on Apply Now
    And _Specify information on Income Page for Employed with DirectDeposit for VIP User
    And _Specify information on Banking Page using "<PaymentMode>" for VIP User
    Then Apply Contact Consent
    And _Apply ESign with EFT for VIP
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And _In Review App Search for VIP User by SSN#
    #  And Update VIP Cutomer table
    And update paydate for PPC user
    And Change due date
    And Originate Application
    And Update VIP Cutomer table

    Examples: 
      | FileName       | PaymentMode              |
      | BTL_QA_VIPData | Electronic Fund Transfer |

  @PPCMLL @churnRegression
  Scenario Outline: Create and Originate Application Using MLL for PPC User
    Given Get customer details and file "<FileName>" for PPC User
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    Then verify login to TRANS LMS is successful
    And _In Review App Search for VIP User by SSN#
    And Change due date
    And Originate Application
    Then if not paid off then pay it off
    When user is on MLL homepage and clicks on Apply Now and enters SSN Number
    Then User lands on Login page and enters email and click on login
    And Create Password with VALID Information
    Then Login to the Application and click on Apply Now
    And _Specify information on Income Page for Employed with DirectDeposit for VIP User
    And _Specify information on Banking Page using "<PaymentMode>" for VIP User
    Then Apply Contact Consent
    And _Apply ESign with EFT for VIP
    Then user is on LMS homepage
    When user enters login details in LMS for MLL
    And _In Review App Search for VIP User by SSN#
    # And Update VIP Cutomer table
    And update paydate for PPC user
    And Change due date
    And Originate Application
    And Update VIP Cutomer table

    Examples: 
      | FileName       | PaymentMode              |
      | MLL_QA_VIPData | Electronic Fund Transfer |

  @PiflaonEFTBTL @churnRegression
  Scenario Outline: BTL_Originate Application and Verify Status
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
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And Change due date
    Then Check if ReSign required for "<Merchant>" and "<PaymentMode>"
    And Originate Application
    #Then originate process start
    And pay of status change

    Examples: 
      | FileName            | PaymentMode              | Merchant |
      | BTL_QA_ClientData_1 | Electronic Fund Transfer | BTL   |

  @PiflaonEFTMLL @churnRegression
  Scenario Outline: MLL_Originate Application and Verify Status
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
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And Change due date
    Then Check if ReSign required for "<Merchant>" and "<PaymentMode>"
    And Originate Application
    # Then originate process start
    And pay of status change

    Examples: 
      | FileName            | PaymentMode              | Merchant|
      | MLL_QA_ClientData_1 | Electronic Fund Transfer | MLL			|
