package jmethods;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.config.RandomVariableConfig;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.TransactionController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.control.gui.TransactionControllerGui;
import org.apache.jmeter.extractor.BeanShellPostProcessor;
import org.apache.jmeter.extractor.JSR223PostProcessor;
import org.apache.jmeter.extractor.json.jsonpath.JSONPostProcessor;
import org.apache.jmeter.extractor.json.jsonpath.gui.JSONPostProcessorGui;
import org.apache.jmeter.modifiers.BeanShellPreProcessor;
import org.apache.jmeter.modifiers.UserParameters;
import org.apache.jmeter.modifiers.gui.UserParametersGui;
import org.apache.jmeter.protocol.http.config.gui.HttpDefaultsGui;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.HTTPArgumentsPanel;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.sampler.DebugSampler;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.testelement.property.BooleanProperty;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.TestElementProperty;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.timers.ConstantTimer;
import org.apache.jmeter.timers.SyncTimer;
import org.apache.jmeter.timers.gui.ConstantTimerGui;
import org.apache.jmeter.visualizers.backend.BackendListener;
import org.apache.jmeter.visualizers.backend.BackendListenerGui;

public class JMeterMethods {
	
	public ArrayList<Header> header() {
		// Headers are used on the HeadManagers
		ArrayList<Header> headers= new ArrayList<Header>() {
			private static final long serialVersionUID = 5524951452709084141L;
			{
	            add(new Header("Content-Type", "application/json"));
	            add(new Header("Authorization", ""));
	        }
		}; 
        return headers;
	}

	public HeaderManager reqHeaders(String name, ArrayList<Header> headers) {
		// Header Manager
        HeaderManager headerManager = new HeaderManager();
        headerManager.setName(name);
        headerManager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
        headerManager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName()); 
        for(int i=0; i<headers.size(); i++) {
        	headerManager.add(headers.get(i));
        }
        return headerManager;
	}
	
	public ConfigTestElement reqDefaults(String name, String domain, String protocol, String port) {
		// Request Defaults
        ConfigTestElement reqDeafults=new ConfigTestElement();
        reqDeafults.setName(name);
        reqDeafults.setProperty(new TestElementProperty("HTTPsampler.Arguments", new HTTPArgumentsPanel().createTestElement()));
        reqDeafults.setProperty("HTTPSampler.domain", domain);
        reqDeafults.setProperty("HTTPSampler.protocol", protocol);
        reqDeafults.setProperty("HTTPSampler.port", port);
        reqDeafults.setProperty(TestElement.TEST_CLASS, ConfigTestElement.class.getName());
        reqDeafults.setProperty(TestElement.GUI_CLASS, HttpDefaultsGui.class.getName());
        return reqDeafults;
	}
	
	public BeanShellPreProcessor beanShellPreProcessor(String name, String script, Boolean reset) {
		BeanShellPreProcessor beanShellPreProcessor= new BeanShellPreProcessor();
		beanShellPreProcessor.setName(name);
		beanShellPreProcessor.setProperty("script", script);
		beanShellPreProcessor.setProperty("resetInterpreter", reset);
		beanShellPreProcessor.setProperty(TestElement.TEST_CLASS, BeanShellPreProcessor.class.getName());
		beanShellPreProcessor.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
        return beanShellPreProcessor;
	}
	
	public BeanShellPostProcessor beanShellPostProcessor(String name, String script, Boolean reset) {
		BeanShellPostProcessor beanShellPostProcessor= new BeanShellPostProcessor();
		beanShellPostProcessor.setName(name);
		beanShellPostProcessor.setProperty("script", script);
		beanShellPostProcessor.setProperty("resetInterpreter", reset);
		beanShellPostProcessor.setProperty(TestElement.TEST_CLASS, BeanShellPostProcessor.class.getName());
		beanShellPostProcessor.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
        return beanShellPostProcessor;
	}
	
	public JSR223PostProcessor jSR223PostProcessor(String name, String script) {
		JSR223PostProcessor jSR223PostProcessor= new JSR223PostProcessor();
		jSR223PostProcessor.setName(name);
		jSR223PostProcessor.setProperty("script", script);
		jSR223PostProcessor.setProperty(TestElement.TEST_CLASS, JSR223PostProcessor.class.getName());
		jSR223PostProcessor.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
		return jSR223PostProcessor;
	}
	
	public RandomVariableConfig randomVariable(String name, String format, String min, String max, String varName, Boolean perThread) {
		// Random Variable config element
        RandomVariableConfig randomVariableConfig = new RandomVariableConfig();
        randomVariableConfig.setProperty("variableName",varName);
        randomVariableConfig.setProperty("outputFormat",format);
        randomVariableConfig.setProperty("minimumValue",min);
        randomVariableConfig.setProperty("maximumValue",max);
        randomVariableConfig.setProperty("perThread",perThread);
        randomVariableConfig.setName(name);
        randomVariableConfig.setProperty(TestElement.TEST_CLASS, RandomVariableConfig.class.getName());
        randomVariableConfig.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
        return randomVariableConfig;
	}
	
	public UserParameters userParameters(String name, Boolean perIteration, LinkedList<String> varNames, ArrayList<LinkedList<String>> varValues) {
		//User Parameters
        UserParameters userParameters = new UserParameters();
        userParameters.setName(name);
        userParameters.setProperty(new BooleanProperty("UserParameters.per_iteration", perIteration));
        userParameters.setNames(new CollectionProperty("UserParameters.names", varNames));
        userParameters.setThreadLists(varValues);
        userParameters.setProperty(TestElement.TEST_CLASS, UserParameters.class.getName());
        userParameters.setProperty(UserParameters.GUI_CLASS, UserParametersGui.class.getName());
        return userParameters;
	}
	
	public SyncTimer syncTimer(String name, int threadNumber, String timeOut){
        SyncTimer syncTimer= new SyncTimer();
        syncTimer.setName(name);
        syncTimer.setProperty("groupSize", threadNumber);
        syncTimer.setProperty("timeoutInMs", timeOut);
        syncTimer.setProperty(TestElement.TEST_CLASS, SyncTimer.class.getName());
        syncTimer.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
        return syncTimer;
	}
	
	public CSVDataSet csvDataFile(String name, String path, String varNames, Boolean ignoreFirstLine, String delimiter) {
		// CSV Data Set Config
        CSVDataSet csvDataSet = new CSVDataSet();
        csvDataSet.setProperty("filename", path);
        csvDataSet.setDelimiter(delimiter);
        csvDataSet.setVariableNames(varNames);
        csvDataSet.setIgnoreFirstLine(ignoreFirstLine);
        csvDataSet.setName(name);
        csvDataSet.setProperty("shareMode", "shareMode.all");
        csvDataSet.setProperty(TestElement.TEST_CLASS, CSVDataSet.class.getName());
        csvDataSet.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
        return csvDataSet;
	}
	
	public ConstantTimer constantTimer(String name, String delay) {
		// Constant Timer
        ConstantTimer timer= new ConstantTimer();
        timer.setName(name);
        timer.setDelay(delay);
        timer.setProperty(TestElement.TEST_CLASS, ConstantTimer.class.getName());
        timer.setProperty(TestElement.GUI_CLASS, ConstantTimerGui.class.getName());
        return timer;
	}
	
	public LoopController loopController(String loops) {
		// Loop Controller
        LoopController loopController = new LoopController();
        loopController.setLoops(loops);
        loopController.setFirst(true);
        loopController.setName("Loop controller");
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
        loopController.initialize();
        return loopController;
	}
	
	// Requests
	public HTTPSamplerProxy httpSampler(String name, String domain, String endpoint, String method, String[] args, String[] values, String body) {
		// HTTP Sampler
        HTTPSamplerProxy httpsampler = new HTTPSamplerProxy();
        httpsampler.setPath(endpoint);
        httpsampler.setMethod(method.toUpperCase());
        httpsampler.setName(name);
        httpsampler.setDomain(domain);
        httpsampler.setUseKeepAlive(true);
        httpsampler.setFollowRedirects(true);
        if(body.length()>0) {
	        httpsampler.addNonEncodedArgument("body", body, "");
	        httpsampler.setPostBodyRaw(true);
        }
        if(args!=null) {
        	for(int i=0; i<args.length; i++) {
            	httpsampler.addEncodedArgument(args[i], values[i], "=");
            }
        }
        httpsampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpsampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());
        return httpsampler;
	}
	
	// Debug Sampler
	public DebugSampler debugSampler(String name) {
		DebugSampler debugSampler = new DebugSampler();
        debugSampler.setName(name);
        debugSampler.setProperty("displayJMeterVariables", false);
        debugSampler.setProperty("displayJMeterProperties", false);
        debugSampler.setProperty("displaySystemProperties", false);
        debugSampler.setProperty(TestElement.TEST_CLASS, DebugSampler.class.getName());
        debugSampler.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
        return debugSampler;
	}
	
	// Json Postprocessor(Extractor)
	public JSONPostProcessor jSONPostProcessor(String name, String varName) {
		JSONPostProcessor jSONPostProcessor= new JSONPostProcessor();
		jSONPostProcessor.setName(name);
		jSONPostProcessor.setRefNames(varName);
		jSONPostProcessor.setJsonPathExpressions("$.access_token");
		jSONPostProcessor.setMatchNumbers("1");
		jSONPostProcessor.setDefaultValues("TokenNotFound");
		jSONPostProcessor.setProperty(TestElement.TEST_CLASS, JSONPostProcessor.class.getName());
		jSONPostProcessor.setProperty(TestElement.GUI_CLASS, JSONPostProcessorGui.class.getName());
        return jSONPostProcessor;
	}
	
	// Backend Listener InfluxdbBackendListenerClient
	public BackendListener backendListenerInflux(String name, String database) {
		BackendListener backendListenerInflux = new BackendListener();
		backendListenerInflux.setName(name);
		backendListenerInflux.setClassname("org.apache.jmeter.visualizers.backend.influxdb.InfluxdbBackendListenerClient");
        ArrayList<Argument> listArgs= new ArrayList<Argument>();
        listArgs.add(new Argument("influxdbMetricsSender", "org.apache.jmeter.visualizers.backend.influxdb.HttpMetricsSender", "="));
        listArgs.add(new Argument("influxdbUrl", "http://localhost:8086/write?db="+database, "="));
        listArgs.add(new Argument("application", "application name", "="));
        listArgs.add(new Argument("measurement", "jmeter", "="));
        listArgs.add(new Argument("summaryOnly", "false", "="));
        listArgs.add(new Argument("samplersRegex", ".*", "="));
        listArgs.add(new Argument("percentiles", "99;95;90", "="));
        listArgs.add(new Argument("testTitle", "Test name", "="));
        listArgs.add(new Argument("eventTags", "", "="));
        Arguments args= new Arguments(); 
        args.setArguments(listArgs);
        backendListenerInflux.setArguments(args);
        backendListenerInflux.setProperty(TestElement.TEST_CLASS, BackendListener.class.getName());
        backendListenerInflux.setProperty(TestElement.GUI_CLASS, BackendListenerGui.class.getName());
        return backendListenerInflux;
	}
	
	public ThreadGroup threadGroup(String name, LoopController loopController, int numThreads, int rampUp, Boolean scheduler, long duration, long delay) {
		 // Thread Group
        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setName(name);
        threadGroup.setNumThreads(numThreads);
        threadGroup.setRampUp(0);
        threadGroup.setScheduler(true);
        threadGroup.setDuration(duration);
        threadGroup.setDelay(delay);
        threadGroup.setSamplerController(loopController);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
        return threadGroup;
	}
	
	public TransactionController transactionController(String name, Boolean parentSample) {
		// Transaction Controller
        TransactionController transactionController = new TransactionController();
        transactionController.setProperty("TransactionController.parent", parentSample);
        transactionController.setName(name);
        transactionController.setProperty(TestElement.TEST_CLASS, TransactionController.class.getName());
        transactionController.setProperty(TestElement.GUI_CLASS, TransactionControllerGui.class.getName());
        return transactionController;
	}
	
	public TestPlan testPlan(String name) {
		// Test Plan
        TestPlan testPlan = new TestPlan(name);
        testPlan.setSerialized(true);
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());
        return testPlan;
	}
}
