# Test Method of Subfunction Unit Test (Web)

## Exclusive control error

1. Start the app in the IDE
1. Start Chrome (hereinafter referred to as browser A).
1. Select "New incognito window" from the menu of Browser A and start another Chrome window (hereinafter referred to as Browser B).
   Two browsers are opened to prevent sessions from being shared between browsers. 
1. Both browser A and browser B will transition to the update screen with the same data.
1. Confirm with Browser B.
1. Confirm that the screen transitions to a screen indicating that an exclusive control error has occurred when you press Enter on Browser A.

## Duplicate form submission error

1. Start the app in the IDE
1. Start the browser and transition to the screen to be tested.
1. Click the button you want to test in the browser.
1. Return with the back function of the browser.
1. Click the button to test. 
   Since the same token as 2 is sent, you can confirm the duplicate form submission error.


## "Message display (by item)" test

The test of "Unit test specification" → "Subfunction unit (1)" → "Message display (by item)" checks whether the error message is displayed at the appropriate position.    
Variations of errors are not tested for the following reasons.

- Since "if the correct domain is assigned, an appropriate error message will be issued", variations of error is not required to be tested.  
  The review of the form class ensures that the appropriate domain is granted. 
- By checking each item and error message one by one, you can check whether the validation is working.

The correlation check is performed by a unit test.
