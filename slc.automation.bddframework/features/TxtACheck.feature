Feature: TxtACheck Scenario

  Background: Copy the feature file
    Given feature file is TxtACheck

  @TextACheck111 @Regression @TxtACheckRegression
  Scenario Outline: ML merchant site_Apply now page_UIVALIDATION
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And UI Verify on Banking Page using "<PaymentMode>"

    Examples: 
      | FileName            | PaymentMode |
      | MLL_QA_ClientData_1 | Txt-A-Check |

  @TextACheck @Regression @TxtACheckRegression @check98765
  Scenario Outline: ML merchant site_Apply now page_Successful_ESign
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck

    Examples: 
      | FileName            | PaymentMode |
      | MLL_QA_ClientData_1 | Txt-A-Check |

  @TextACheck @Regression @TxtACheckRegression
  Scenario Outline: ML_Perform TxtACheck Process and Validate Originate Button
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
    When user enters login details in LMS for MaxLend
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then verify Originate Button
    And Logout of LMS Page

    Examples: 
      | FileName            | PaymentMode |
      | MLL_QA_ClientData_1 | Txt-A-Check |

  @TextACheck @Regression @TxtACheckRegression 
  Scenario Outline: ML_Perform TxtACheck Complete Process and Validate Originate Button
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
    When user enters login details in LMS for MaxLend
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for MaxLend
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And verify TxtACheck Complete
    And Logout of LMS Page

    Examples: 
      | FileName            | PaymentMode |
      | MLL_QA_ClientData_1 | Txt-A-Check |

  @TextACheckdemotest @Regression @TxtACheckRegression @Test12345
  Scenario Outline: ML_Originate Application and Verify Status 
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
    When user enters login details in LMS for MaxLend
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for MaxLend
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And perform Originate and validate status for Max Lend
    And Logout of LMS Page

    Examples: 
      | FileName            | PaymentMode |
      | MLL_QA_ClientData_1 | Txt-A-Check |

  ################# BlueTrust Cases ##################
  @TextACheck11 @Regression @TxtACheckRegression
  Scenario Outline: BTL merchant site_Apply now page_UIVALIDATION
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And UI Verify on Banking Page using "<PaymentMode>"

    Examples: 
      | FileName            | PaymentMode |
      | BTL_QA_ClientData_1 | Txt-A-Check |

  @TextACheck @Regression @TxtACheckRegression
  Scenario Outline: BTL merchant site_Apply now page_Successful_ESign
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then check try with another lender for BTL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck

    Examples: 
      | FileName            | PaymentMode |
      | BTL_QA_ClientData_1 | Txt-A-Check |

  @TextACheck @Regression @TxtACheckRegression
  Scenario Outline: BTL_Perform TxtACheck Process and Validate Originate Button
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
    Then verify Originate Button
    And Logout of LMS Page

    Examples: 
      | FileName            | PaymentMode |
      | BTL_QA_ClientData_1 | Txt-A-Check |

  @TextACheck @Regression @TxtACheckRegression @CheckApplication
  Scenario Outline: BTL_Perform TxtACheck Complete Process and Validate Originate Button
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
    And verify TxtACheck Complete
    And Logout of LMS Page

    Examples: 
      | FileName            | PaymentMode |
      | BTL_QA_ClientData_1 | Txt-A-Check |

  @TextACheck @Regression @TxtACheckRegression
  Scenario Outline: BTL_Originate Application and Verify Status
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
    And perform Originate and validate status for Blue Trust
    And Logout of LMS Page

    Examples: 
      | FileName            | PaymentMode |
      | BTL_QA_ClientData_1 | Txt-A-Check |

  ################# Delete Scenario - Sonali##################
  #Loan amount change and originate testcase BTL
  #@TextACheckdemo @Regressiondemo
  @loanamountchangeBTL @regression @CheckApp @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_Loan amount change process
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    #    And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
    Then check try with another lender for BTL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address
    And Review App verify process txtacheck
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
    And change loan amount
    And Logout of LMS Page
    When user is on BTL homepage
    And user enters login details for loan in BTL
    And Create Password for ssn Information for BTL
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address
    And Esign check process
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address
    And Receive Mail
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    # Then originate process start
    And Originate Application for TxtACheck for Blue Trust
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address
    And Review App verify process txtacheck

    Examples: 
      | FileName             | PaymentMode |
      | BTL_DEV_ClientData_1 | Txt-A-Check |

  #below Withdraw test cases for pending application BTL
  #@TextACheckdemo @Regressiondemo
  @WithdrawBTL @regression @CheckApp123 @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_Withdraw process
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    #    And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
    Then check try with another lender for BTL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And Withdraw process start

    Examples: 
      | FileName             | PaymentMode |
      | BTL_DEV_ClientData_1 | Txt-A-Check |

  ##below for void loan test cases  BTL
  #@TextACheckdemo @Regressiondemo
  @voidloanBTL @regression @CheckApp @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_void loan process
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    #  And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
    Then check try with another lender for BTL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    # Then originate process start
    And Originate Application for TxtACheck for Blue Trust
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then void loan process is start

    Examples: 
      | FileName             | PaymentMode |
      | BTL_DEV_ClientData_1 | Txt-A-Check |

  #Below for Charge Off (Manual) BTL
  #@TextACheckdemo @Regressiondemo
  @chargeoffBTL @regression @CheckApp @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_chargeoff_manual
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    #  And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
    Then check try with another lender for BTL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    #And verify TxtACheck Complete
    #   Then originate process start
    And Originate Application for TxtACheck for Blue Trust
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then Charge Off manual process start
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address

    Examples: 
      | FileName             | PaymentMode |
      | BTL_DEV_ClientData_1 | Txt-A-Check |

  #Below for  updates the pay dates from Help desk (update loan amount from help desk) BTL
  #@TextACheckdemo @Regressiondemo
  @HelpdeskupdateloanBTL @regression @CheckApp999 @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_updates loan amount from Help desk
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
    And update the pay date process start from Helpdesk
    And Receive Mail checkno notavailable
    #Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    #Then perform TextaCheck Process
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address
    And Review App verify process txtacheck

    Examples: 
      | FileName             | PaymentMode |
      | BTL_QA_ClientData_1 | Txt-A-Check |

  #Below for ACH to txtAcheck testcase
  #@TextACheckdemo @Regressiondemo
  @ACHtoTxtAcheckBTL @regression @CheckApp @failing
  Scenario Outline: Manual Extension Functional testing for TextAcheck_updates ACH to txtAcheck
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    #  And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
    Then check try with another lender for BTL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply paymentmode with EFT
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And Review App verify process txtacheck
    #Then schedule TxtACheck and Send Mail
    #And Logout of LMS Page
    #Then perform TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And choose payment method EFT
    And Logout of LMS Page
    When user is on BTL homepage
    And user enters login details for loan in BTL
    And Create Password for ssn Information for BTL
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address
    When Payment method is TxtACheck then DebitMethodId is 4
    And Review App verify process txtacheck

    Examples: 
      | FileName             | PaymentMode              |
      | BTL_DEV_ClientData_1 | Electronic Fund Transfer |

  ################# Delete Scenario - Gautam###################@TextACheckdemo @Regressiondemo
  #@TextACheckdemo @Regressiondemo
  @ABAaccountchangesOriginateStatusBTL @regression @CheckApptest @TxtACheckRegression
  Scenario Outline: BTL_OriginateStatus_ABA_ACCOUNT_changes
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
 #   And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
 Then check try with another lender for BTL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And verify TxtACheck Complete
    #  Then originate process start
    And Originate Application for TxtACheck for Blue Trust
    And Update Customer Maintenance Bank_Information
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address

    #And Receive Mail checkno notavailable
    #Then perform Second TextaCheck Process Accountno change
    #Then user is on LMS homepage
    #When user enters login details in LMS for Blue Trust
    #Then verify login to TRANS LMS is successful
    #And In Review App Search by Email_Address
    Examples: 
      | FileName             | PaymentMode |
      | BTL_DEV_ClientData_1 | Txt-A-Check |

  # Below new line added for ABA and Payroll ,loanamount change
  #Below for txtAcheck to ACH testcase for BTL
  @TxtAchecktoACHBTL @regression @CheckApp @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_updates txtAcheck to ACH
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    #  And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
    Then check try with another lender for BTL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And Review App verify process txtacheck
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
    And choose payment method ACH
    And Logout of LMS Page
    When user is on BTL homepage
    And user enters login details for loan in BTL
    And Create Password for ssn Information for BTL
    Then Apply Contact Consent
    And Apply paymentmode with EFT
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address
    When Payment method is ACH then DebitMethodId is 1
    And Review App verify process txtacheck

    Examples: 
      | FileName             | PaymentMode |
      | BTL_DEV_ClientData_1 | Txt-A-Check |

  @ABAaccountchangesPendingStatusBTL @regression @CheckApptest @TxtACheckRegression
  Scenario Outline: BTL_PendingStatus_ABA_ACCOUNT_changes
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    #   And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
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
    And Update Customer Maintenance Bank_Information
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address

    #And Receive Mail checkno notavailable
    #Then perform Second TextaCheck Process Accountno change
    #Then user is on LMS homepage
    #When user enters login details in LMS for Blue Trust
    #Then verify login to TRANS LMS is successful
    #And In Review App Search by Email_Address
    #Then originate process start
    #And Logout of LMS Page
    #Then user is on LMS homepage
    #When user enters login details in LMS for Blue Trust
    #Then verify login to TRANS LMS is successful
    #And In Review App Search by Email_Address
    Examples: 
      | FileName             | PaymentMode |
      | BTL_DEV_ClientData_1 | Txt-A-Check |

  #Below excuted for payrollc changes pending status Exuted sucessfully
  @PayrollchangespendingstatusBTL @regression
  Scenario Outline: BTL_Payroll_changes_pending status
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
    And Update Customer Maintenance Payroll_Information
    #And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address
    And Receive Mail checkno notavailable
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address

    #Then originate process start
    Examples: 
      | FileName             | PaymentMode |
      | BTL_DEV_ClientData_1 | Txt-A-Check |

  #@TextACheckdemo @Regressiondemo
  #Below excuted for payrollc changes originate status
  @PayrollchangesOriginatestatusBTL @regression
  Scenario Outline: BTL_Payroll_changes_originate status
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
    Then originate process start
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address
    And Update Customer Maintenance Payroll_Information
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address
    And Receive Mail
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address

    #Then originate process start
    Examples: 
      | FileName             | PaymentMode |
      | BTL_DEV_ClientData_1 | Txt-A-Check |

  #Below if for Additional payment made BTL (Manual Extension Regressio for TxtAcheck delete scenario)
  @AdditionalpaymentBTL @regression @CheckApp @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    # And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
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
    And verify TxtACheck Complete
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    #Then originate process start
    And Originate Application for TxtACheck for Blue Trust
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then Additional payment process

    Examples: 
      | FileName             | PaymentMode |
      | BTL_DEV_ClientData_1 | Txt-A-Check |

  @AdditionalpaymentusingcashmodeBTL @regression @data @CheckApp @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    #   And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
    Then check try with another lender for BTL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And verify TxtACheck Complete
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    #Then originate process start
    And Originate Application for TxtACheck for Blue Trust
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then Additional payment process payment type for CASH

    Examples: 
      | FileName             | PaymentMode |
      | BTL_DEV_ClientData_1 | Txt-A-Check |

  @AdditionalpaymentusingcheckmodeBTL @data @CheckApp @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    #  And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
    Then check try with another lender for BTL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And verify TxtACheck Complete
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    #Then originate process start
    And Originate Application for TxtACheck for Blue Trust
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then Additional payment process payment type for check

    Examples: 
      | FileName             | PaymentMode |
      | BTL_DEV_ClientData_1 | Txt-A-Check |

  #************Maxlend**************************************************************************************************************
  #@Tag
  #Scenario Outline: ML merchant site_Apply now page_Successful_ESign
  #Given user load configuration file "<FileName>"
  #When user fetch the information
  #Then user is on MLL homepage
  #And Specify information on AboutYou Page
  #And Specify information on Income Page for Employed with DirectDeposit
  #And Specify information on Banking Page using "<PaymentMode>"
  #Then Apply Contact Consent
  #And Apply ESign with TextCheck
  #Examples:
  #|FileName|PaymentMode|
  #|BTL_QA_ClientData_1|Txt-A-Check|
  #*************Below delete scenario for Maxlend Txtacheck************************************************************************
  #Loan amount change and originate MLL
  #@TextACheckdemo @Regressiondemo
  @loanamountchangeMLL @regression @CheckApp @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_Loan amount change process
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    #   And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
    Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And change loan amount
    And Logout of LMS Page
    When user is on MLL homepage
    And user enters login details for loan in MLL
    And Create Password for ssn Information for MLL
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    And In Review App Search by Email_Address
    And Esign check process
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    And In Review App Search by Email_Address
    And Receive Mail
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    #Then originate process start
    And Originate Application for TxtACheck for Max Lend
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    And In Review App Search by Email_Address
    And Review App verify process txtacheck

    Examples: 
      | FileName             | PaymentMode |
      | MLL_DEV_ClientData_1 | Txt-A-Check |

  #below Withdraw test cases for pending application for MLL
  #@TextACheckdemo @Regressiondemo
  @WithdrawMLL @CheckApp @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_Withdraw process
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    #  And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
    Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for MaxLend
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And Withdraw process start

    Examples: 
      | FileName             | PaymentMode |
      | MLL_DEV_ClientData_1 | Txt-A-Check |

  #below for void loan test cases MLL
  #@TextACheckdemo @Regressiondemo
  @voidloanMLL @CheckApp @done @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_void loan process
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
 #   And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
 Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    #  Then originate process start
    And Originate Application for TxtACheck for Max Lend #test the change
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then void loan process is start

    Examples: 
      | FileName             | PaymentMode |
      | MLL_DEV_ClientData_1 | Txt-A-Check |

  #Below for Charge Off (Manual) for MLL
  #@TextACheckdemo @Regressiondemo
  @chargeoffMLL @CheckApp @done @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_chargeoff_manual
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    #    And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
    Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    #And verify TxtACheck Complete
    #  Then originate process start
    And Originate Application for TxtACheck for Max Lend #test the change
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then Charge Off manual process start
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    And In Review App Search by Email_Address

    Examples: 
      | FileName             | PaymentMode |
      | MLL_DEV_ClientData_1 | Txt-A-Check |

  #Below if for Additional payment made MLL
  #@TextACheckdemo @Regressiondemo
  @AdditionalpaymentMLL @CheckApp @done @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    #    And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
    Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And verify TxtACheck Complete
    And Logout of LMS Page
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    #  Then originate process start
    And Originate Application for TxtACheck for Max Lend #test the change
    Then Additional payment process

    Examples: 
      | FileName             | PaymentMode |
      | MLL_DEV_ClientData_1 | Txt-A-Check |

  #Below for  updates the pay dates from Help desk (update loan amount from help desk) MLL
  #@TextACheckdemo @Regressiondemo
  @HelpdeskupdateloanMLL @regression @CheckApp @data123 @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_updates the pay dates from Help desk
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    #    And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
    Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And Review App verify process txtacheck
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    And In Review App Search by Email_Address
    And update the pay date process start from Helpdesk
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    And In Review App Search by Email_Address
    # And Receive Mail checkno notavailable
    #Then schedule TxtACheck and Send Mail
    And Logout of LMS Page

    #Then perform TextaCheck Process
    # Then perform Second TextaCheck Process
    # Then user is on LMS homepage
    # When user enters login details in LMS for MLL Trust
    # And In Review App Search by Email_Address
    #And Review App verify process txtacheck
    Examples: 
      | FileName             | PaymentMode |
      | MLL_DEV_ClientData_1 | Txt-A-Check |

  #Below for txtAcheck to ACH testcase for MLL
  #@TextACheckdemo @Regressiondemo
  @TxtAchecktoACHMLL @regression @CheckApp @done @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_updates txtAcheck to ACH
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    #   And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
    Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And choose payment method ACH
    And Logout of LMS Page
    When user is on MLL homepage
    And user enters login details for loan in MLL
    And Create Password for ssn Information for MLL
    Then Apply Contact Consent
    And Apply paymentmode with EFT
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    And In Review App Search by Email_Address
    When Payment method is ACH then DebitMethodId is 1
    And Review App verify process txtacheck

    Examples: 
      | FileName             | PaymentMode |
      | MLL_DEV_ClientData_1 | Txt-A-Check |

  #Below for ACH to txtAcheck testcase
  #@TextACheckdemo @Regressiondemo
  @ACHtoTxtAcheckMLL @regression @CheckApp @NotDone @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_updates ACH to txtAcheck
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    #    And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
    Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply paymentmode with EFT
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And choose payment method EFT
    And Logout of LMS Page
    When user is on MLL homepage
    And user enters login details for loan in BTL
    And Create Password for ssn Information for MLL
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    And In Review App Search by Email_Address
    When Payment method is ACH then DebitMethodId is 1
    And Review App verify process txtacheck

    Examples: 
      | FileName             | PaymentMode              |
      | MLL_DEV_ClientData_1 | Electronic Fund Transfer |

  ################# Maxlend CASES Gautam delete scenario #########
  #@TextACheckdemo @Regressiondemo
  @ABAaccountchangesPendingStatusMLL @regression @data @done @TxtACheckRegression
  Scenario Outline: MLL_PendingStatus_ABA_ACCOUNT_changes
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
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And Update Customer Maintenance Bank_Information
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address

    #And Receive Mail checkno notavailable
    #Then perform Second TextaCheck Process Accountno change
    #Then user is on LMS homepage
    #When user enters login details in LMS for MLL Trust
    #Then verify login to TRANS LMS is successful
    #And In Review App Search by Email_Address
    #Then originate process start
    #And Logout of LMS Page
    #Then user is on LMS homepage
    #When user enters login details in LMS for MLL Trust
    #Then verify login to TRANS LMS is successful
    #And In Review App Search by Email_Address
    Examples: 
      | FileName             | PaymentMode |
      | MLL_DEV_ClientData_1 | Txt-A-Check |

  #TextACheckdemoMLL @RegressiondemoMLL
  #@TextACheckdemo @Regressiondemo
  @ABAaccountchangesOriginateStatusMLL @data @done @TxtACheckRegression
  Scenario Outline: MLL_OriginateStatus_ABA_ACCOUNT_changes
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
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And verify TxtACheck Complete
    #  Then originate process start
    And Originate Application for TxtACheck for Max Lend #test the change
    And Update Customer Maintenance Bank_Information
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address

    #And Receive Mail checkno notavailable
    #Then perform Second TextaCheck Process Accountno change
    #Then user is on LMS homepage
    #When user enters login details in LMS for MLL Trust
    #Then verify login to TRANS LMS is successful
    #And In Review App Search by Email_Address
    Examples: 
      | FileName             | PaymentMode |
      | MLL_DEV_ClientData_1 | Txt-A-Check |

  #@TextACheckdemo @Regressiondemo
  @PayrollchangesOriginateMLL @data @done @TxtACheckRegression
  Scenario Outline: MLL_Payroll_changes
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
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    #  Then originate process start
    And Originate Application for TxtACheck for Max Lend #test the change
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    And In Review App Search by Email_Address
    And Update Customer Maintenance Payroll_Information
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    And In Review App Search by Email_Address

    Examples: 
      | FileName             | PaymentMode |
      | MLL_DEV_ClientData_1 | Txt-A-Check |

  @PayrollchangespendingstatusMLL @regression @data1234 @NotDone @TxtACheckRegression
  Scenario Outline: MLL_Payroll_changes_pending status
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
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    #And Logout of LMS Page
    Then perform TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And Update Customer Maintenance Payroll_Information
    And Logout of LMS Page
  #  Then user is on LMS homepage
  #  When user enters login details in LMS for MLL Trust
  #  And In Review App Search by Email_Address
  #  And Receive Mail checkno notavailable
  #  Then perform Second TextaCheck Process
  #  Then user is on LMS homepage
  #  When user enters login details in LMS for MLL Trust
  #  Then verify login to TRANS LMS is successful
   # And In Review App Search by Email_Address

    #Then originate process start
    Examples: 
      | FileName             | PaymentMode |
      | MLL_DEV_ClientData_1 | Txt-A-Check |

  @AdditionalpaymentusingcashmodeMLL @regression @data @CheckApp123 @done @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    # And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
    Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And verify TxtACheck Complete
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    #  Then originate process start
    And Originate Application for TxtACheck for Max Lend #test the change
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then Additional payment process payment type for CASH

    Examples: 
      | FileName             | PaymentMode |
      | MLL_DEV_ClientData_1 | Txt-A-Check |

  @AdditionalpaymentusingcheckmodeMLL @data @CheckApp @done @TxtACheckRegression
  Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    #  And Check try with Another lender "<FileName>" , "<PaymentMode>" , Employed, DirectDeposit
    Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then schedule TxtACheck and Send Mail
    And Logout of LMS Page
    Then perform Second TextaCheck Process
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    And verify TxtACheck Complete
    And Logout of LMS Page
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    # Then originate process start
    And Originate Application for TxtACheck for Max Lend #test the change
    Then user is on LMS homepage
    When user enters login details in LMS for MLL Trust
    Then verify login to TRANS LMS is successful
    And In Review App Search by Email_Address
    Then Additional payment process payment type for check

    Examples: 
      | FileName             | PaymentMode |
      | MLL_DEV_ClientData_1 | Txt-A-Check |

  #### Checking Try With Another Lender
  @createOriginateBTL @CreateAndOriginateApplication
  Scenario Outline: BTL_Create and Originate application
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
    And In Review App Search by Email_Address
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
    Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
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

  # create loan with pending status
  @createLoanBTL @CreateWithPendingStatusAndEsig
  Scenario Outline: BTL_Create and Originate application
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
    Then check try with another lender for MLL with "<FileName>" and Employed with DirectDeposit "<PaymentMode>"
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
