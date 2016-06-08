package testJsEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.Arrays;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

public class TestJsEngine {

	private Logger logger = LogManager.getLogger(TestJsEngine.class);

	// 支持对象的
	/**
	 * 运行JS对象中的函数
	 * 
	 * @return
	 */
	@Test
	public void jsObjFunc() {
		String script = "var obj={run:function(){return 'run method : return:\"abc'+this.next('test')+'\"';},next:function(str){return ' 我来至next function '+str+')'}}";
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine scriptEngine = sem.getEngineByName("js");
		try {
			scriptEngine.eval(script);
			Object object = scriptEngine.get("obj");
			Invocable inv2 = (Invocable) scriptEngine;
			String result = (String) inv2.invokeMethod(object, "run");// 动态参数，支持传入function中参数
			logger.info("result :" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void jsObjFuncWithParam() {
		String script = "var obj = {run: function(name) {return 'run method : return:' + name + this.next('test') ;},next: function(str) {return ' 我来至next function ' + str + ')'}}";
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine scriptEngine = sem.getEngineByName("js");
		try {
			scriptEngine.eval(script);
			Object object = scriptEngine.get("obj");
			Invocable inv2 = (Invocable) scriptEngine;
			String result = (String) inv2.invokeMethod(object, "run", "ljx");// 动态参数，支持传入function中参数
			logger.info("result :" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取js对象数字类型属性
	 * 
	 * @return
	 */
	@Test
	public void getArray() {
		ScriptEngineManager sem = new ScriptEngineManager();
		String script = "var obj={array:['test',true,1,1.0,2.11111]}";

		ScriptEngine scriptEngine = sem.getEngineByName("js");
		try {
			scriptEngine.eval(script);// 加载完脚本
			Object object2 = scriptEngine.eval("obj.array");// 加载完脚本直接就可以获取里面元素，然后获取field
			@SuppressWarnings("rawtypes")
			Class clazz = object2.getClass();
			Field denseField = clazz.getDeclaredField("dense");// 获取元素中的所有field，field自动映射到对应类型
			denseField.setAccessible(true);
			Object[] objs = (Object[]) denseField.get(object2);//
			logger.info(Arrays.toString(objs));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * JS计算
	 * 
	 * @param script
	 * @return
	 */
	@Test
	public void jsCalculate() {
		String script = "var obj = {run: function(name) {return 'run method : return:' + name + this.next('test') ;},next: function(str) {return ' 我来至next function ' + str + ')'}}";
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		try {
			Object obj = (Object) engine.eval(script);
			logger.info(obj);
		} catch (ScriptException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 运行JS基本函数
	 */
	@Test
	public void jsFunction() {
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine se = sem.getEngineByName("javascript");
		try {
			String script = "function say(name){ return 'hello,'+name; }";
			se.eval(script);
			Invocable inv2 = (Invocable) se;
			String res = (String) inv2.invokeFunction("say", "test");// 调用function并传入参数
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * JS中变量使用
	 */
	@Test
	public void jsVariables() {
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine engine = sem.getEngineByName("javascript");
		File file = new File("/data/js.txt");
		engine.put("file", file);
		try {
			engine.eval("println('path:'+file.getAbsoluteFile())");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Executing Scripts from Java Programs : Script Engines
	 * 
	 * @throws ScriptException
	 */
	@Test
	public void scriptExecutionDemo() throws ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine jsEngine = manager.getEngineByExtension("js");
		jsEngine.eval("println ('Hello! JavaScript executed from a Java program.')");
	}

	/**
	 * Read and execute a script source file : Script Engines
	 * 
	 * @throws FileNotFoundException
	 * @throws ScriptException
	 */
	@Test
	public void scriptExecutionReaderDemo() throws FileNotFoundException, ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine jsEengine = manager.getEngineByExtension("js");
		Reader reader = new InputStreamReader(
				new FileInputStream("C:/stsWorkspace/testJsEngine/src/main/resources/myScript.js"));
		jsEengine.eval(reader);
	}

	/**
	 * Using thread to run JavaScript by Java : Script Engines
	 * 
	 * @throws ScriptException
	 * @throws InterruptedException
	 */
	@Test
	public void InterfaceTest() throws ScriptException, InterruptedException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		engine.eval("function run() {print('www.java2s.com');}");
		Invocable invokeEngine = (Invocable) engine;
		Runnable runner = invokeEngine.getInterface(Runnable.class);
		Thread t = new Thread(runner);
		t.start();
		t.join();
	}

	/**
	 * Get the value in JavaScript from Java Code by reference the variable name
	 * : Script Engines
	 */
	@Test
	public void RunJavaScript() {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		try {
			engine.put("name", "abcde");
			engine.eval("var output = '';for (i = 0; i <= name.length; i++) {"
					+ "  output = name.charAt(i)+'-' + output" + "}");
			String name = (String) engine.get("output");
			System.out.println(name);
		} catch (ScriptException e) {
			System.err.println(e);
		}
	}

	/**
	 * Variables bound through ScriptEngine : Script Engines
	 * @throws ScriptException
	 */
	@Test
	public void BindingDemo() throws ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		engine.put("a", 1);
		engine.put("b", 5);

		Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		Object a = bindings.get("a");
		Object b = bindings.get("b");
		System.out.println("a = " + a);
		System.out.println("b = " + b);

		Object result = engine.eval("c = a + b;");
		System.out.println("a + b = " + result);
	}
}
