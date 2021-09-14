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

















