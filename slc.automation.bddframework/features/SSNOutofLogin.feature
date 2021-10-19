Feature: SSN Out of Login

  Background: Copy the feature file
    Given feature file is SSNOutOfLogin

  @SSNOutofLogin @Regression @Check311
  Scenario Outline: BTL_Register after Application_with New Customer_VALID
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign and Register using VALID
    Then Welcome Back Login Existing

    Examples: 
      | FileName            | PaymentMode              |
      | BTL_QA_ClientData_1 | Electronic Fund Transfer |

  @SSNOutofLogin @Regression
  Scenario Outline: BTL_Register after Application_with New Customer_INVALID
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign and Register using INVALID

    Examples: 
      | FileName            | PaymentMode              |
      | BTL_QA_ClientData_1 | Electronic Fund Transfer |

  @SSNOutofLogin @Regression @TestCode123
  Scenario Outline: BTL_Register after Application_Existing Customer_Not Registered_Customer with unique email_VALID
    Given user load and fetch file "<FileName>"
    Then user is on BTL homepage
    And user enters existing login details in BTL
    And Create Password with VALID Information
    Then Welcome Back Login Existing

    Examples: 
      | FileName       |
      | BTL_QA_VIPData |

  @SSNOutofLogin @Regression @Check1
  Scenario Outline: BTL_Register after Application_Existing Customer_Not Registered_Customer with unique email_INVALID
    Given user load and fetch file "<FileName>"
    Then user is on BTL homepage
    And user enters existing login details in BTL
    And Create Password with INVALID Information

    Examples: 
      | FileName       |
      | BTL_QA_VIPData |

  @SSNOutofLogin_tocheck @Regression
  Scenario Outline: BTL_Register after Application_MULTIPLE USER_Not Registered_Customer with unique email_VALID
    Given user load and fetch Multiple User "<FileName>"
    Then user is on BTL homepage
    And Login and Validate UserType
    And Create Password with VALID Information
    Then Welcome Back Login after Register

    Examples: 
      | FileName       |
      | BTL_QA_VIPData |

  @SSNOutofLogin_tocheck @Regression
  Scenario Outline: BTL_Reset Password
    Given user fetch Register User "<FileName>"
    When user is on BTL homepage
    Then user enters new login details in BTL
    And Apply to Reset Password

    Examples: 
      | FileName       |
      | BTL_QA_LMSData |

  ################# MaxLend Cases ##################
  @SSNOutofLogin @Regression @Check2
  Scenario Outline: MML_Register after Application_with New Customer_VALID
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign and Register using VALID
    Then Welcome Back Login Existing

    Examples: 
      | FileName            | PaymentMode              |
      | MLL_QA_ClientData_1 | Electronic Fund Transfer |

  @SSNOutofLogin @Regression
  Scenario Outline: MML_Register after Application_with New Customer_INVALID
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign and Register using INVALID

    #Then Welcome Back Login Existing
    Examples: 
      | FileName            | PaymentMode              |
      | MLL_QA_ClientData_1 | Electronic Fund Transfer |

  @SSNOutofLogin @Regression @check001
  Scenario Outline: MML_Register after Application_Existing Customer_Not Registered_Customer with unique email_VALID
    Given user load and fetch file "<FileName>"
    Then user is on MLL homepage
    And user enters existing login details in MLL
    And Create Password with VALID Information
    Then Welcome Back Login Existing

    Examples: 
      | FileName       |
      | MLL_QA_VIPData |

  @SSNOutofLogin @Regression
  Scenario Outline: MLL_Register after Application_Existing Customer_Not Registered_Customer with unique email_INVALID
    Given user load and fetch file "<FileName>"
    Then user is on MLL homepage
    And user enters existing login details in MLL
    And Create Password with INVALID Information

    Examples: 
      | FileName       |
      | MLL_QA_VIPData |

  @SSNOutofLogin_tocheck @Regression
  Scenario Outline: MLL_Register after Application_MULTIPLE USER_Not Registered_Customer with unique email_VALID
    Given user load and fetch Multiple User "<FileName>"
    Then user is on MLL homepage
    And Login and Validate UserType
    And Create Password with VALID Information
    Then Welcome Back Login after Register

    Examples: 
      | FileName       |
      | MLL_QA_VIPData |
