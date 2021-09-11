package com.xxx.yyy;
import javax.servlet.*;
import javax.servlet.http.*;
import com.thinking.machines.annotations.*;
import com.thinking.machines.interfaces.*;
public class Login implements AuthenticationInterface
{
public boolean areValidCredentials(HttpSession session,HttpServletRequest request,ServletContext servletContext)
{
String username=request.getParameter("username");
String password=request.getParameter("password");
if(username.equals("aaa")) return true;
else return false;
}
public void otherwise(HttpServletRequest request,HttpServletResponse response)
{
try
{
RequestDispatcher rd=request.getRequestDispatcher("/login.html");
rd.forward(request,response);
}catch(Exception e)
{
System.out.println("Login exception : "+e);
}
}
}
