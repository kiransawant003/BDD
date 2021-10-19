Feature: Perform Start of Day and End of day

  Background: Copy the feature file
    Given feature file is SOD_EOD

  @SOD
  Scenario Outline: Perform SOD Process for blue trust
    When user gets the data from database "<FileName>"
    Then user checks if return is available or not for blue trust
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then user clicks on trans Mgr then the Batch Extension
    Then user enters batch renewal details for blue trust and process the application

    Examples: 
      | FileName |
      | BTL_QA   |

  @SOD
  Scenario Outline: Perform SOD Process for Max Lend
    When user gets the data from database "<FileName>"
    Then user checks if return is available or not for Max Lend
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    Then user clicks on trans Mgr then the Batch Extension
    Then user enters batch renewal details for Max lend and process the application

    Examples: 
      | FileName |
      | MLL_QA   |

  @EOD
  Scenario: Perform EOD Process for Blue Trust
  Given merchant is Blue Trust
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then user clicks on trans Mgr then the Batch Extension
    Then user selects all the application number to extend and originate
    When user is on LMS homepage
    Then user enters login details in LMS for Blue Trust
    And user clicks on trans Mgr and then ACH Process 3.0
    Then user selects the debit and clicks on search
    And user updates the ACH Date
    And user clicks on trans Mgr and then ACH Process 3.0
    Then user selects the credit and clicks on search

  @EOD
  Scenario: Perform EOD Process for Max Lend
  Given merchant is Max Lend
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    Then user clicks on trans Mgr then the Batch Extension
    Then user selects all the application number to extend and originate
    When user is on LMS homepage
    Then user enters login details in LMS for Max Lend
    And user clicks on trans Mgr and then ACH Process 3.0
    Then user selects the debit and clicks on search
    And user updates the ACH Date
    And user clicks on trans Mgr and then ACH Process 3.0
    Then user selects the credit and clicks on search

  
