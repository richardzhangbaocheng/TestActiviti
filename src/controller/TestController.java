package controller;

import java.awt.image.RasterFormatException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import util.ActivitiUtils;

@WebServlet("/test")
public class TestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ProcessEngine processEngine = ActivitiUtils.getProcessEngine();
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		 RuntimeService runtimeService = processEngine.getRuntimeService();
		 RepositoryService repositoryService = processEngine.getRepositoryService();
		 
		 ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("test").orderByProcessDefinitionVersion().desc().list().get(0);
		 ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionId(processDefinition.getId()).singleResult();
		 BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		 
         InputStream imageStream =  ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", runtimeService.getActiveActivityIds(processInstance.getId()));  
         request.getSession().setAttribute("inputStream", imageStream);
         response.sendRedirect("test.jsp");
        }catch(RasterFormatException e){
            e.printStackTrace();  
        }  
	}
}
