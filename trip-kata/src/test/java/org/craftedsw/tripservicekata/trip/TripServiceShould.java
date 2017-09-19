package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceShould {

    private static final User USER_NOT_LOGGED_IN = null;
    private static final User USER_LOGGED_IN = new User();
    private static final User UNKNOWN_USER = new User();
    private static final Trip MADRID = new Trip();
    private User user;

    @Mock
    private TripDAO tripDAO;

    private TripService tripService;

    @Before
    public void setUp() throws Exception {
        tripService = new TripService(tripDAO);
    }

    @Test(expected = UserNotLoggedInException.class)
    public void throw_exception_when_user_is_not_logged_in() {
        tripService.getTripsByUser(user, USER_NOT_LOGGED_IN);
    }

    @Test
    public void not_return_trips_when_users_arent_friends() {
        user = User.UserBuilder
                .aUser()
                .withFriends(UNKNOWN_USER)
                .build();

        List<Trip> trips = tripService.getTripsByUser(user, USER_LOGGED_IN);

        assertTrue(trips.isEmpty());
    }

    @Test
    public void return_all_of_friends_trips_when_they_are_friends() {
        user = User.UserBuilder.aUser()
                .withFriends(USER_LOGGED_IN)
                .withTrips(MADRID)
                .build();
        when(tripDAO.tripsBy(user)).thenReturn(user.getTrips());

        List<Trip> trips = tripService.getTripsByUser(user, USER_LOGGED_IN);

        assertThat(trips.size(), is(1));
    }
}
