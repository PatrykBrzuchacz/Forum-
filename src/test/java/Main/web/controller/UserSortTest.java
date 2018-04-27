package Main.web.controller;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Main.model.User;
import Main.model.form.UserSorter;

public class UserSortTest {

	 UserSorter userSorter;
	 
	    @Before
	    public void setUp() throws Exception {
	        User user1 = new User("Bark", "Smith", "user@interia.pl","qwerty123");
	        User user2 = new User("Aark2", "Smith2", "user2@interia.pl","2qwerty123");
	        User user3 = new User("Dark3", "Smith3", "user3@interia.pl","3qwerty123");
	        User user4 = new User("Cark4", "Smith4", "user4@interia.pl","4qwerty123");
	        ArrayList<User> UserList = new ArrayList<>();
	        UserList.add(user1);
	        UserList.add(user2);
	        UserList.add(user3);
	        UserList.add(user4);
	        userSorter = new UserSorter(UserList);
	    }
	 
	    @Test
	    public void testGetSortedUserByName() throws Exception {
	        System.out.println("-----Sorted User by name: Ascending-----");
	        ArrayList<User> sortedUser = userSorter.sortByName();
	        for (User User : sortedUser) {
	            System.out.println(User);
	        }}}