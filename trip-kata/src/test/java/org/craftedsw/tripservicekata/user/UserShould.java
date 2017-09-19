package org.craftedsw.tripservicekata.user;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class UserShould {

    private static final User KNOWN_USER = new User();
    private static final User UNKNOWN_USER = new User();
    private User user;

    @Test
    public void be_a_friend_of_user_provided() {
        user = User.UserBuilder
                .aUser()
                .withFriends(KNOWN_USER)
                .build();

        assertTrue(user.isFriendWith(KNOWN_USER));
    }

    @Test
    public void not_be_a_friend_of_user_provided() {
        user = User.UserBuilder
                .aUser()
                .build();

        assertFalse(user.isFriendWith(UNKNOWN_USER));
    }
}
