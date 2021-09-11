package com.thinking.machines.service;
import java.lang.reflect.*;

public class Service
{
public Class classPointer;
public Method methodPointer;
public Object instance;

public Service(Class classPointer,Method methodPointer)
{
this.classPointer=classPointer;
this.methodPointer=methodPointer;
this.instance=null;
}
}