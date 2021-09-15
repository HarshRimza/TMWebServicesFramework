# <div align='center'> TMWebServicesFramework </div>
A J2EE Web Services Framework for easy and fast development of web-applications using Java Reflection API and Java Annotation. This framework can handle request forwarding, login-logout part, raw data processing, URL mapping with user defined class methods and file uploading system. The user has no need to understand the java servlet concept.

#### We have created different types of annotations for different kinds of works. These annotations reside in the package 'com.thinking.machines.annotations'

<div align='center'>

|Annotation|Description|Value Type|
|--------|------|----|
|@FilesAnnotation|For handling the File Uploading Part of web application |No Type|
|@Forward|For forwarding request to different classes / jsp |String Type|
|@Path|For mapping class's methods to url |String Type|
|@RequestData|For providing data to the mapped methods |String Type|
|@ResponseType|For sending response json / text data back  |String Type|
|@Secured|For handling the login / logout module |Class Type|
|Special Objects|For providing objects like HttpServletRequest, HttpServletResponse, ServletContext, HttpSession to mapped methods |
  
</div>

### How to use the framework
First you need to download the 'tmswf.jar' from this repository and put it in the '\your-project\WEB-INF\lib' folder and then add some lines to 'web.xml' file in your project's 'WEB-INF' folder as below for our servlet mapping 
```xml
<servlet>
<servlet-name>CollectClassesServlet</servlet-name>
<servlet-class>com.thinking.machines.servlets.CollectClassesServlet</servlet-class>
<load-on-startup>0</load-on-startup>
</servlet>

<servlet>
<servlet-name>TMService</servlet-name>
<servlet-class>com.thinking.machines.servlets.TMService</servlet-class>
</servlet>
<servlet-mapping>
<servlet-name>TMService</servlet-name>
<url-pattern>/service/*</url-pattern>
</servlet-mapping>
```
Now, you are good to go to use our web services framework.


#### Let's start with How to apply @Path annotation
@Path Annotation needs to be applied on classes and methods both only if you want to map methods of the class to the url.

For Example : The URL is `http://localhost:8080/your-project-name/service/student/kite` here `service` is the framework servlet and `student`, `kite` are used for mapping method.
```java
@Path("/student")
class Student
{
@Path("/kite")
public void doSomething()
{
System.out.println("doSomething got called");
}
}
```
So, whenever the request come for url  `http://localhost:8080/your-project-name/service/student/kite` the `doSomething()` method will be called. In this way you can map a class method with url.

#### How to apply @RequestData annotation
@RequestData Annotation needs to be applied on method's parameters if the data is coming from client in request scope.

For Example : The URL is `http://localhost:8080/your-project-name/service/student/setdata` here `service` is the framework servlet and `student`, `setdata` are used for mapping method.
```java
@Path("/student")
class Student
{
@Path("/setdata")
public void setData(@RequestData("rr") int rollNumber,@RequestData("nn") String name)
{
System.out.println("Data Received, Roll Number : "+rollNumber+", Name : "+name);
}
}
```

And So the html would be like this
```html
<html>
  <head>...</head>
  <body>
    <form method='POST' action='/your-project-name/services/student/setdata'>
      <input type='text' id='rr' name='rr'><br><br>
      <input type='text' id='nn' name='nn'><br><br>
      <button type='submit'>Proceed</button>
    </form>
  </body>
</html>
```
So, whenever the form get submitted the `setData()` method will get called and the value of 'rr' input field and value of 'nn' input field will be passed as arguments to setData() method.

#### How to apply @ResponseType annotation
@ResponseType Annotation needs to be applied on method if the method is supposed to send data from server to client. This annotation accepts only two value 
1. Normal String
2. JSON String

For Example : The URL is `http://localhost:8080/your-project-name/service/student/getdata` here `service` is the framework servlet and `student`, `getdata` are used for mapping method and the second URL is `http://localhost:8080/your-project-name/service/student/getJSONData`.
```java

@Path("/student")
class Student
{
public int rollNumber;
public String name;

public Student()
{
this.rollNumber=0;
this.name="";
}

public Student(int rollNumber,String name)
{
this.rollNumber=rollNumber;
this.name=name;
}

// for sending plain string
@ResponseType("html/text")
@Path("/getdata")
public String getData()
{
return "Every thing is fine";
}

// for sending complex data in JSON string format
@ResponseType("JSON")
@Path("/getJSONData")
public Student getJSONData()
{
return new Student(101,"Suresh");
}

}
```
So, you can send String or JSON String as a response. Whenever request come for `/student/getdata` plain string will get send and when request come for `/student/getJSONData` the complex data will get converted to JSON String and then it will get send to client.

#### How to apply @Forward annotation
@Forward Annotation needs to be applied on method if the method's return type is of void type and it is supposed to forward request to other mapped method or to any JSP file.

For Example : The URL is `http://localhost:8080/your-project-name/service/student/forwardToMethod` here `service` is the framework servlet and `student`, `forwardToMethod` are used for mapping method and the second URL is `http://localhost:8080/your-project-name/service/student/forwardToJSP`.
```java

@Path("/student")
class Student
{

@ResponseType("html/text")
@Path("/whatever")
public String whatever()
{
return "whatever method got called";
}

@Forward("/student/whatever")
@Path("/forwardToMethod")
public void forwardToMethod()
{
System.out.println("Forwarding request to /student/whatever");
}

@Forward("/whatever.jsp")
@Path("/forwardToJSP")
public void forwardToJSP()
{
System.out.println("Forwarding request to whatever.jsp");
}

}
```
So, Whenever the request will arrive for `/student/forwardToMethod` after processing of the mapped method the request will get forwarded to `/student/whatever` and as the response type of 'whatever()' method is 'html/text', so a string as response will get send to client. And whenever the request will arrive for `/student/forwardToJSP` after processing of the mapped method the request will get forwarded 'whatever.jsp' and this JSP file should be present in your-project-folder.

#### How to apply @Secured annotation
@Secured Annotation needs to be applied on method if the method is involved in login/logout handling.

For Example : The URL is `http://localhost:8080/your-project-name/service/student/login` here `service` is the framework servlet and `student`, `login` are used for mapping method.
```java
@Path("/student")
class Student
{
@Secured(Login.class)
@Path("/login")
public void login()
{
System.out.println("Login method got called");
}
}
```
Now, the value of @Secured annotation is of Class Type, so you have to create a class which will implement AuthenticationInterface and override two methods of the interface
1. areValidCredentials() with return type boolean and parameters of classes HttpSession, HttpServletRequest and ServletContext
        `boolean areValidCredentials(HttpSession session,HttpServletRequest request,ServletContext servletContext)`
2. otherwise  with return type void and parameters of classes HttpServletRequest and HttpServletResponse
        `void otherwise(HttpServletRequest request,HttpServletResponse response)`

On the decision of 'areValidCredentials()' method it will get decided wheather to process the login method or not.
1. If the method returns true the login method will get called.
2. If the method returns false the otherwise method will get called and the login method will not get executed.

```java
import com.thinking.machines.interfaces.*;  // for AuthenticationInterface
public class Login implements AuthenticationInterface
{
public boolean areValidCredentials(HttpSession session,HttpServletRequest request,ServletContext servletContext)
{
// calculation wheather credentials are right or wrong
//return true/false;
}
public void otherwise(HttpServletRequest request,HttpServletResponse response)
{
// whatever you want to do forward request to other file or send response back to client
}
}
```
So, whenever the request will arrive for `/student/login` the 'areValidCredentials()' method will get called of that class whose type is assigned to @Secured annotation and if the 'areValidCredentials()' method returns false then otherwise method will get called, if the 'areValidCredentials()' method returns true then the method mapped with `/student/login` URL will get processed.

#### How to apply @FilesAnnotation annotation
@FilesAnnotation Annotation needs to be applied on method if the method is supposed to be used for getting uploaded file / accept array of files. To use this feature you need to create a folder named as 'filestore' in 'WEB-INF' folder of your project. 

For Example : The URL is `http://localhost:8080/your-project-name/service/student/uploadFiles` here `service` is the framework servlet and `student`, `uploadFiles` are used for mapping method.
```java
@Path("/student")
class Student
{
@FilesAnnotation
@Path("/uploadFiles")
public void uploadFiles(File files[])
{
// piece of code to copy the files from filestore folder to your desired location.
}
}
```
So, Whenever the request will arrive for `/student/uploadFiles` the mapped method will get called and the array of uploaded files will be provided to the mapped method.

Note : For uploading file it is necessary to provide File[] array to the mapped method parameters

#### Special Objects
All The mapped methods have access to Special Objects i.e. HttpServletRequest, HttpServletResponse, HttpSession, and ServletContext. To use them there is no need of any annotation. 
```java
@Path("/student")
class Student
{
@Path("/kite")
public void doSomething(HttpServletRequest request,HttpServletResponse response,HttpSession session,ServletContext context)
{
System.out.println("doSomething got called");
}
}
```
So, you can get access to the special objects whenever you want them in your mapped methods.

> Note : Don't forget to include the 'your-project-name\WEB-INF\lib' folder in your classpath while compiling your '.java' file.
