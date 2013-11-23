package com.parallels.utils.ui.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import com.parallels.utils.ui.log.LogView.TreeObject;
import com.parallels.utils.ui.log.LogView.TreeParent;
//Nov 13 10:52:25 a : DBG [1:4485:b37beb70:56 1:4479:b10fbb70 chief]: [ServiceTemplate_impl::activateServiceTemplate] <=== EXIT [0.003866]
//Nov 13 10:52:25 a : DBG [1:4485:b37beb70:56 1:4604:b346cb70 AutoProvisioningManager]: [OpenAPI::STManagementOpenAPI_impl::activateST] <=== EXIT [0.004281]
//Nov 13 10:52:25 a : DBG [1:4485:b37beb70:56 1:4604:b346cb70 lib]: CORBA_1:4485:b37beb70 </1> OK
//Nov 13 10:52:25 a : DBG [SYSTEM 1:4485:b37beb70 OpenAPI]: [OpenAPI::PEMProxyMethod::execute] <=== EXIT [0.004842]
//Nov 13 10:52:25 a : DBG [SYSTEM 1:4485:b37beb70 OpenAPI]: [OpenAPI::TransformExceptions::process] <=== EXIT [0.004868]
//Nov 13 10:52:25 a : INF [SYSTEM 1:4485:b37beb70 OpenAPI]: <<<== Method 'pem.activateST' is executed, with status: { 'status' => 0, }
//Nov 13 10:52:25 a : DBG [SYSTEM 1:4485:b37beb70 OpenAPI]: [OpenAPI::LogMethodCalls::process] <=== EXIT [0.004928]
//Nov 13 10:52:26 a : DBG [SYSTEM 1:4510:b25fdb70 TaskManager]: [Tasks::impl::GlobalQueue::get_next] Runner 0xb28025d8 - task 57 (start_at: 1384325546, now: 1384325546)
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3665 1:4472:b1efdb70 lib]: CORBA_1:4510:b25fdb70 <1> IDL:Plesk/DBS/db_service:1.0::_is_a
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3665 1:4472:b1efdb70 lib]: CORBA_1:4510:b25fdb70 </1> OK
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3666 1:4472:b1efdb70 lib]: CORBA_1:4510:b25fdb70 <1> IDL:Plesk/DBS/db_service:1.0::create
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3666 1:4472:b1efdb70 Kernel]: [DBS::TransactionManager::getTransaction] Beginning transaction '17083', timeout 0
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3666 1:4472:b1efdb70 lib]: CORBA_1:4510:b25fdb70 </1> OK
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3667 1:4472:b1efdb70 lib]: CORBA_1:4510:b25fdb70 <1> IDL:Plesk/DBS/CosTransactions_Transaction:1.0::get_coordinator
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3667 1:4472:b1efdb70 lib]: CORBA_1:4510:b25fdb70 </1> OK
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3668 1:4472:b1efdb70 lib]: CORBA_1:4510:b25fdb70 <1> IDL:Plesk/DBS/CosTransactions_Transaction:1.0::get_txcontext
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3668 1:4472:b1efdb70 lib]: CORBA_1:4510:b25fdb70 </1> OK
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3669 1:4472:b1efdb70 lib]: CORBA_1:4510:b25fdb70 <1> IDL:Plesk/DBS/db_service:1.0::executeUpdate
//Nov 13 10:52:26a : DBG [1:4510:b25fdb70:3669 1:4472:b1efdb70 Kernel]: STMT [Con: 2902, 0xb2734880 txn:17083] ' UPDATE tm_tasks SET next_start =  ? , status =  ? , run_num =  ? , timeout =  ?WHERE task_id =  ?'($0 = '2013-11-13 10:52:26.000000', $1 = 'e', $2 = 930, $3 = 3600, $4 = 57)
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3669 1:4472:b1efdb70 lib]: CORBA_1:4510:b25fdb70 </1> OK
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3670 1:4472:b1efdb70 lib]: CORBA_1:4510:b25fdb70 <1> IDL:Plesk/DBS/CosTransactions_Transaction:1.0::commit
//Nov 1310:52:26 a : DBG [1:4510:b25fdb70:3670 1:4472:b1efdb70 Kernel]: [txn:17083 DBS::Transaction_impl::commit] Commiting transaction '17083'
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3670 1:4472:b1efdb70 lib]: [txn:17083 SimpleServantActivator::etherealize] ===> ENTRY
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3670 1:4472:b1efdb70 lib]: [txn:17083 SimpleServantActivator::etherealize] <=== EXIT [0.000122]
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3670 1:4472:b1efdb70 lib]: CORBA_1:4510:b25fdb70 </1> OK		
//Nov 13 10:52:26 a : DBG [SYSTEM 1:4510:b25fdb70 TaskManager]: [Tasks::impl::GlobalQueue::get_next] <=== EXIT [27.972546]
//Nov 13 10:52:26 a : DBG [SYSTEM 1:4510:b25fdb70 TaskManager]: [Tasks::impl::TaskImpl::process] ===> ENTRY
//Nov 13 10:52:26 a : DBG [SYSTEM 1:4510:b25fdb70 TaskManager]: [task:57 process] TaskI 57: invoking taskDUCollectorsMonitor on SCREF:QmailCluster:0
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3671 1:4659:b2946b70 lib]: CORBA_1:4510:b25fdb70 <1> IDL:psa.parallels.com/ExecuteTask:1.0::doExecuteTask
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3671 1:4659:b2946b70 QmailCluster]: [task:57 doExecuteTask] task execution requested: taskDUCollectorsMonitor
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3671 1:4659:b2946b70 QmailCluster]: [task:57 doExecuteTask] SC task execution requested: taskDUCollectorsMonitor
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3671 1:4659:b2946b70 QmailCluster]: [task:57 executeNamedFunction] Function execution requested: taskDUCollectorsMonitor
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3671 1:4659:b2946b70 QmailCluster]: [task:57 QmailCluster::sc_impl::taskDUCollectorsMonitor] ===> ENTRY
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3671 1:4472:aeecdb70 Kernel]: [task:57 DBS::TransactionManager::getTransaction] Beginning transaction '17084', timeout 0
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3671 1:4472:aeecdb70 Kernel]: STMT [Con: 2902, 0xb2734880 txn:17084 task:57] ' SELECT du_task_id, config_id FROM cqmail_configurations'
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3671 1:4472:aeecdb70 Kernel]: [txn:17084 task:57 DBS::Transaction_impl::rollback_impl] Rolling back transaction '17084'
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3671 1:4472:aeecdb70 Kernel]: [txn:17084 task:57 DBS::DSNResourceCoordinator::rollback] ===> ENTRY
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3671 1:4472:aeecdb70 Kernel]: [txn:17084 task:57 DBS::DSNResourceCoordinator::rollback] <=== EXIT [0.000156]
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3671 1:4472:aeecdb70 lib]: [txn:17084 task:57 SimpleServantActivator::etherealize] ===> ENTRY
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3671 1:4472:aeecdb70 lib]: [txn:17084 task:57 SimpleServantActivator::etherealize] <=== EXIT [0.000097]
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3671 1:4659:b2946b70 QmailCluster]: [task:57 QmailCluster::sc_impl::taskDUCollectorsMonitor] <=== EXIT [0.003542]
//Nov 13 10:52:26 a : DBG [1:4510:b25fdb70:3671 1:4659:b2946b70 lib]: CORBA_1:4510:b25fdb70 </1> OK		
//Enry/Leave method
//(Nov 13 10:52:26 a) : (DBG) [(1:4510:b25fdb70:3671) (1:4472:aeecdb70 Kernel)]: [(txn:17084) (task:57) (DBS::DSNResourceCoordinator::rollback)] ===> (ENTRY)
//(Nov 13 10:52:26 a) : (DBG) [(1:4510:b25fdb70:3671) (1:4472:aeecdb70 Kernel)]: [(txn:17084) (task:57) (DBS::DSNResourceCoordinator::rollback)] <=== (EXIT) [(0.000156)]
//(Nov 13 10:52:26a) : (DBG) [(SYSTEM) (1:4510:b25fdb70) (TaskManager)]: [(task:57) process] TaskI 57: complete
class ViewContentProvider implements IStructuredContentProvider,ITreeContentProvider {
	// (Nov 13 10:52:26 a) : (DBG) [(1:4510:b25fdb70:3671 1:4472:aeecdb70
	// Kernel)]: [(txn:17084) (task:57) (DBS::DSNResourceCoordinator::rollback)]
	// ===> (ENTRY)
	// static final Pattern METHOD_ENTER_EXIT_TRANSACTION_TASK =// Pattern.compile("(.+) : (\\w{3}) [(.+)]: [txn:(\\d+) task:(\\d+) (.*)] ===> (ENTRY|EXIT)");
	static final Pattern DO_NOT_PROCESS = Pattern.compile(
			  "(.*)\\[(.*)Tasks::impl::GlobalQueue::(.*)\\] (.*)|"
			+ "(.*)\\[(.*)Tasks::impl::TaskImpl::process\\](.*)|"
			+ "(.*)\\[(.*)Tasks::impl::TaskManager_impl::(.*)\\](.*)|"
			+ "(.*)\\[(.*)SimpleServantActivator::etherealize\\](.*)|"
			+ "(.*)\\]: STMT \\[Con:(.*)|"+ "(.*)\\]: CORBA_1:(.*)|"
			+ "(.*)\\[OpenAPI::(.*)|"
			+ "(.*)\\[DBS::TransactionManager::getTransaction\\](.*)|"
			+ "(.*)\\[txn:(\\d+) DBS::Transaction_impl::commit\\](.*)"
			);
	static final Pattern METHOD_ENTER = Pattern.compile("(.+) ===> ENTRY");
	// Nov 13 10:52:23 a : DBG [SYSTEM 1:4510:b22fab70 TaskManager]:	
	// [Tasks::impl::GlobalQueue::get_next] Runner 0x85ba148 - task 1229
	// (start_at: 1384325543, now: 1384325543)	
	static final Pattern RUNNER_ENTER = Pattern.compile("(.+) Runner (.+) - task (\\d+) \\(start_at: (\\d+), now: (\\d+)\\)");
	static final Pattern METHOD_EXIT = Pattern.compile("(.+) <=== EXIT \\[(.*)\\]");
	static final Pattern METHOD_EXIT_ERROR = Pattern.compile("(.+) <=== EXIT \\(by exception\\) \\[(.*)\\]");
	static final Pattern METHOD_TRANSACTION_TASK = Pattern.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[txn:(\\d+) task:(\\d+) (.+)\\](.*)");
	
	static final Pattern METHOD_TASK_ANNOTATED = Pattern.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[task:(\\d+) (\\w+)\\] \\[(.+)\\](.*)");
	static final Pattern METHOD_TASK = Pattern.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[task:(\\d+) (.+)\\](.*)");
	
	static final Pattern METHOD_TRANSACTION_ANNOTATED = Pattern.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[txn:(\\d+) (\\w+)\\] \\[(.+)\\](.*)");
	static final Pattern METHOD_TRANSACTION = Pattern.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[txn:(\\d+) (.+?)\\](.*)");
	////Nov 13 10:52:24 a : DBG [1:4485:b23feb70:49 1:4472:b29ffb70 Kernel]: STMT [Con: 2902, 0xb2734880] ' SELECT rt.rt_id AS rt_id, rc.class_id AS rc_id, rc.sc_id AS sc_id, rt.restype_name AS name, rt.system AS system, rt.description AS description FROM resource_types rt JOIN resource_classes rc ON (rc.class_id = rt.class_id)  WHERE rt_id = ?'($0 = 1000245)
	//static final Pattern STATEMENT_CONNECTION = Pattern
	//.compile("(.+) : (\\w{3}) \\[(.*)\\]: STMT \\[Con: (\\d+), (.+)\\](.*)");
	////Nov 13 10:52:24 a : DBG [1:4485:b26ffb70:54 1:4472:b29ffb70 Kernel]: STMT [Con: 2902, 0xb2734880 txn:17081] ' SELECT st.st_id AS st_id, st.version AS version, st.name AS name, st.description AS description, st.owner_id AS owner_id, st.st_type AS category, st.for_sale AS active, st.auto_provisioning AS auto_provisioning, st.subs_visibility_policy AS subs_visibility_policy, (SELECT 1 FROM dual WHERE EXISTS     (SELECT 1 FROM st_resources str WHERE str.st_id = st.st_id AND str.sub_limit = -2)) AS limits_defined FROM service_templates st WHERE st_id = ?'($0 = 4)
	//static final Pattern STATEMENT_CONNECTION_TASK = Pattern
	//.compile("(.+) : (\\w{3}) \\[(.*)\\]: STMT \\[Con: (\\d+), (.+) txn:(\\d+)\\](.*)");
	// Nov 13 10:52:23 a : DBG [SYSTEM 1:4510:b18f0b70 TaskManager]:	
	// [Tasks::impl::TaskImpl::process] ===> ENTRY
	static final Pattern METHOD_TASK_START = Pattern.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[(.+)\\]");
	// Nov 13 10:52:23 a : DBG [SYSTEM 1:4510:b22fab70 TaskManager]: [task:1229 process] TaskI 1229: invoking scheduleProxiesConfigurationUpdate on SCREF:proxies_configuration:0"
	// Nov 13 10:52:23 a : DBG [SYSTEM 1:4510:b22fab70 TaskManager]: [task:1229// process] TaskI 1229: complete
	static final Pattern TASK_START = Pattern.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[task:(\\d+) process\\] TaskI (\\d+): invoking(.*)");
	static final Pattern TASK_COMPLETE = Pattern.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[task:(\\d+) process\\] TaskI (\\d+): complete(.*)");
	static final Pattern ANY_LOG = Pattern.compile("(.+) : (\\w{3}) \\[(.*)\\](.*)");
	/** *  */private final LogView logView;/** * @param logView */ViewContentProvider(LogView logView) {this.logView = logView;}TreeParent root;

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

	public void dispose() {
	}

	public Object[] getElements(Object parent) {
		if (parent.equals(logView.getViewSite())) {
			if (root == null)
				initialize();
			return getChildren(root);
		}
		return getChildren(parent);
	}

	public Object getParent(Object child) {
		if (child instanceof TreeObject) {
			return ((TreeObject) child).getParent();
		}
		return null;
	}

	public Object[] getChildren(Object parent) {
		if (parent instanceof TreeParent) {
			return ((TreeParent) parent).getChildren();
		}
		return new Object[0];
	}

	public boolean hasChildren(Object parent) {
		if (parent instanceof TreeParent)
			return ((TreeParent) parent).hasChildren();
		return false;
	}
	/*
	 *  * We will set up a dummy model to initialize tree heararchy. In a real *
	 * code, you will connect to a real model and expose its hierarchy.
	 */

	class LogEntryDescriptor {
		String date;
		String level;
		String module;
		String txn;
		String task;
		String method;
		String text;
	}

	private LogEntryDescriptor getCallDescriptor(String line) {
		LogEntryDescriptor d = new LogEntryDescriptor();
		Matcher matcher = METHOD_TRANSACTION_TASK.matcher(line);
		if (matcher.matches()) {
			d.date = matcher.group(1);
			d.level = matcher.group(2);
			d.module = matcher.group(3);
			d.txn = matcher.group(4);
			d.task = matcher.group(5);
			d.method = matcher.group(6);
			d.text = matcher.group(7);
		} else {
			matcher = METHOD_TASK_ANNOTATED.matcher(line);
			if (matcher.matches()) {
				d.date = matcher.group(1);
				d.level = matcher.group(2);
				d.module = matcher.group(3);
				d.task = matcher.group(4);
				d.method = matcher.group(6);
				d.text = matcher.group(7);
			} else {
				matcher = METHOD_TASK.matcher(line);
				if (matcher.matches()) {
					d.date = matcher.group(1);
					d.level = matcher.group(2);
					d.module = matcher.group(3);
					d.task = matcher.group(4);
					d.method = matcher.group(5);
					d.text = matcher.group(6);
				} else {
					matcher = METHOD_TRANSACTION_ANNOTATED.matcher(line);
					if (matcher.matches()) {
						d.date = matcher.group(1);
						d.level = matcher.group(2);
						d.module = matcher.group(3);
						d.txn = matcher.group(4);
						d.method = matcher.group(6);
						d.text = matcher.group(7);
					} else {
						matcher = METHOD_TRANSACTION.matcher(line);
						if (matcher.matches()) {
							d.date = matcher.group(1);
							d.level = matcher.group(2);
							d.module = matcher.group(3);
							d.txn = matcher.group(4);
							d.method = matcher.group(5);
							d.text = matcher.group(6);
						} else {
							matcher = METHOD_TASK_START.matcher(line);
							if (matcher.matches()) {
								d.date = matcher.group(1);
								d.level = matcher.group(2);
								d.module = matcher.group(3);
								d.method = matcher.group(4);
							} else {
								// matcher = ANY_LOG.matcher(line);
								// if (matcher.matches()) {
								// d.date = matcher.group(1);//d.level =
								// matcher.group(2);//d.method =
								// matcher.group(4);//}
								// else {d.method = line;
								d.text = line;
							}
						}
					}
				}
			}}
		return d;
	}

	HashMap<String, Stack<TreeParent>> callStacks = new HashMap<String, Stack<TreeParent>>();

	private Stack<TreeParent> getStack(String taskID) {
		Stack<TreeParent> stack = callStacks.get(taskID);
		if (stack == null) {
			stack = new Stack<TreeParent>();
			callStacks.put(taskID, stack);
		}
		return stack;
	}

	//	void closeCall(LogEntryDescriptor callDescriptor) {
	//		Stack<TreeParent> stack = getStack(callDescriptor.task);
	//		ArrayList<TreeParent> lostEntries = new ArrayList<TreeParent>();
	//		for (int i = stack.size() - 1; i >= 0; i--) {
	//			TreeParent candidate = stack.get(i);
	//			lostEntries.add(candidate);
	//			if (candidate.getName().equals(callDescriptor.method)) {
	//				break;
	//			}
	//		}
	//		if (lostEntries.size() == stack.size()
	//				&& !stack.get(0).getName().equals(callDescriptor.method)) {
	//			throw new RuntimeException(String.format(
	//					"Could not find beginning of '%s'", callDescriptor.method));
	//		}
	//		if (lostEntries.size() > 1) {
	//			System.out.println("LONG JUMP:" + callDescriptor.method + "->"
	//					+ lostEntries);
	//		}
	//		stack.removeAll(lostEntries);
	//	}

	void closeCall(LogEntryDescriptor callDescriptor) {
		Stack<TreeParent> stack = getStack(callDescriptor.task);
		ArrayList<TreeParent> lostEntries = new ArrayList<TreeParent>();
		for (int i = stack.size() - 1; i >= 0; i--) {
			TreeParent candidate = stack.get(i);
			lostEntries.add(candidate);
			if (candidate.getName().equals(callDescriptor.method)) {
				break;
			}
//			else 
//				System.err.println("oops");
		}
		if (lostEntries.size() == stack.size()
				&& !stack.get(0).getName().equals(callDescriptor.method)) {// throw
			// new
			// RuntimeException(String.format("Could not find beginning of '%s'",
			// callDescriptor.method));
			System.err.println("ORPHAN RETURN detected: "
					+ callDescriptor.method);
			return;
		}
		if (lostEntries.size() > 1) {
			System.out.println("LONG RETURN executed: " + callDescriptor.method
					+ "->" + lostEntries);
		}
		stack.removeAll(lostEntries);
	}

	private void initialize() {
		root = logView.new TreeParent("");
		getStack(null).push(root);
		String logfilepath = "/home/kdonskov/workspace/apsc/2.0/poa.debug.log";// b181//String
		// logfilepath
		// =
		// "/home/kdonskov/workspace/POALogViewer/b.poa.debug.log";//b20...
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					logfilepath)));
			int order = -1;
			String line = null;
			while ((line = reader.readLine()) != null) {
				order++;
				if (line.isEmpty())
					continue;
				Matcher skipIt = DO_NOT_PROCESS.matcher(line);
				if (skipIt.matches()) {
					continue;
				}
				Matcher matcher = METHOD_ENTER.matcher(line);
				if (matcher.matches()) {
					LogEntryDescriptor callDescriptor = getCallDescriptor(matcher
							.group(1));
					TreeParent method = logView.new TreeParent(
							callDescriptor.method);
					method.raw = line;
					method.order = order;
					Stack<TreeParent> stack = getStack(callDescriptor.task);
					stack.peek().addChild(method);
					stack.push(method);
					continue;
				}
				matcher = RUNNER_ENTER.matcher(line);
				if (matcher.matches()) {
					LogEntryDescriptor callDescriptor = getCallDescriptor(matcher
							.group(1));
					TreeParent method = logView.new TreeParent(
							callDescriptor.method);
					method.raw = line;
					method.order = order;
					Stack<TreeParent> stack = getStack(callDescriptor.task);
					stack.peek().addChild(method);
					stack.push(method);
					continue;
				}
				matcher = TASK_START.matcher(line);
				if (matcher.matches()) {
					LogEntryDescriptor callDescriptor = getCallDescriptor(line);
					TreeParent method = logView.new TreeParent(
							callDescriptor.method);
					method.raw = line;
					method.order = order;
					getStack(null/* TODO: find scheduler and attach */).peek()
					.addChild(method);
					getStack(callDescriptor.task).push(method);
					continue;
				}
				matcher = METHOD_EXIT.matcher(line);
				if (matcher.matches()) {// check pop the same method
					LogEntryDescriptor callDescriptor = getCallDescriptor(matcher
							.group(1));
					closeCall(callDescriptor);
					continue;
				}
				matcher = METHOD_EXIT_ERROR.matcher(line);
				if (matcher.matches()) {
					LogEntryDescriptor callDescriptor = getCallDescriptor(matcher
							.group(1));
					closeCall(callDescriptor);
					continue;
				}
				matcher = TASK_COMPLETE.matcher(line);
				if (matcher.matches()) {
					LogEntryDescriptor callDescriptor = getCallDescriptor(line);
					closeCall(callDescriptor);
					continue;
				}
				LogEntryDescriptor callDescriptor = getCallDescriptor(line);
				TreeObject unclassified = logView.new TreeObject(
						callDescriptor.text);
				unclassified.raw = line;
				unclassified.order = order;
				try {
					getStack(callDescriptor.task).peek().addChild(unclassified);
				} catch (Exception e) {
					System.err.println("ERROR:" + line);
					break;
				}
			}
			reader.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

//C:\Users\Public\{a923b653-b6d1-4c0c-948a-5366f2aad0ff}.vdi                                                                        d 65001   73351458816     Col 0     27%   17:54
//\[(.+)\\]");// Nov 13 10:52:23 a : DBG [SYSTEM 1:4510:b22fab70 TaskManager]: [task:1229// process] TaskI 1229: invoking scheduleProxiesConfigurationUpdate on// SCREF:prox
//ies_configuration:0// Nov 13 10:52:23 a : DBG [SYSTEM 1:4510:b22fab70 TaskManager]: [task:1229// process] TaskI 1229: completestatic final Pattern TASK_START = Pattern
//.compile("(.+) : (\\w{3}) \\[(.*)\\]: \\[task:(\\d+) process\\] TaskI (\\d+): invoking(.*)");static final Pattern TASK_COMPLETE = Pattern.compile("(.+) : (\\w{3}) \\[(.*
//)\\]: \\[task:(\\d+) process\\] TaskI (\\d+): complete(.*)");static final Pattern ANY_LOG = Pattern.compile("(.+) : (\\w{3}) \\[(.*)\\](.*)");/** *  */private
//final LogView logView;/** * @param logView */ViewContentProvider(LogView logView) {this.logView = logView;}TreeParent root;public void inputChanged(Viewer v,
// Object oldInput, Object newInput) {}public void dispose() {}public Object[] getElements(Object parent) {if (parent.equals(logView.getViewSite())) {if (root ==
//null)initialize();return getChildren(root);}return getChildren(parent);}public Object getParent(Object child) {if (child instanceof TreeObject) {retur
//n ((TreeObject) child).getParent();}return null;}public Object[] getChildren(Object parent) {if (parent instanceof TreeParent) {return ((TreeParent) parent).ge
//tChildren();}return new Object[0];}public boolean hasChildren(Object parent) {if (parent instanceof TreeParent)return ((TreeParent) parent).hasChildren();re
//turn false;}/* * We will set up a dummy model to initialize tree heararchy. In a real * code, you will connect to a real model and expose its hierarchy. */class Log
//EntryDescriptor {String date;String level;String module;String txn;String task;String method;String text;}private LogEntryDescriptor getCallDescriptor(
//String line) {LogEntryDescriptor d = new LogEntryDescriptor();Matcher matcher = METHOD_TRANSACTION_TASK.matcher(line);if (matcher.matches()) {d.date = matcher.grou
//p(1);d.level = matcher.group(2);d.module = matcher.group(3);d.txn = matcher.group(4);d.task = matcher.group(5);d.method = matcher.group(6);d.text = match
//er.group(7);} else {matcher = METHOD_TASK.matcher(line);if (matcher.matches()) {d.date = matcher.group(1);d.level = matcher.group(2);d.module = matcher
//.group(3);d.task = matcher.group(4);d.method = matcher.group(5);d.text = matcher.group(6);} else {matcher = METHOD_TRANSACTION.matcher(line);if (mat
//cher.matches()) {d.date = matcher.group(1);d.level = matcher.group(2);d.module = matcher.group(3);d.txn = matcher.group(4);d.method = matcher.group
//(5);d.text = matcher.group(6);} else {matcher = METHOD_TASK_START.matcher(line);if (matcher.matches()) {d.date = matcher.group(1);d.level =
//matcher.group(2);d.module = matcher.group(3);d.method = matcher.group(4);} else {//matcher = ANY_LOG.matcher(line);//if (matcher.matches()) {/
///d.date = matcher.group(1);//d.level = matcher.group(2);//d.method = matcher.group(4);//} else {d.method = line;d.text = line;//
//}}}}}return d;}HashMap<String, Stack<TreeParent>> callStacks = new HashMap<String, Stack<TreeParent>>();private Stack<TreeParent> getStack(Stri
//ng taskID) {Stack<TreeParent> stack = callStacks.get(taskID);if (stack == null) {stack = new Stack<TreeParent>();callStacks.put(taskID, stack);}return stack;
//}void closeCall(LogEntryDescriptor callDescriptor) {Stack<TreeParent> stack = getStack(callDescriptor.task);ArrayList<TreeParent> lostEntries = new ArrayList<TreePar
//ent>();for (int i = stack.size() - 1; i >= 0; i--) {TreeParent candidate = stack.get(i);lostEntries.add(candidate);if (candidate.getName().equals(callDescriptor.m
//ethod)) {break;}}if (lostEntries.size() == stack.size() && !stack.get(0).getName().equals(callDescriptor.method) ) {//throw new RuntimeException(String.format
//("Could not find beginning of '%s'", callDescriptor.method));System.err.println("ORPHAN RETURN detected: " + callDescriptor.method);} if(lostEntries.size()>1) { Sy
//stem.out.println("LONG RETURN executed: " + callDescriptor.method + "->" + lostEntries); }stack.removeAll(lostEntries);}private void initialize() {root = logView.n
//ew TreeParent("");getStack(null).push(root);//String logfilepath = "/home/kdonskov/workspace/POALogViewer/a.poa.debug.log";//b181String logfilepath = "/home/kdonskov/
//workspace/POALogViewer/poa.debug.log";//b20...try {BufferedReader reader = new BufferedReader(new FileReader(new File(logfilepath)));int order = -1;St
//ring line = null;while ((line = reader.readLine()) != null) {order++;if (line.isEmpty())continue;Matcher skipIt = DO_NOT_PROCESS.matcher(line)
//;if (skipIt.matches()) {continue;}Matcher matcher = METHOD_ENTER.matcher(line);if (matcher.matches()) {LogEntryDescriptor callDescriptor = getCa
//llDescriptor(matcher.group(1));TreeParent method = logView.new TreeParent(callDescriptor.method);method.raw = line;method.order = order;S
//tack<TreeParent> stack = getStack(callDescriptor.task);stack.peek().addChild(method);stack.push(method);continue;}matcher = RUNNER_ENTER.matcher(line
//);if (matcher.matches()) {LogEntryDescriptor callDescriptor = getCallDescriptor(matcher.group(1));TreeParent method = logView.new TreeParent(cal
//lDescriptor.method);method.raw = line;method.order = order;Stack<TreeParent> stack = getStack(callDescriptor.task);stack.peek().addChild(method);st
//ack.push(method);continue;}matcher = TASK_START.matcher(line);if (matcher.matches()) {LogEntryDescriptor callDescriptor = getCallDescriptor(line);
//TreeParent method = logView.new TreeParent(callDescriptor.method);method.raw = line;method.order = order;getStack(null/* TODO: find scheduler and att
//ach */).peek().addChild(method);getStack(callDescriptor.task).push(method);continue;}matcher = METHOD_EXIT.matcher(line);if (matcher.matches(
//)) {// check pop the same methodLogEntryDescriptor callDescriptor = getCallDescriptor(matcher.group(1));closeCall(callDescriptor);continue;}
//matcher = METHOD_EXIT_ERROR.matcher(line);if (matcher.matches()) {LogEntryDescriptor callDescriptor = getCallDescriptor(matcher.group(1));closeCall