package com.chub.ws.producer.service;

import com.chub.ws.producer.model.*;

import java.util.ArrayList;
import java.util.List;

public class UserService {
	
	private static List<User> USERS = new ArrayList<User>();
	static {
		System.out.println("===== UserService.init =====");


		for (int i = 1; i <= 20; i++) {
			User u = new User();
			u.setId(i);
			u.setName("name" + i);
			u.setLastname("lastname" +i);
			u.setSex(i % 2 == 0 ? "M" : "F");
			u.setExtraField("extra Field" + i);
			Address address = new Address();
			address.setZip("zip" + i);
			address.setPhone("phone" + i);
			u.setAddress(address);

			u.setLevel1Field(new Level1());
			u.getLevel1Field().setLevel2Field1(new Level2());
			u.getLevel1Field().getLevel2Field1().setLevel3Field1(new Level3("f1", "f2"));
			u.getLevel1Field().getLevel2Field1().setLevel3Field2(new Level3("f1x", "f2x"));

			u.getLevel1Field().setLevel2Field2(new Level2());
			u.getLevel1Field().getLevel2Field2().setLevel3Field1(new Level3("f1y", "f2y"));
			u.getLevel1Field().getLevel2Field2().setLevel3Field2(new Level3("f1z", "f2z"));


			USERS.add(u);
		}
	}
	
	public static List<User> getUsers() {
		return USERS;
	}


}
