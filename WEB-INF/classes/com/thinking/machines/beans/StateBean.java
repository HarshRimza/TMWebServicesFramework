package com.thinking.machines.beans;
public class StateBean implements java.io.Serializable
{
private int code;
private String name;
public StateBean()
{
this.code=0;
this.name="";
}
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
}