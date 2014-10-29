package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

import util.ActivitiUtils;
import manager.TestManager;

/**
 * Servlet implementation class TestListener
 */
@WebServlet("/listener")
public class TestListener extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProcessEngine processEngine = ActivitiUtils.getProcessEngine();
	@Autowired(required = false)
	private TestManager testManager;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		testManager = new TestManager();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("a", 12);
		params.put("b", 23);
		params.put("testManager", testManager);
		List<Task> tasks = ActivitiUtils.getTasksByProcessInstanceBusinessKey(processEngine.getTaskService(), "zbctest02");
		for(Task task : tasks){
			ActivitiUtils.claimAndFinishTaskWithParams(processEngine.getTaskService(), task.getId(), "u1", params);
			System.out.println(task.getId() + "--" + task.getName() + " get completed");
		}
	}

}
