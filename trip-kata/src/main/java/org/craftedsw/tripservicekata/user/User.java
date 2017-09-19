package org.craftedsw.tripservicekata.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.craftedsw.tripservicekata.trip.Trip;

public class User {

	private List<Trip> trips = new ArrayList<Trip>();
	private List<User> friends = new ArrayList<User>();
	
	public List<User> getFriends() {
		return friends;
	}
	public List<Trip> getTrips() {
		return trips;
	}

	public void addFriend(User user) {
		friends.add(user);
	}

	public void addTrip(Trip trip) {
		trips.add(trip);
	}
	public List<Trip> trips() {
		return trips;
	}

	public boolean isFriendWith(User anotherUser) {
		return friends.contains(anotherUser);
	}

	public static class UserBuilder {
		private List<Trip> trips = new ArrayList<>();
		private List<User> friends = new ArrayList<>();

		public static UserBuilder aUser() {
			return new UserBuilder();
		}

		public UserBuilder withFriends(User... friends) {
			this.friends.addAll(Arrays.asList(friends));
			return this;
		}

		public UserBuilder withTrips(Trip... trips) {
			this.trips.addAll(Arrays.asList(trips));
			return this;
		}

		public User build() {
			User user = new User();
			this.friends.stream().forEach(user::addFriend);
			this.trips.stream().forEach(user::addTrip);
			return user;
		}
	}
}
