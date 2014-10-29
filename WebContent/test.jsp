<%@page import="java.io.InputStream"%>  
<%@page import="org.activiti.engine.impl.*"%>  
<%@page import="org.activiti.engine.impl.pvm.*"%>  
<%@page import="org.activiti.engine.impl.pvm.process.*"%>  
<%@page import="org.activiti.engine.repository.*"%>  
<%@page import="org.activiti.engine.*"%>  
<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<%  response.setContentType("image/jpeg");
    InputStream is = (InputStream)session.getAttribute("inputStream");
    byte[] b = new byte[1024];
    int len = -1;
    out.println("InputStream is null ? : " + (is == null));
    if(is == null) return;
    while((len = is.read(b, 0, 1024)) != -1) {
    	 response.getOutputStream().write(b, 0, len);
         // 防止异常：getOutputStream() has already been called for this response
         out.clear();
         out = pageContext.pushBody();
    }
%> 