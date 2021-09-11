package com.thinking.machines.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import com.google.gson.*;
import com.thinking.machines.beans.*;
public class StateServlet extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
ArrayList<StateBean> states=new ArrayList<StateBean>();
try
{
int country_code=Integer.parseInt(request.getParameter("country"));
Class.forName("org.apache.derby.jdbc.ClientDriver");
Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/combobox");
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select * from state where country_code="+country_code);
StateBean state=null;
while(resultSet.next())
{
state=new StateBean();
state.setCode(resultSet.getInt("code"));
state.setName(resultSet.getString("name").trim());
states.add(state);
}
AJAXResponse ajaxResponse=new AJAXResponse();
ajaxResponse.setResponse(states);
ajaxResponse.setSuccess(true);
ajaxResponse.setException("");
response.setContentType("application/json");
PrintWriter pw=response.getWriter();
Gson gson=new Gson();
String responseString=gson.toJson(ajaxResponse);
pw.print(responseString);
}catch(Exception e)
{
System.out.println("StateServlet : "+e);
}
}
}