package com.iris.system.model;

import com.iris.system.enums.Gender;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

/*  MODEL object / VALUE object
    to be handled by the DAO interface, retrieved by DAO class.
    have getter setter
 */

/*  Lombook @Value
    an immutable variant of @Data. All fields are private and final
    NO SETTER, only getter
    toString, equals, hashCode
 */

/*  Lombook @Builder
    Once created an object,we want it to be immutable
    (change later on and use in between will cause one object with 2 different states)
    -> help create an immutable class instance with a large set of state attributes.

    User userA = new User(name);
    userA.setEmail("...@gmail.com");
    use userA somewhere <-  cause problem
    userA.setEmail("XXX@gmail.com");

    Separate the construction of a complex object from its representation so that
    the same construction process can create multiple different representations

    -> if need different ways to create an object, we need a lot of constructors (6, 7, 8...),
    which is hard to keep.

    ------
    @Builder help us generate a static class UserBuilder inside the User class,
    which has multiple constructor and "final" data type avoid modification

    // when use, call innerclass UserBuilder
    User user1 = new User.UserBuilder().age(30).phone().address.....build();
 */

@Builder
@Value
public class User {
    int id;
    String username;
    String nickname;
    String password;
    String loginToken;
    Date registerTime;
    Date lastLoginTime;
    Gender gender;
    String email;
    String address;
    Boolean isValid;

    /*
    public static class UserBuilder
	{
		private final String firstName;
		private final String lastName;
		private int age;
		private String phone;
		private String address;

		public UserBuilder(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}
		public UserBuilder age(int age) {
			this.age = age;
			return this;
		}
		public UserBuilder phone(String phone) {
			this.phone = phone;
			return this;
		}
		public UserBuilder address(String address) {
			this.address = address;
			return this;
		}
		//Return the finally constructed User object
		public User build() {
			User user =  new User(this);
			validateUserObject(user);
			return user;
		}
		private void validateUserObject(User user) {
			//Do some basic validations to check
			//if user object does not break any assumption of system
		}
	}
     */
}
