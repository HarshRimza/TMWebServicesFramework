package com.thinking.machines.beans;
public class DateBean implements java.io.Serializable
{
private int date;
private int month;
private int year;
public DateBean()
{
this.date=0;
this.month=0;
this.year=0;
}
public void setDate(int date)
{
this.date=date;
}
public int getDate()
{
return this.date;
}
public void setMonth(int month)
{
this.month=month;
}
public int getMonth()
{
return this.month;
}
public void setYear(int year)
{
this.year=year;
}
public int getYear()
{
return this.year;
}
}