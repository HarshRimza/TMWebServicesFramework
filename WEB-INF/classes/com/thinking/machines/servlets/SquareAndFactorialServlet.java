package com.thinking.machines.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import com.google.gson.*;
import com.thinking.machines.beans.*;
public class SquareAndFactorialServlet extends HttpServlet
{
// doPost to accept raw data starts
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
BufferedReader br=request.getReader();
StringBuilder sb=new StringBuilder();
String line;
while(true)
{
line=br.readLine();
if(line==null)break;
sb.append(line);
}
String jsonString=sb.toString();
System.out.println(jsonString);
Gson gson=new Gson();
NumberWrapperBean nw=new NumberWrapperBean();
nw=(NumberWrapperBean)gson.fromJson(jsonString,NumberWrapperBean.class);
int num1=nw.getNum1();
int num2=nw.getNum2();

int square=num1*num1;

int e,f;
e=1;
f=1;
while(e<=num2)
{
f=f*e;
e++;
}

SquareAndFactorialBean safb=new SquareAndFactorialBean();
safb.setSquare(square);
safb.setFactorial(f);
AJAXResponse ajaxResponse=new AJAXResponse();
ajaxResponse.setResponse(safb);
ajaxResponse.setSuccess(true);
ajaxResponse.setException("");
response.setContentType("application/json");
PrintWriter pw=response.getWriter();
String responseString=gson.toJson(ajaxResponse);
pw.print(responseString);
}catch(Exception e)
{
System.out.println(e);
}
}
//doPost to accept raw data ends

public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
AJAXResponse ajaxResponse=new AJAXResponse();
ajaxResponse.setResponse("");
ajaxResponse.setSuccess(false);
ajaxResponse.setException("GET type request not allowed");
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



/*
//doPost to accept data starts
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
int num1=Integer.parseInt(request.getParameter("num1"));
int num2=Integer.parseInt(request.getParameter("num2"));
int square=num1*num1;
int e,f;
e=1;
f=1;
while(e<=num2)
{
f=f*e;
e++;
}

SquareAndFactorialBean safb=new SquareAndFactorialBean();
safb.setSquare(square);
safb.setFactorial(f);
AJAXResponse ajaxResponse=new AJAXResponse();
ajaxResponse.setResponse(safb);
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
}//doPost to accept data ends
*/
}