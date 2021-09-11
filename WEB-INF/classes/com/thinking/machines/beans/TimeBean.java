package com.thinking.machines.beans;
public class TimeBean implements java.io.Serializable
{
private int hours;
private int minutes;
private int seconds;
public TimeBean()
{
this.hours=0;
this.minutes=0;
this.seconds=0;
}
public void setHours(int hours)
{
this.hours=hours;
}
public int getHours()
{
return this.hours;
}
public void setMinutes(int minutes)
{
this.minutes=minutes;
}
public int getMinutes()
{
return this.minutes;
}
public void setSeconds(int seconds)
{
this.seconds=seconds;
}
public int getSeconds()
{
return this.seconds;
}
}