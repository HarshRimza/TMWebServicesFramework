package com.thinking.machines.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import com.google.gson.*;
import com.thinking.machines.beans.*;
public class TimeServlet extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
Date date=new Date();

DateBean dateBean=new DateBean();
dateBean.setDate(date.getDate());
dateBean.setMonth(date.getMonth()+1);
dateBean.setYear(date.getYear()+1900);

TimeBean timeBean=new TimeBean();
timeBean.setHours(date.getHours());
timeBean.setMinutes(date.getMinutes());
timeBean.setSeconds(date.getSeconds());

DateTimeBean dateTimeBean=new DateTimeBean();
dateTimeBean.setDate(dateBean);
dateTimeBean.setTime(timeBean);

AJAXResponse ajaxResponse=new AJAXResponse();
ajaxResponse.setResponse(dateTimeBean);
ajaxResponse.setSuccess(true);
ajaxResponse.setException("");
response.setContentType("application/json");
PrintWriter pw=response.getWriter();
Gson gson=new Gson();
String responseString=gson.toJson(ajaxResponse);
pw.print(responseString);
}catch(Exception e)
{
System.out.println(e);
}
}
}