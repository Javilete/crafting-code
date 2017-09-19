# BANK - KATA

Create a simple bank application with the following features:
 - Deposit into Account
 - Withdraw from an Account
 - Print a bank statement to the console

Print statement should print in the following format:
    DATE    | AMOUNT  | BALANCE
 10/04/2014 | 500.00  | 1400.00
 02/04/2014 | -100.00 | 900.00
 01/04/2014 | 1000.00 | 1000.00

## SOLUTION ##
The main goal of this problem is to practice **outside-in TDD** and its **double loop**. What is it?
It basically consists of an outter loop that covers what would be our acceptance test and the inner loop
will cover the unit test of the different components that are needed to satisfy the solution.
This double loop works going in and out of the loops till our acceptance test passes and is green.
The process to follow in the loops are **red -> green -> refactor**. This is the basic process to follow
when doing TDD.
The first step to resolve this problem is to create our **acceptance test** (I prefe to call it **guiding test** as we are going to see guides you through the implementation).
During the creation of the acceptance test compilation errors have led us to create classes, methods, etc. and once all the compilation errors are resolve, when the test runs obviously it fails. This is the first step and where everything starts. One habit that I tend to do is that when new methods are created I fill their body with the following statement 
*throw new UnsupportedOperationException()*.
This will guide me to see what is the next component that needs to be unit tested. When our acceptance test fails due to that exception it is telling us to go to the inner loop and unit test that component or class. This is the case when *AccountServiceShould* class is created. During the implementation of the functionality for *AccountService* a design decision had to be made. This service is going to need a collaborator called *Repository*, but on one hand a unique operation would be sufficient to cover both deposit and withdraw or it would be better to have one per each and in repository
decide the - or + for the amount depending on the operation? For clarity, two different operations
under the *LocalTransactionRepository* have been created.
Once this class has been unit tested (**red -> green -> refactor**) we come out to the outter loop and
execute again the acceptance test. In this case, our helper exception will tell us at which component it is failing and that should be next to implement.
*LocalTransactionRepository* class is the next. Our first test is "create_a_deposit".
At this stage, we need to take another decision regarding the *Transaction* entity. Should it just contain
amount and date instance variables, or should running balance be added as well? For this case, it has been decided
to have only amound and date and running balance will be calculated on the fly. Another decision to be made is regarding the dates when a transaction happens (deposit or withdraw). As it can not be controlled, it is random depending on the day. A collaborator needs to have the responsability of providing the behaviour. In this way, it will be controlled (mock) during testing.
Once the *LocalTransactionRepository.java* has been implemented following the *red -> green -> refactor*
process our acceptance test is re-run and in this case the *StatementPrinter* is the next collaborator that is throwing our UnsupportedOperationException.
We go again to the inner loop and implement the functionality of this component. In this step private methods have been added in the class to make it clear each step of printing.
After this, back to the outter loop, the acceptance test is run and the test passes, is it neccesary to do some refactoring? That would be our last step in each iteration of the loops, both inner and outer.
It is important to highlight the following points:
- Every time a component had to be unit tested, their collaborators were mocked (mockito framework).
- Things that can not be controlled (time) must be provided for a specific component so it can be controlled (mock) during testing.
- Collaborators can be added to a single component and grow quickly (fan-out). For example,
Clock could have been added to the AccountService to calculate the date at that level but that would
have been the third one which it is ok but looks a bit too much, so *LocalTransictionRepository* seemed a better place when the transactions are created.






