Feature: Run the Smoke test

  Background: Copy the feature file
    Given feature file is Smoke Test

  @smoke @test01
  Scenario Outline: Create and Originate Application using ACH payment method for Blue Trust
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
      | FileName            | PaymentMode              |Merchant|
      | BTL_QA_ClientData_1 | Electronic Fund Transfer |BTL|

  @smoke @test02
  Scenario Outline: Create and Originate Application using ACH payment method for Max Lend
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
      | MLL_QA_ClientData_1 | Electronic Fund Transfer | MLL |

  @smoke @checkTryWithAnotherLender @test03
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
    And update paydate for VIP user
    And Change due date
    Then Check if ReSign required for "<Merchant>" and "<PaymentMode>"
    And Originate Application
    And Update VIP Cutomer table

    Examples: 
      | FileName       | PaymentMode              | Merchant |
      | BTL_QA_VIPData | Electronic Fund Transfer |BTL |

  @smoke @checkVIP @test04
  Scenario Outline: Create and Originate Application Using MLL for VIP User
    Given user load and fetch file "<FileName>"
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    Then verify login to TRANS LMS is successful
    And _In Review App Search for VIP User by SSN#
    And Change due date
    Then Check if ReSign required for "<Merchant>" and "<PaymentMode>"
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
    And update paydate for VIP user
    And Change due date
    Then Check if ReSign required for "<Merchant>" and "<PaymentMode>"
    And Originate Application
    And Update VIP Cutomer table

    Examples: 
      | FileName       | PaymentMode              | Merchant |
      | MLL_QA_VIPData | Electronic Fund Transfer |MLL |

  @smoke @test05
  Scenario Outline: Create and Originate Application Using BTL for PPC User
    Given Get customer details and file "<FileName>" for PPC User
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And _In Review App Search for VIP User by SSN#
    And Change due date
    Then Check if ReSign required for "<Merchant>" and "<PaymentMode>"
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
    Then Check if ReSign required for "<Merchant>" and "<PaymentMode>"
    And Originate Application
    And Update VIP Cutomer table

    Examples: 
      | FileName       | PaymentMode              | Merchant |
      | BTL_QA_VIPData | Electronic Fund Transfer | BTL |

  @smoke @test06
  Scenario Outline: Create and Originate Application Using MLL for PPC User
    Given Get customer details and file "<FileName>" for PPC User
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    Then verify login to TRANS LMS is successful
    And _In Review App Search for VIP User by SSN#
    And Change due date
    Then Check if ReSign required for "<Merchant>" and "<PaymentMode>"
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
    Then Check if ReSign required for "<Merchant>" and "<PaymentMode>"
    And Originate Application
    And Update VIP Cutomer table

    Examples: 
      | FileName       | PaymentMode              |Merchant |
      | MLL_QA_VIPData | Electronic Fund Transfer | MLL |

  @smoke @test07
  Scenario Outline: Verify paid in full for Blue Trust
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
    And pay of status change

    Examples: 
      | FileName            | PaymentMode              |Merchant |
      | BTL_QA_ClientData_1 | Electronic Fund Transfer | BTL |

  @smoke @test08
  Scenario Outline: Verify paid in full for Max Lend
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
    And pay of status change

    Examples: 
      | FileName            | PaymentMode              |Merchant |
      | MLL_QA_ClientData_1 | Electronic Fund Transfer |MLL |

  @smoke @test09
  Scenario Outline: BTL_Register Application for Blue Trust
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then check try with another lender for BTL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign and Register using VALID
    Then Welcome Back Login Existing

    Examples: 
      | FileName            | PaymentMode              |
      | BTL_QA_ClientData_1 | Electronic Fund Transfer |

  @smoke @test010
  Scenario Outline: MLL_Register after Application for Max Lend
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign and Register using VALID
    Then Welcome Back Login Existing

    Examples: 
      | FileName            | PaymentMode              |
      | MLL_QA_ClientData_1 | Electronic Fund Transfer |

  @smoke @CheckCode @test001
  Scenario Outline: To validate Re-Sign button in BTL
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then check try with another lender for BTL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign and Register using VALID
    Then Welcome Back Login Existing
    And validate Re-Esign button
    Then Apply Contact Consent
    And Apply ESign with EFT

    Examples: 
      | FileName            | PaymentMode              |
      | BTL_QA_ClientData_1 | Electronic Fund Transfer |

  @smoke @test011
  Scenario Outline: To validate Re-Sign button in MLL
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign and Register using VALID
    Then Welcome Back Login Existing
    And validate Re-Esign button
    Then Apply Contact Consent
    And Apply ESign with EFT

    Examples: 
      | FileName            | PaymentMode              |
      | MLL_QA_ClientData_1 | Electronic Fund Transfer |

   @smoke @test012
  Scenario Outline: To validate forgot password for Blue Trust
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then check try with another lender for BTL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign and Register using VALID
    Then Welcome Back Login Existing
    Then Logout of the application and re-login
    And Click on forgot password link and Reset the password
    Then open the reset link

    Examples: 
      | FileName            | PaymentMode              |
      | BTL_QA_ClientData_1 | Electronic Fund Transfer |
      
      @smoke @test013
  Scenario Outline: To validate forgot password for Max Lend
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign and Register using VALID
    Then Welcome Back Login Existing
    Then Logout of the application and re-login
    And Click on forgot password link and Reset the password
    Then open the reset link

    Examples: 
      | FileName            | PaymentMode              |
      | BTL_QA_ClientData_1 | Electronic Fund Transfer |


  # TxtACheck
  @smoke @test014
  Scenario Outline: Create and Originate Application using TxtACheck payment method for Blue Trust
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then check try with another lender for BTL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And Originate Application for TxtACheck for Blue Trust

    Examples: 
      | FileName            | PaymentMode |
      | BTL_QA_ClientData_1 | Txt-A-Check |

  @smoke @test015
  Scenario Outline: Create and Originate Application using TxtACheck payment method for Max Lend
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And Originate Application for TxtACheck for Max Lend

    Examples: 
      | FileName            | PaymentMode |
      | BTL_QA_ClientData_1 | Txt-A-Check |
