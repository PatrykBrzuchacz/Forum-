package Main.model.form;

import java.util.ArrayList;
import java.util.Collections;

import Main.model.User;

public class UserSorter {
	 ArrayList<User> user = new ArrayList<>();
	 
	    public UserSorter(ArrayList<User> user) {
	        this.user=user;
	    }
	public ArrayList<User> sortByName() {
        Collections.sort(user, User.nameComparator);
        return user;
    }
}
