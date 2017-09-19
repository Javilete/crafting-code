BANK - KATA
-----------
Create a simple bank application with the following features:
 - Deposit into Account
 - Withdraw from an Account
 - Print a bank statement to the console

Print statement should print in the following format:
    DATE    | AMOUNT  | BALANCE
 10/04/2014 | 500.00  | 1400.00
 02/04/2014 | -100.00 | 900.00
 01/04/2014 | 1000.00 | 1000.00

The main goal of this problem is to practice the double loop of TDD. What is it?
It consist of an outter loop that covers what would be our acceptance test and the inner loop
will cover the unit test of the different components that are needed to satisfy the solution.
This double loop consist basically in going in and out of the loops till our acceptance test
is green.
The process to follow in the loops are red -> green -> refactor. This is the basic process to follow
when following TDD.
The first step to resolve this problem would be to create our acceptance test (guiding test can be
called as well since as we are going to see guides you through the implementation).
During the creation of the acceptance test compilation errors have led us to create components,
methods, etc. and once all the compilation errors are resolve, when the test runs obviously it fails.
One habit that I tend to do is that when new methods are created I fill then with throw new UnsupportedOperationException().
This will guide me to see what is the component that needs to be unit tested next.
When our acceptance test fails due to that exception it is telling us that we need to go inside the
inner loop and unit test that component. This is the case when AccountServiceShould class is created.
During the implementation of the functionality for AccountService a design decision had to be made.
This service is going to need a collaborator called Repository, but once operation would be sufficient
to cover both deposit and withdraw or it would be better to have one per each and in repository
decide the - or + depending on the operation? For clarity, we have gone for two different operations
under the LocalRepository.
Once this class has been unit tested (red -> green -> refactor) we come out to the outter loop and
execute it again. In this case, our helper exception will tell us what component should be next.
In this case, it is the LocalTransactionRepository class. We create our first test "create_a_deposit".
At this stage, we need to take another decision regarding the Transaction entity. Should it contain
amount and date, or should current balance be added as well? For this case, it has been decided
to have only amound and date. Another issue is encounter for the date, it can not be controlled, it is
random depending on the current day. A collaborator needs to take care of it so the behaviour can
be controlled (mock) during testing.
Once the LocalTransactionRepository.java has been implemented following the red -> green -> refactor
process we re-run our acceptance test and in this case the StatementPrinter is the next collaborator.
We go again to the inner loop and implement the functionality of this component.
After this, the acceptance test is run and the found that we are at the green step, so we move
to the refactoring phase in the outter loop.
It is important to highlight the following:
- Every time a component had to be unit tested, their collaborators
were mocked.
- Collaborators can be added to a single component and grow quickly (fan-out). For example,
Clock could have been added to the AccountService to calculate the date at that level but that would
have been three, so LocalTransictionRepository seemed a better place when the transactions are
created.






