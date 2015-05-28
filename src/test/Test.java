package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import manager.TestManager;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import util.ActivitiUtils;

public class Test {
	//test
	private static ProcessEngine processEngine = ActivitiUtils.getProcessEngine();
	private TestManager testManager = new TestManager();

	public static void main(String[] args) {
//		testDeploy();
//		startInstance();
//		List<Task> tasks = queryTask1("u1");
		
//		List<Task> tasks = ActivitiUtils.getTasksByProcessInstanceBusinessKey(processEngine.getTaskService(), "zbctest02");
//		for(Task task : tasks){
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("close", true);
//			ActivitiUtils.claimAndFinishTaskWithParams(processEngine.getTaskService(), task.getId(), "u1", params);
//			System.out.println(task.getId() + "--" + task.getName() + " get completed");
//		}
	}
	
	public static void testDeploy(){
		Deployment deployment = ActivitiUtils.deploy("test.bpmn", processEngine.getRepositoryService());
		System.out.println(deployment.getId() + ", " + deployment.getName() + ","  + deployment.getDeploymentTime());
	}
	public static void startInstance(){
		IdentityService is = processEngine.getIdentityService();
		is.setAuthenticatedUserId("u1");
		Map<String, Object> params = new HashMap<String, Object>();
		ActivitiUtils.startInstanceWithParams(processEngine.getRuntimeService(), "test", "zbctest02", params);
	}
	public static void queryInstance(){
		//All Active
		List<ProcessInstance> list = ActivitiUtils.getAllActiveProcessInstance(processEngine);
		//¶¨µã²éÑ¯
		list = ActivitiUtils.getProcessInstanceByBusinessKey(processEngine.getRuntimeService(), "zbctest01");
		for(ProcessInstance processInstance : list){
			System.out.println(processInstance.getId());
		}
	}

	public static List<Task> queryTask1(String userId){
		TaskService tss = processEngine.getTaskService();
		IdentityService is = processEngine.getIdentityService();
		is.setAuthenticatedUserId("u1");
		//start by. should set this before ProcessInstance is started, useless for query tasks
		
		List<Task> tasks = tss.createTaskQuery().processInstanceBusinessKey("zbctest01").taskCandidateUser(userId).list();
		for(Task task: tasks){
			System.out.println(task.getId() + " -- " + task.getName());
		}
		return tasks;
	}
}
