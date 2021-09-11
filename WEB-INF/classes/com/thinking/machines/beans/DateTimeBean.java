package com.thinking.machines.beans;
public class DateTimeBean implements java.io.Serializable
{
private DateBean date;
private TimeBean time;
public DateTimeBean()
{
this.date=null;
this.time=null;
}
public void setDate(DateBean date)
{
this.date=date;
}
public DateBean getDate()
{
return this.date;
}
public void setTime(TimeBean time)
{
this.time=time;
}
public TimeBean getTime()
{
return this.time;
}
}