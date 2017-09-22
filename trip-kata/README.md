## TRIP-KATA ##

In this kata the main goal is to practice how to refactor legacy code and what steps should be
followed to make sure existing functionality and code is not broken.
Given a legacy code, find code smells, unit tested and refactor it.

# Unit test #
The first thing to do would be to make sure that the existing code is unit tested. To do so, we should start from the shallowest branch in the code to the deepest.
Having a look at the *TripService_Original.java* class:
The first test to be written would be that it should throw *UserNotLoggedInException* when the user is null.
When trying to create a test to cover this code it is found that the class that verifies whether
the user is logged in is static. Although we could use powermockito to mock this call this is a sign
of a bad design using static classes which makes it hard to test but... how can we unit test this bit?
Here comes one of the best tricks I have seen in a while.
First of all, we extract the call to the static method to a protected one in the class.
```java
protected User getLoggedUser() {
    return UserSession.getInstance().getLoggedUser();
}
```

Secondly, we create a class in our test class that extends the TripService and the new method
it was created is override with the response that we would like to get, in this case null.

```java
public TestableTripService extends TripService {
    private static final User USER_NOT_LOGGED_IN = null;

    @Override
    protected User getLoggedUser() {
        return USER_NOT_LOGGED_IN;
    }
}
```

This little trick allows us to be able to test that bit of code.

The second test would be to test that no trips are returned when user and user logged in are not friends
For this case it is necessary to do something similar where the getLoggedUser() method needs to be
overrided with the following functionality:

```java
public class TestableTripServiceWithUser extends TripService_Original2 {
    @Override
    protected User getLoggedUser() {
        return USER_LOGGED_IN;
    }
}
```

In this case, during the test, it is verified that there is a user logged in and
as it is not friends with the user passed as param it will return no trips.

Our last and third test would be the deepest branch in our code. This is
the check whether the user passed as a param is friend with the logged in user
and if so, return the trips for that user.
As the call to the DAO service is doing using static context it is neccesary
to do the same "trick" as before, the call is extracted to a protected method.

```java
protected List<Trip> getTripsByUserPassed(User user) {
    return TripDAO.findTripsByUser(user);
}
```

Then, in our testable class we can override the method and return whatever we want
avoiding the call to the static class, basically, as if we were mocking our
collaborator (DAO service). Our *TestableTripServiceWithUser* class will look like following:

```java
public class TestableTripServiceWithUser extends TripService_Original2 {
	@Override
	protected User getLoggedUser() {
	    return USER_LOGGED_IN;
	}

	@Override
	protected List<Trip> getTripsByUserPassed(User user) {
	    return Collections.singletonList(LONDON);
	}
}
```

Once the existing code has been covered 100% we move to the refactoring phase.

# Refactoring #
In this phase the process is the other way around: **from deepest to shallowest branch**.

The first thing to look at is the branch about checking whether user passed
as parameter is friend with the one logged in. Basically, here we can apply the
tell don't ask practice where the logic is together with the model as it tell us
information about the data it contains. To come up with the following method unit test
is done first (obvioulsy) in the *UserShould.java* class.

```java
public boolean isFriendWith(User anotherUser) {
    return friends.contains(anotherUser);
}
```

Next step would be to remove the call to get the user which is logged in.
At this level in the stack the current user logged in should be passed as
a parameter when calling the service. It should not be part of the body of the
service method to get what user is logged in.

At this stage we can bring the "guarding" condition of the loggedUser at the top
of the method, so if it is null, the exception will be thrown, otherwise,
trips will be returned depending on whether user and userLoggedIn are friends.

Our collaborator which is TripDao should no longer be static. As
we do not know where the static method is used and to avoid breaking the code
in several places, what we can do is create a public method inside the TripDao class
that will call the static one. This public method will be the one that can be mocked
in our trip service.

```java
    public List<Trip> tripsBy(User user) {
        return TripDAO.findTripsByUser(user);
    }
```

Like this we can gurantee that wherever the static method is used it is not breaking.
When we run our test obviously it fails as it is still executing the real code of the
DAO but as we are using it as instance variable we can mock it wherever it is
necessary in our test.

After all of these step, the protected methods are not longer used and they
can be removed but thank to them we have been able to unit test the existing code
and little by little improving it.
We can conclude our refactoring and the solution looks
much better than before with the corresponding unit tests covering it.

```java
    private TripDAO tripDAO;

	public TripService_Original2(TripDAO tripDAO) {
		this.tripDAO = tripDAO;
	}

	public List<Trip> getTripsByUser(User user, User loggedUser) throws UserNotLoggedInException {
		if(loggedUser == null) {
			throw new UserNotLoggedInException();
		}

		return user.isFriendWith(loggedUser) ?
				tripDAO.tripsBy(user):
				noTrips();
	}

	private List<Trip> noTrips() {
        return Collections.EMPTY_LIST;
    }
```



