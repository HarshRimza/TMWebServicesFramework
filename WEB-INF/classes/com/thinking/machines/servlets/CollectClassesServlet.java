package com.thinking.machines.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import java.util.jar.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import com.thinking.machines.annotations.*;
import com.thinking.machines.service.*;

public class CollectClassesServlet extends HttpServlet
{
public void init()
{
Map<String,Service> urlServiceMap=new HashMap<>();
ServletContext sc=getServletContext();
List<String> packages;
String folderPath=sc.getRealPath("");
folderPath=folderPath+"WEB-INF"+File.separator+"classes";
try
{
BufferedReader br=new BufferedReader(new FileReader(folderPath+File.separator+"ServiceConfiguration.conf"));
Gson g=new Gson();
JsonObject jsonObject=g.fromJson(br,JsonObject.class);
Type listType = new TypeToken<ArrayList<String>>() {}.getType();
packages=g.fromJson(jsonObject.getAsJsonArray("packages"),listType);
}catch(Exception e)
{
System.out.println("Exception in CollectClassesServlet class "+e);
packages=new ArrayList<String>();
}

File mainDirectory=new File(folderPath);
List<File> directories=new ArrayList<>();
List<String> classFiles=new ArrayList<>();
Class<?> c=null;
String className="";
String packageName="";

try
{
directories.add(mainDirectory);
while(!directories.isEmpty())
{
List<File> subDirectories=new ArrayList<>();
for(File directory:directories)
{
for(File file: directory.listFiles())
{
if(file.isDirectory())
{
subDirectories.add(file);
}
else
{
if(file.getName().endsWith(".class"))
{
className=file.toString().substring(folderPath.length()+1).replaceAll("\\\\",".");
className=className.substring(0,className.lastIndexOf('.'));
packageName=className.substring(0,className.lastIndexOf('.'));
if(!packages.contains(packageName)) continue;
c=Class.forName(className);
Path path=c.getAnnotation(Path.class);
if(path!=null)
{
Method methods[]=c.getDeclaredMethods();
for(Method m:methods)
{
Path methodPath=m.getAnnotation(Path.class);
if(methodPath!=null) urlServiceMap.put(path.value()+methodPath.value(),new Service(c,m));
}
}                               // if(path!=null) ends;

}
else if(file.getName().endsWith(".jar"))
{
JarInputStream jis=new JarInputStream(new FileInputStream(file));
JarEntry je;
while(true)
{
je=jis.getNextJarEntry();
if(je==null) break;
if(je.getName().endsWith(".class"))
{ 
className=je.getName().replaceAll("/","\\.");
className=className.substring(0,className.lastIndexOf('.'));
packageName=className.substring(0,className.lastIndexOf('.'));
if(!packages.contains(packageName)) continue;
c=Class.forName(className);
Path path=c.getAnnotation(Path.class);
if(path!=null)
{
Method methods[]=c.getDeclaredMethods();
for(Method m:methods)
{
Path methodPath=m.getAnnotation(Path.class);
if(methodPath!=null) urlServiceMap.put(path.value()+methodPath.value(),new Service(c,m));
}
}                                // if(path!=null) ends;

}

} //while ends
} //else if
} //else
} //for file
} //for directory
directories.clear();
directories.addAll(subDirectories);
}

}catch(Exception e)
{
System.out.println(e);
}

sc.setAttribute("urlServiceMap",urlServiceMap);
}
}