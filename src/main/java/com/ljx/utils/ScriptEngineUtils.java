package com.ljx.utils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptEngineUtils {
	
	public static Invocable getInstance(String script) throws ScriptException {
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine se = sem.getEngineByName("javascript");
		se.eval(script);
		Invocable invocable = (Invocable) se;
		return invocable ;
	}
}
