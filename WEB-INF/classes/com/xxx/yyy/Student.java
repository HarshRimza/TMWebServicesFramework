package com.xxx.yyy;
import javax.servlet.*;
import javax.servlet.http.*;
import com.thinking.machines.annotations.*;
import java.io.*;
import java.nio.file.Files;

@Path("/student")
public class Student
{

public int rollNumber;
public String name;
public int age;

public Student()
{
this.rollNumber=0;
this.name="";
this.age=0;
}

public Student(int rollNumber,String name,int age)
{
this.rollNumber=rollNumber;
this.name=name;
this.age=age;
}


@ResponseType("html/text")
@Path("/kite")
public String doSomething()
{
return "cool stuff";
}

@Path("/register")
public String registerStudent(@RequestData("rr") int rollNumber,@RequestData("nn") String name, @RequestData("age") int age)
{
System.out.println("Roll Number : "+rollNumber+", Name : "+name+", Age : "+age);
return "Student Registered Successfully";
}


@Forward("/Student.jsp")
@Path("/whatever")
public void registerStudent(@RequestData("rr") int rollNumber,@RequestData("nn") String name, @RequestData("age") int age,HttpSession session)
{
session.setAttribute("student",new Student(rollNumber,name,age));
System.out.println("Roll Number : "+rollNumber+", Name : "+name+", Age : "+age);
System.out.println("Forwarding request to Student.jsp");
}

@ResponseType("JSON")
@Path("/getStudent")
public Student getStudentData(HttpSession session)
{
Student student=(Student)session.getAttribute("student");
System.out.println("Roll Number : "+student.rollNumber+", Name : "+student.name+", Age : "+student.age);
return student;
}

@Forward("/student/getStudent")
@Path("/forwardRequest")
public void forwardToGetStudent(@RequestData("stu") Student student,HttpSession session)
{
session.setAttribute("student",student);
System.out.println("Forwarding request to 'getStudent' method ");
}


@Forward("/Student.jsp")
@Path("/forwarding")
public void registerStudent(HttpSession session)
{
String message=(String)session.getAttribute("message");
System.out.println("Forwarding request to Student.jsp");
System.out.println(message);
}

@Forward("/student/forwarding")
@Secured(Login.class)
@Path("/login")
public void whatever(HttpSession session)
{
System.out.println("Whatever");
session.setAttribute("message","Everything is fine");
}

@FilesAnnotation
@Path("/file")
public String getFile(File files[])
{
File where=new File("c:\\tomcat9\\webapps\\ajax-examples\\uploadedFiles\\");
for(File file:files)
{
try
{
Files.copy(file.toPath(),new File(where.toPath()+File.separator+file.getName()).toPath());
}catch(Exception e)
{
System.out.println(e);
}
}
return "Files Copied";
}

}