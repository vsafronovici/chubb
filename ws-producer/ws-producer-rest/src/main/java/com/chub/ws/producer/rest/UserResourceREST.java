package com.chub.ws.producer.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.chub.ws.producer.model.User;
import com.chub.ws.producer.service.UserService;

@Path("/userres")
public class UserResourceREST {
	
	public UserResourceREST() {
		System.out.println("Constructor UserResourceREST");
	}
	
    @GET 
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/users")
	public List<User> getUsers() {
		List<User> users = UserService.getUsers();
		return users;
	}

    @GET 
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/users/user/{id}")
	public User getUser(@PathParam("id") int id) {
    	User user = null;
		for (User u : UserService.getUsers()) {
			if (u.getId() == id) {
				user = u;
				break;
			}
		}
		return user;
	}
    
    @GET 
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/users/userJSON/{id}")
	public User getUserJson(@PathParam("id") int id) {
    	User user = null;
		for (User u : UserService.getUsers()) {
			if (u.getId() == id) {
				user = u;
				break;
			}
		}
		return user;
	}
        
    @DELETE 
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/users/user/{id}")
	public User remove(@PathParam("id") int id) {
    	User user = null;
		for (User u : UserService.getUsers()) {
			if (u.getId() == id) {
				user = u;
				break;
			}
		}
		UserService.getUsers().remove(user);
		return user;
	}
    
    @PUT
    @Path("/users/user/edit/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response update(User user) {
    	int index = -1;
    	for (int i = 0; i < UserService.getUsers().size(); i++) {
    		User u = UserService.getUsers().get(i);
    		if (u.getId() == user.getId()) {
    			index = i;
    		}
    	}
    	if (index >= 0) {
        	UserService.getUsers().set(index, user);    		
    	} else {
    		throw new IllegalArgumentException("invalid user id");
    	}

		return Response.ok().header("token", "xyz").entity(user).build();
    }
	
    @POST
    @Path("/users/user/create")
    @Consumes({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response create(User user) {
    	int id = 0;
		for (User u : UserService.getUsers()) {
			if (u.getId() > id) {
				id = u.getId();
			}
		}
		user.setId(++id);
		UserService.getUsers().add(user);


		return Response.status(201).entity(String.valueOf(id)).build();
    }

}
