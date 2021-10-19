Feature: TxtACheck  additional payment
#Below if for Additional payment made BTL (Manual Extension Regressio for TxtAcheck delete scenario)
@AdditionalpaymentBTLchangenoadd12
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Txt-A-Check|


#Manual Extension test cases for Electronic Fund Transfer(ACH)
#Below Est Effective date  < Due date of the loan, Payment Amount > Schedule amt , using ACH 
@AdditionalpaymentBTL1
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment type method is ACH
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
And Additional payment process payment method for ACH
#Below new line added
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
And Review App verify process txtacheck
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Electronic Fund Transfer|



#Below Est Effective date  < Due date of the loan ,Payment Amount = Schedule amt ,using ACH
@AdditionalpaymentBTLtest2
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment payment type method is ACH
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for ACH and pay amount equal schedule amount
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Electronic Fund Transfer|



#Below Est Effective date  < Due date of the loan,Payment Amount > Schedule amt, using Cash
@AdditionalpaymentBTLtest3
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for CASH 
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Electronic Fund Transfer|
 

#Below Est Effective date  < Due date of the loan,Payment Amount = Schedule amt,  using Cash
@AdditionalpaymentBTL4
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for CASH and pay amount equal schedule amount
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Electronic Fund Transfer|


#Below EST effectve date =Due date of loan,paymnet amount>scheduled amount,using cash
@AdditionalpaymentBTLtest5
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for CASH and Est Effective date equal due date of loan
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Electronic Fund Transfer|


#Below EST effectve date =Due date of loan,paymnet amount>scheduled amount,using ACH
@AdditionalpaymentBTL6
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for ACH and Est Effective date equal due date of loan
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Electronic Fund Transfer|



#Below EST effectve date =Due date of loan,paymnet amount=scheduled amount,using cash
@AdditionalpaymentBTL7
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for cash and Est Effective date equal due date of loan and Payment amount equal Schedule amount
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Electronic Fund Transfer|


#Below EST effectve date =Due date of loan,paymnet amount=scheduled amount,using ACH
@AdditionalpaymentBTLtestdemo8
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment Effective date equal due date of loan, Payment amount =  Schedule amount and paymode =ACH
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for ACH and Est Effective date equal due date of loan and Payment amount equal Schedule amount
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Electronic Fund Transfer|


#***********************************************************************************************************************************************
#********************************************************************************************************************************************
#						Manual Extension test cases for Txt-A-Check BTL


#Below if for Additional payment made BTL (Manual Extension Regressio for TxtAcheck delete scenario)
@AdditionalpaymentBTL9
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Txt-A-Check|


#Manual Extension test cases for Electronic Fund Transfer(ACH)
#Below Manual Extension Regressio for TxtAcheck Manual extensio scenario  payment type method=ACH)

#Est Effective date  < Due date of the loan, Payment Amount > Schedule amt , using ACH 
@AdditionalpaymentBTL10
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment type method is ACH
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
#Then Additional payment process
And Additional payment process payment method for ACH
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Txt-A-Check|


#Below .If the PMT Amt == Schedule amt before the due and paymode =ACH

#Est Effective date  < Due date of the loan ,Payment Amount = Schedule amt ,using ACH
@AdditionalpaymentBTL11
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment payment type method is ACH
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for ACH and pay amount equal schedule amount
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Txt-A-Check|



#Below if for Additional payment made BTL (Manual Extension Regressio for payment type method=CASH)
#***Est Effective date  < Due date of the loan,Payment Amount > Schedule amt, using Cash
@AdditionalpaymentBTL12
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for CASH 
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Txt-A-Check|
 
#Below 
#Est Effective date  < Due date of the loan,Payment Amount = Schedule amt,  using Cash
@AdditionalpaymentBTL13
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for CASH and pay amount equal schedule amount
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Txt-A-Check|


#Below EST effectve date =Due date of loan,paymnet amount>scheduled amount,using cash
@AdditionalpaymentBTL14
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for CASH and Est Effective date equal due date of loan
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Txt-A-Check|


#Below EST effectve date =Due date of loan,paymnet amount>scheduled amount,using ACH
@AdditionalpaymentBTL15
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for ACH and Est Effective date equal due date of loan
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Txt-A-Check|



#Below EST effectve date =Due date of loan,paymnet amount=scheduled amount,using cash
@AdditionalpaymentBTL16
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for cash and Est Effective date equal due date of loan and Payment amount equal Schedule amount
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Txt-A-Check|


#Below EST effectve date =Due date of loan,paymnet amount=scheduled amount,using ACH
@AdditionalpaymentBTL17
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment Effective date equal due date of loan, Payment amount =  Schedule amount and paymode =ACH
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for Blue Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for ACH and Est Effective date equal due date of loan and Payment amount equal Schedule amount
Examples:
|FileName|PaymentMode|
|BTL_DEV_ClientData_1|Txt-A-Check|


#*******************Below Manaul Extension for Maxlend side *******************************************************************************
#*****************************************************************************************************************************************
#****************************  Manual Extension test cases for ACH(Electronic Fund Transfer) for maxlend below  **************************************************  
@AdditionalpaymentMLL1change12345
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for MLL Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process
Examples:
|FileName|PaymentMode|
|MLL_DEV_ClientData_1|Txt-A-Check|


#Manual Extension test cases for Electronic Fund Transfer(ACH)
#Below Manual Extension Regressio for TxtAcheck Manual extensio scenario  payment type method=ACH)
#If the Pmt Amt > Schedule amt before the due date
@AdditionalpaymentMLL2
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment type method is ACH
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for MLL Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
#Then Additional payment process
And Additional payment process payment method for ACH
Examples:
|FileName|PaymentMode|
|MLL_DEV_ClientData_1|Electronic Fund Transfer|


#Below .If the PMT Amt == Schedule amt before the due and paymode =ACH
@AdditionalpaymentMLL3
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment payment type method is ACH
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for MLL Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for ACH and pay amount equal schedule amount
Examples:
|FileName|PaymentMode|
|MLL_DEV_ClientData_1|Electronic Fund Transfer|



#Below if for Additional payment made BTL (Manual Extension Regressio for payment type method=CASH)
#If the Pmt Amt > Schedule amt before the due date
@AdditionalpaymentMLL4
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for MLL Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for CASH 
Examples:
|FileName|PaymentMode|
|MLL_DEV_ClientData_1|Electronic Fund Transfer|
 
#Below .If the PMT Amt == Schedule amt before the due and paymode =cash
@AdditionalpaymentMLL5
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for MLL Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for CASH and pay amount equal schedule amount
Examples:
|FileName|PaymentMode|
|MLL_DEV_ClientData_1|Electronic Fund Transfer|


#Below Est Effective date = due date of loan,Payment amount >  Schedule amount and paymode =cash
@AdditionalpaymentMLL6
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for MLL Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for CASH and Est Effective date equal due date of loan
Examples:
|FileName|PaymentMode|
|MLL_DEV_ClientData_1|Electronic Fund Transfer|



#Below Est Effective date = due date of loan, Payment amount >  Schedule amount and paymode =ACH
@AdditionalpaymentMLL7
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for MLL Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for ACH and Est Effective date equal due date of loan
Examples:
|FileName|PaymentMode|
|MLL_DEV_ClientData_1|Electronic Fund Transfer|




#Below Est Effective date = due date of loan, Payment amount =  Schedule amount and paymode =cash
@AdditionalpaymentMLL8
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for MLL Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for cash and Est Effective date equal due date of loan and Payment amount equal Schedule amount
Examples:
|FileName|PaymentMode|
|MLL_DEV_ClientData_1|Electronic Fund Transfer|


#Below Est Effective date = due date of loan, Payment amount =Schedule amount and paymode =ACH
@AdditionalpaymentMLL9
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment Effective date equal due date of loan, Payment amount =  Schedule amount and paymode =ACH
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for MLL Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for ACH and Est Effective date equal due date of loan and Payment amount equal Schedule amount
Examples:
|FileName|PaymentMode|
|MLL_DEV_ClientData_1|Electronic Fund Transfer|


#***********************************************************************************************************************************************
#********************************************************************************************************************************************
#                         Manual Extension test cases for Txt-A-Check for maxlend below

#Below Manual Extension Regressio for TxtAcheck Manual extensio scenario  payment type method=ACH)
#If the Pmt Amt > Schedule amt before the due date
@AdditionalpaymentMLL10
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment type method is ACH
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for MLL Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
#Then Additional payment process
And Additional payment process payment method for ACH
Examples:
|FileName|PaymentMode|
|MLL_DEV_ClientData_1|Txt-A-Check|


#Below .If the PMT Amt == Schedule amt before the due and paymode =ACH
@AdditionalpaymentMLL11
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment payment type method is ACH
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for MLL Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for ACH and pay amount equal schedule amount
Examples:
|FileName|PaymentMode|
|MLL_DEV_ClientData_1|Txt-A-Check|



#Below if for Additional payment made BTL (Manual Extension Regressio for payment type method=CASH)
#If the Pmt Amt > Schedule amt before the due date
@AdditionalpaymentMLL12
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for MLL Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for CASH 
Examples:
|FileName|PaymentMode|
|MLL_DEV_ClientData_1|Txt-A-Check|
 
#Below .If the PMT Amt == Schedule amt before the due and paymode =cash
@AdditionalpaymentMLL13
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for MLL Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for CASH and pay amount equal schedule amount
Examples:
|FileName|PaymentMode|
|MLL_DEV_ClientData_1|Txt-A-Check|


#Below Est Effective date = due date of loan,Payment amount >  Schedule amount and paymode =cash
@AdditionalpaymentMLL14
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on BTL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for MLL Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for CASH and Est Effective date equal due date of loan
Examples:
|FileName|PaymentMode|
|MLL_DEV_ClientData_1|Txt-A-Check|



#Below Est Effective date = due date of loan, Payment amount >  Schedule amount and paymode =ACH
@AdditionalpaymentMLL15
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for MLL Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for ACH and Est Effective date equal due date of loan
Examples:
|FileName|PaymentMode|
|MLL_DEV_ClientData_1|Txt-A-Check|




#Below Est Effective date = due date of loan, Payment amount =  Schedule amount and paymode =cash
@AdditionalpaymentMLL16
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for MLL Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for cash and Est Effective date equal due date of loan and Payment amount equal Schedule amount
Examples:
|FileName|PaymentMode|
|MLL_DEV_ClientData_1|Txt-A-Check|


#Below Est Effective date = due date of loan, Payment amount =Schedule amount and paymode =ACH
@AdditionalpaymentMLL17
Scenario Outline: Manual Extension Functional testing for TextAcheck_Additional payment Effective date equal due date of loan, Payment amount =  Schedule amount and paymode =ACH
Given user load configuration file "<FileName>"
When user fetch the information
Then user is on MLL homepage
And Specify information on AboutYou Page
And Specify information on Income Page for Employed with DirectDeposit
And Specify information on Banking Page using "<PaymentMode>"
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
Then originate process start
Then user is on LMS homepage
When user enters login details in LMS for MLL Trust
Then verify login to TRANS LMS is successful
And In Review App Search by Email_Address
Then Additional payment process payment type for ACH and Est Effective date equal due date of loan and Payment amount equal Schedule amount
Examples:
|FileName|PaymentMode|
|MLL_DEV_ClientData_1|Txt-A-Check|






