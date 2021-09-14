# <div align='center'> TMWebServicesFramework </div>
A J2EE Web Services Framework for easy and fast development of web-applications. This framework can handle request forwarding, login-logout part, raw data processing, URL mapping with user defined class methods and file uploading system. The user has no need to understand the java servlet concept.

#### We have created different types of annotations for different kinds of works.

<div align='center'>

|Annotation|Description|
|--------|------|
|@FilesAnnotation|For handling the File Uploading Part of web application |
|@Forward|For forwarding request to different classes / jsp |
|@Path|For mapping class's methods to url |
|@RequestData|For providing data to the mapped methods |
|@ResponseType|For sending response json / text data back  |
|@Secured|For handling the login / logout module |
|Special Objects|For providing objects like HttpServletRequest, HttpServletResponse, ServletContext, HttpSession to mapped methods |
  
</div>

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
Now, the value of @Secured annotation is of Class Type, so you have to create a class which will inherit AuthenticationInterface and override two methods
1. areValidCredentials() with return type boolean and parameters of classes HttpSession, HttpServletRequest and ServletContext
        `boolean areValidCredentials(HttpSession session,HttpServletRequest request,ServletContext servletContext)`
2. otherwise  with return type void and parameters of classes HttpServletRequest and HttpServletResponse
        `void otherwise(HttpServletRequest request,HttpServletResponse response)`









