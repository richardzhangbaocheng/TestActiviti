package util;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

public class ActivitiUtils {
	public static ProcessEngine getProcessEngine(){
		return getProcessEngine("activiti.xml");
	}
	public static ProcessEngine getProcessEngine(String cfgFileName){
		ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(
				cfgFileName).buildProcessEngine(); 
		return processEngine;
	}
	public static List<Deployment> getAllDeployments(ProcessEngine processEngine){
		List<Deployment> list = new ArrayList<Deployment>();
		list = processEngine.getRepositoryService().createDeploymentQuery().list();
		return list;
	}
	public static List<ProcessInstance> getAllActiveProcessInstance(ProcessEngine processEngine){
		List<ProcessInstance> list = new ArrayList<ProcessInstance>();
		list = processEngine.getRuntimeService().createProcessInstanceQuery().active().list();
		return list;
	}
	public static List<HistoricActivityInstance> getAllHistoryProcessInstance(ProcessEngine processEngine){
		List<HistoricActivityInstance> list = new ArrayList<HistoricActivityInstance>();
		list = processEngine.getHistoryService().createHistoricActivityInstanceQuery().list();
		return list;
	}
	public static RepositoryService getRepositoryService(ProcessEngine processEngine){
		return processEngine.getRepositoryService();
	}
	public static List<ProcessInstance> getProcessInstanceByBusinessKey(RuntimeService runtimeService, String businessKey){
		return runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).list();
	}
	public static RuntimeService getRuntimeService(ProcessEngine processEngine){
		return processEngine.getRuntimeService();
	}
	public static Deployment deploy(String processName, RepositoryService repositoryService){
		return deploy(processName, processName, repositoryService);
	}
	
	public static Deployment deploy(String processName, String deploymentName, RepositoryService repositoryService){
		Deployment deployment = repositoryService.createDeployment()
				  .addClasspathResource(processName).name(deploymentName).deploy();
		return deployment;
	}
	public static Deployment deployZip(String zipName, String deploymentName, RepositoryService repositoryService){
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("diagrams/"+zipName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		Deployment deployment = repositoryService.createDeployment().addZipInputStream(zipInputStream).name(deploymentName).deploy();
		return deployment;
	}
	
	public static ProcessInstance startInstance(RuntimeService runtimeService, String key){
		return runtimeService.startProcessInstanceByKey(key);
	}
	public static ProcessInstance startInstanceWithParams(RuntimeService runtimeService, String processDefinitionKey, String businessKey, Map<String, Object> params){
		return runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, params);
	}
	public static TaskService getTaskService(ProcessEngine processEngine){
		return processEngine.getTaskService();
	}
	public static List<Task> getAllTasks(TaskService taskService){
		return taskService.createTaskQuery().list();
	}
	public static List<Task> getTasksByTaskDefinitionKey(TaskService taskService, String taskDefinitionKey){
		return taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey).list();
	}
	public static List<Task> getTasksByProcessInstanceBusinessKey(TaskService taskService, String processInstanceBusinessKey){
		return taskService.createTaskQuery().processInstanceBusinessKey(processInstanceBusinessKey).list();
	}
	public static List<Task> getTasksByProcessInstanceID(TaskService taskService, String processInstanceId){
		return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
	}
	public static void claimAndFinishTask(TaskService taskService, String taskId, String userId){
		taskService.claim(taskId, userId);
		taskService.complete(taskId);
	}
	public static void claimAndFinishTaskWithParams(TaskService taskService, String taskId, String userId, Map<String, Object> paramMap){
		taskService.claim(taskId, userId);
		taskService.complete(taskId, paramMap);
	}
	public static void claimTask(TaskService taskService, String taskId, String userId){
		taskService.claim(taskId, userId);
	}
	public static void finishTask(TaskService taskService, String taskId){
		taskService.complete(taskId);
	}
}
