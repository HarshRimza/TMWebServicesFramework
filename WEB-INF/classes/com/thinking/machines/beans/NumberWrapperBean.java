package com.thinking.machines.beans;
public class NumberWrapperBean implements java.io.Serializable
{
private int num1;
private int num2;
public NumberWrapperBean()
{
this.num1=0;
this.num2=0;
}
public void setNum1(int num1)
{
this.num1=num1;
}
public int getNum1()
{
return this.num1;
}
public void setNum2(int num2)
{
this.num2=num2;
}
public int getNum2()
{
return this.num2;
}
}