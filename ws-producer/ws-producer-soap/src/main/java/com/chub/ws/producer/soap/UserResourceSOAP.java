package com.chub.ws.producer.soap;

import com.chub.ws.producer.model.User;
import com.chub.ws.producer.service.UserService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

/**
 * Created by vsafronovici on 11/7/2016.
 */
@WebService(serviceName = "UserSoap")
public class UserResourceSOAP {

    @WebMethod
    public List<User> getUsers() {
        List<User> users = UserService.getUsers();
        return users;
    }

    @WebMethod
    public User getUser(int id) {
        User user = null;
        for (User u : UserService.getUsers()) {
            if (u.getId() == id) {
                user = u;
                break;
            }
        }
        return user;
    }



    @WebMethod
    public User update(User user) {
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

        return user;
    }


    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(0);
            System.out.println("UserResourceSOAP socket is running on port:" + s.getLocalPort());

            UserResourceSOAP service = new UserResourceSOAP();
            //String address = "http://172.16.37.214:" + s.getLocalPort() + "/helloWorld";
            String address = "http://localhost:9001/UserSoap";
            Endpoint.publish(address, service);

            System.out.println("UserResourceSOAP URL is: " + address + "?wsdl");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error appeared when starting web service");
        }
    }


}
