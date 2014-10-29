package test;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;

import util.ActivitiUtils;

public class TestIdentity {
	public static void main(String[] args) {
		ProcessEngine pe = ActivitiUtils.getProcessEngine();
		IdentityService identityService = pe.getIdentityService();
		User user1 = findUser(identityService, "u1");
		User user2 = findUser(identityService, "u2");
		Group group = createGroup(identityService, "g1", "group1", "group1");
		identityService.createMembership(user1.getId(), group.getId());
		identityService.createMembership(user2.getId(), group.getId());
	}
	public static User craeteUser(IdentityService is, String id, String firstName, String lastName, String email, String password){
		User user = is.newUser(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		is.saveUser(user);
		return user;
	}
	public static Group createGroup(IdentityService is, String id, String name, String type){
		Group group = is.newGroup(id);
		group.setName(name);
		group.setType(type);
		is.saveGroup(group);
		return group;
	}
	public static Group findGroup(IdentityService is, String id){
		return is.createGroupQuery().groupId(id).singleResult();
	}
	public static User findUser(IdentityService is, String id){
		return is.createUserQuery().userId(id).singleResult();
	}
}
