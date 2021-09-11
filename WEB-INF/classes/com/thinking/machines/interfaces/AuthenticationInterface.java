package com.thinking.machines.interfaces;
import javax.servlet.*;
import javax.servlet.http.*;

public interface AuthenticationInterface
{
public boolean areValidCredentials(HttpSession session,HttpServletRequest request,ServletContext servletContext);
public void otherwise(HttpServletRequest request,HttpServletResponse response);
}