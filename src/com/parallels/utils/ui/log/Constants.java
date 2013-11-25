package com.parallels.utils.ui.log;

import java.util.regex.Pattern;

public class Constants {

	static final Pattern DO_NOT_PROCESS = Pattern
			.compile("(.*)\\[(.*)Tasks::impl::GlobalQueue::(.*)\\] (.*)|"
					+ "(.*)\\[(.*)Tasks::impl::TaskImpl::process\\](.*)|"
					+ "(.*)\\[(.*)Tasks::impl::TaskManager_impl::(.*)\\](.*)|"
					+ "(.*)\\[(.*)SimpleServantActivator::etherealize\\](.*)|"
					+ "(.*)\\]: STMT \\[Con:(.*)|"
					+ "(.*)\\]: CORBA_1:(.*)|"
					// + "(.*)\\[OpenAPI::(.*)|"
					+ "(.*)\\[DBS::TransactionManager::getTransaction\\](.*)|"
					+ "(.*)\\[txn:(\\d+) DBS::Transaction_impl::commit\\](.*)|"
					+ "(.*)\\[(.*)APS::Controller::RdbmsSessionImp::(.*)\\](.*)|"
					+ "(.*)\\[(.*)pleskd(.*)\\](.*)|"
					+ ".*CosTransactions::Current_impl::set_timeout.*|"
					+ ".*OpenAPI::LogMethodCalls::process.*|"
					+ ".*OpenAPI::TransformExceptions::process.*|"
					+ ".*OpenAPI::Begin::execute.*|"
					+ ".*OpenAPI::MethodDispatcher.*|"
					+ ".*OpenAPI::MethodSignature.*|"
					+ ".*OpenAPI::PEMProxyMethod::execute.*|"
					+ ".*DBS::checkSQLError.*|"
					+ ".*Error while executing the query.*|"
					+ ".*APS::Controller::RESTful::ResourceNotification::.*|"
					+ ".*APS::Provision::ApplicationResource::Impl::notify.*|"
					+ ".*APS::Controller::RESTful::checkResourceReferPermission.*");
	static final Pattern METHOD_ENTER = Pattern.compile("(.+) ===> ENTRY");
	static final Pattern RUNNER_ENTER = Pattern
			.compile("(.+) Runner (.+) - task (\\d+) \\(start_at: (\\d+), now: (\\d+)\\)");
	static final Pattern METHOD_EXIT = Pattern
			.compile("(.+) <=== EXIT \\[(.*)\\]");
	static final Pattern METHOD_EXIT_ERROR = Pattern
			.compile("(.+) <=== EXIT \\(by exception\\) \\[(.*)\\]");
	static final Pattern METHOD_TRANSACTION_TASK = Pattern
			.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[txn:(\\d+) task:(\\d+) (.+)\\](.*)");
	static final Pattern METHOD_TASK_ANNOTATED = Pattern
			.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[task:(\\d+) (\\w+)\\] \\[(.+)\\](.*)");
	static final Pattern METHOD_TASK = Pattern
			.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[task:(\\d+) (.+)\\](.*)");
	static final Pattern METHOD_TRANSACTION_ANNOTATED = Pattern
			.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[txn:(\\d+) (\\w+)\\] \\[(.+)\\](.*)");
	static final Pattern METHOD_TRANSACTION = Pattern
			.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[txn:(\\d+) (.+?)\\](.*)");
	static final Pattern METHOD_ANNOTATED = Pattern
			.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[(\\w+)\\] \\[(.+)\\](.*)");
	static final Pattern METHOD_TASK_START = Pattern
			.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[(.+)\\]");
	static final Pattern TASK_START = Pattern
			.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[task:(\\d+) process\\] TaskI (\\d+): invoking(.*)");
	static final Pattern TASK_COMPLETE = Pattern
			.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[task:(\\d+) process\\] TaskI (\\d+): complete(.*)");
	static final Pattern ANY_LOG = Pattern
			.compile("(.+) : (\\w{3}) \\[(.*)\\](.*)");
	
	static final Pattern MODULE = Pattern.compile("(.+?) (.+?) (.+?)");
	//Nov 22 17:36:28 mn : DBG [openapi:10.90.0.154:94 1:11179:abccbb70 SAAS]: [txn:540 APSC] [APS::Provision::Workflow::Impl::addTransitionStep] <=== EXIT [0.002477]

}
