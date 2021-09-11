package com.thinking.machines.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import com.google.gson.*;
import com.thinking.machines.beans.*;
public class CountryServlet extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
ArrayList<CountryBean> countries =new ArrayList<CountryBean>();
try
{
Class.forName("org.apache.derby.jdbc.ClientDriver");
Connection connection=DriverManager.getConnection("jdbc:derby://localhost:1527/combobox");
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select * from country order by name");
CountryBean country=null;
while(resultSet.next())
{
country=new CountryBean();
country.setCode(resultSet.getInt("code"));
country.setName(resultSet.getString("name").trim());
countries.add(country);
}
AJAXResponse ajaxResponse=new AJAXResponse();
ajaxResponse.setResponse(countries);
ajaxResponse.setSuccess(true);
ajaxResponse.setException("");
response.setContentType("application/json");
PrintWriter pw=response.getWriter();
Gson gson=new Gson();
String responseString=gson.toJson(ajaxResponse);
pw.print(responseString);
}catch(Exception e)
{
System.out.println("CountryServlet : "+e);
}
}
}