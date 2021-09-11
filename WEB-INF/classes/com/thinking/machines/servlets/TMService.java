package com.thinking.machines.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.jar.*;
import com.google.gson.*;
import com.thinking.machines.annotations.*;
import com.thinking.machines.service.*;
import com.thinking.machines.interfaces.*;
import com.thinking.machines.exceptions.*;
@MultipartConfig(maxFileSize=1024*1024*5)
public class TMService extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
ServletContext sc=getServletContext();
Map urlServiceMap=(Map)sc.getAttribute("urlServiceMap");

String urlPattern=request.getRequestURL().toString();
String url=urlPattern.substring(urlPattern.indexOf("service")+7); 
Object toSend=null;
PrintWriter pw=null;
try
{ 
Gson g=new Gson();
pw=response.getWriter();
Service service=(Service)urlServiceMap.get(url);
//System.out.println(service.classPointer+","+service.methodPointer);
while(service!=null)
{
if(service.instance==null) service.instance=service.classPointer.newInstance();
FilesAnnotation filesAnnotation=service.methodPointer.getAnnotation(FilesAnnotation.class);
Annotation parameterAnnotations[][]=service.methodPointer.getParameterAnnotations();
Class classes[]=service.methodPointer.getParameterTypes();
Object arguments[]=(parameterAnnotations.length!=0)? new Object[parameterAnnotations.length] : null;
Annotation annotation=null;
int i=0;
for(Class c:classes)
{
if(parameterAnnotations[i].length==1)
{
annotation=parameterAnnotations[i][0];
if(annotation instanceof RequestData)
{
RequestData requestData=(RequestData)annotation;
if(c==int.class) arguments[i]=Integer.parseInt(request.getParameter(requestData.value()));
else if(c==String.class) arguments[i]=request.getParameter(requestData.value());
else arguments[i]=g.fromJson(request.getReader(),Class.forName(classes[i].getTypeName()));
//System.out.println(requestData.value()+","+c+","+c.getSimpleName()+","+c.getTypeName());
}
}
else
{
if(c==HttpServletRequest.class) arguments[i]=request;
else if(c==HttpServletResponse.class) arguments[i]=response;
else if(c==HttpSession.class) arguments[i]=request.getSession();
else if(c==ServletContext.class) arguments[i]=sc;
else if(c==File[].class && filesAnnotation!=null) 
{
List<File> whatever=new ArrayList<>();
String path=getServletContext().getRealPath("");
path=path+"WEB-INF"+File.separator+"filestore"+File.separator;
for(Part part:request.getParts())
{
String cd=part.getHeader("content-disposition");
String pcs[]=cd.split(";");
for(String pc:pcs)
{
if(pc.indexOf("filename")!=-1)
{
String fn=pc.substring(pc.indexOf("=")+2,pc.length()-1);
File file=new File(path+fn);
if(file.exists()) file.delete();
part.write(path+fn);
whatever.add(file);
//System.out.println("File Considered and Saved : "+fn);
}
}
}
File files[]=new File[whatever.size()];
files=whatever.toArray(files);
arguments[i]=files;
}
//System.out.println(c+","+c.getSimpleName()+","+c.getTypeName());
}
i++;
}
//System.out.println("1");
Secured secured=service.methodPointer.getAnnotation(Secured.class);
Forward forward=service.methodPointer.getAnnotation(Forward.class);
ResponseType responseType=service.methodPointer.getAnnotation(ResponseType.class);
Type t=service.methodPointer.getReturnType();

boolean decision=false;
boolean securedApplied=false;
if(secured!=null)
{
securedApplied=true;
Class<?> whatever=secured.value();
Object o=whatever.newInstance();
Method areValidCredentialsMethod=whatever.getDeclaredMethod("areValidCredentials",HttpSession.class,HttpServletRequest.class,ServletContext.class);
Method otherwiseMethod=whatever.getDeclaredMethod("otherwise",HttpServletRequest.class,HttpServletResponse.class);
if(areValidCredentialsMethod!=null)
{
decision=(boolean)areValidCredentialsMethod.invoke(o,request.getSession(),request,sc);
if(!decision && otherwiseMethod!=null) otherwiseMethod.invoke(o,request,response);
}
else if(otherwiseMethod!=null) 
{
otherwiseMethod.invoke(o,request,response);
}
}

if(!securedApplied || (securedApplied && decision))
{
if(responseType!=null && t!=void.class)
{
//System.out.println("Inside response type");
String rt=responseType.value();
if(rt.equals("JSON") && t!=String.class && t!=int.class) toSend=g.toJson(service.methodPointer.invoke(service.instance,arguments));
else if(rt.equals("html/text") && t==String.class) toSend=service.methodPointer.invoke(service.instance,arguments);
else throw new DAOException("No valid response type provided");
pw.println(toSend);
break;
}
else if(forward!=null && t==void.class)
{
url=forward.value();
if(url.length()!=0)
{
//System.out.println("Inside forward");
service.methodPointer.invoke(service.instance,arguments);
service=(Service)urlServiceMap.get(forward.value());
if(service==null)
{
//System.out.println("Inside service==null");
RequestDispatcher rd=request.getRequestDispatcher(url);
rd.forward(request,response);
break;
}
}
else
{
throw new DAOException("No forward url provided");
//pw.println(toSend);
//break;
}
}
else
{
if(decision && filesAnnotation!=null)toSend=service.methodPointer.invoke(service.instance,arguments);
else if(decision || filesAnnotation!=null) toSend=service.methodPointer.invoke(service.instance,arguments);
else throw new DAOException("Method can't be run, may be you made a mistake while specifying annotation or it's value");
pw.println(toSend);
break;
}
}
else
{
throw new DAOException("Method can't be run as no annotation provided for further processing");
//pw.println(toSend);
//break;
}
}                                         // while(service!=null) ends


}catch(Exception e)
{
System.out.println("Exception : "+e.getMessage());
if(pw!=null) pw.println("");
}

}                                 //doPost ends
}                                 //class close;