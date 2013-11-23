package com.parallels.utils.ui.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.parallels.utils.ui.log.LogView.TreeObject;
import com.parallels.utils.ui.log.LogView.TreeParent;

class ViewContentProvider implements IStructuredContentProvider,ITreeContentProvider {
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
		String message;
		String raw;
		@Override
		public String toString() {
			return raw;
		}
		
	}

	private LogEntryDescriptor getCallDescriptor(String line) {
		LogEntryDescriptor d = new LogEntryDescriptor();
		d.raw = line;
		Matcher matcher = Constants.METHOD_TRANSACTION_TASK.matcher(line);
		if (matcher.matches()) {
			d.date = matcher.group(1);
			d.level = matcher.group(2);
			d.module = matcher.group(3);
			d.txn = matcher.group(4);
			d.task = matcher.group(5);
			d.method = matcher.group(6);
			d.message = matcher.group(7);
			return d;
		}
		matcher = Constants.METHOD_TASK_ANNOTATED.matcher(line);
		if (matcher.matches()) {
			d.date = matcher.group(1);
			d.level = matcher.group(2);
			d.module = matcher.group(3);
			d.task = matcher.group(4);
			d.method = matcher.group(6);
			d.message = matcher.group(7);
			return d;
		} 
		matcher = Constants.METHOD_TASK.matcher(line);
		if (matcher.matches()) {
			d.date = matcher.group(1);
			d.level = matcher.group(2);
			d.module = matcher.group(3);
			d.task = matcher.group(4);
			d.method = matcher.group(5);
			d.message = matcher.group(6);
			return d;
		} 
		matcher = Constants.METHOD_TRANSACTION_ANNOTATED.matcher(line);
		if (matcher.matches()) {
			d.date = matcher.group(1);
			d.level = matcher.group(2);
			d.module = matcher.group(3);
			d.txn = matcher.group(4);
			d.method = matcher.group(6);
			d.message = matcher.group(7);
			return d;
		} 
		matcher = Constants.METHOD_ANNOTATED.matcher(line);
		if (matcher.matches()) {
			d.date = matcher.group(1);
			d.level = matcher.group(2);
			d.module = matcher.group(3);
			d.method = matcher.group(5);
			d.message = matcher.group(6);
			return d;
		} 		
		matcher = Constants.METHOD_TRANSACTION.matcher(line);
		if (matcher.matches()) {
			d.date = matcher.group(1);
			d.level = matcher.group(2);
			d.module = matcher.group(3);
			d.txn = matcher.group(4);
			d.method = matcher.group(5);
			d.message = matcher.group(6);
			return d;
		} 
		matcher = Constants.METHOD_TASK_START.matcher(line);
		if (matcher.matches()) {
			d.date = matcher.group(1);
			d.level = matcher.group(2);
			d.module = matcher.group(3);
			d.method = matcher.group(4);
			return d;
		}
		
		d.message = line;		

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

	void endCall(LogEntryDescriptor callDescriptor) {
		Stack<TreeParent> stack = getStack(callDescriptor.task);
//		ArrayList<TreeParent> lostEntries = new ArrayList<TreeParent>();
		if(stack.isEmpty()){
			System.out.println("RETURN without call: " + callDescriptor.method);
			return;
		}
		int tail = stack.size() -1;
		for (int i = tail; i >= 0; i--) {
			TreeParent candidate = stack.get(i);
//			lostEntries.add(candidate);
			if (candidate.desciptor != null &&candidate.desciptor.method.equals(callDescriptor.method)) {
				stack.remove(i);//lostEntries.add(candidate);
				break;
			}
			else if(i==0)
				System.err.println("oops: " + callDescriptor.method);
		}
//		if (lostEntries.size() == stack.size()
//				&& !stack.get(0).getName().equals(callDescriptor.method)) {// throw
//			// new
//			// RuntimeException(String.format("Could not find beginning of '%s'",
//			// callDescriptor.method));
//			System.err.println("ORPHAN RETURN detected: "
//					+ callDescriptor.method);
//			return;
//		}
//		if (lostEntries.size() > 1) {
//			System.out.println("LONG RETURN executed: " + callDescriptor.method
//					+ "->" + lostEntries);
//		}
//		stack.removeAll(lostEntries);
	}
	
	private TreeParent findParent(Stack<TreeParent> stack, LogEntryDescriptor entry) {
		TreeParent tail;
		TreeParent parent = tail = stack.peek();
		if(entry.txn != null) {
			while(parent != null && parent.desciptor!=null) {
				if(entry.txn.equals(parent.desciptor.txn))
					return parent;
				parent = parent.getParent();
			}
		}
		return tail;
	}
	
	private void initialize() {
		root = logView.new TreeParent(null);
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
				Matcher skipIt = Constants.DO_NOT_PROCESS.matcher(line);
				if (skipIt.matches()) {
					continue;
				}
				Matcher matcher = Constants.METHOD_ENTER.matcher(line);
				if (matcher.matches()) {
					LogEntryDescriptor callDescriptor = getCallDescriptor(matcher.group(1));
					TreeParent method = logView.new TreeParent(callDescriptor);
					method.order = order;
					
					Stack<TreeParent> stack = getStack(callDescriptor.task);
					findParent(stack, callDescriptor).addChild(method);
					stack.push(method);
//					stack.peek().addChild(method);
//					stack.push(method);
					continue;
				}
				matcher = Constants.RUNNER_ENTER.matcher(line);
				if (matcher.matches()) {
					LogEntryDescriptor callDescriptor = getCallDescriptor(matcher
							.group(1));
					TreeParent method = logView.new TreeParent(callDescriptor);
					method.order = order;
					
					Stack<TreeParent> stack = getStack(callDescriptor.task);
					findParent(stack, callDescriptor).addChild(method);
					stack.push(method);
//					
//					stack.peek().addChild(method);
//					
					continue;
				}
				matcher = Constants.TASK_START.matcher(line);
				if (matcher.matches()) {
					LogEntryDescriptor callDescriptor = getCallDescriptor(line);
					TreeParent method = logView.new TreeParent(callDescriptor);
					method.order = order;
					
					getStack(null).peek().addChild(method);
					getStack(callDescriptor.task).push(method);
					
//					getStack(null).peek().addChild(method);
//					getStack(callDescriptor.task).push(method);
					continue;
				}
				matcher = Constants.METHOD_EXIT.matcher(line);
				if (matcher.matches()) {// check pop the same method
					LogEntryDescriptor callDescriptor = getCallDescriptor(matcher.group(1));
					endCall(callDescriptor);
					continue;
				}
				matcher = Constants.METHOD_EXIT_ERROR.matcher(line);
				if (matcher.matches()) {
					LogEntryDescriptor callDescriptor = getCallDescriptor(matcher.group(1));
					endCall(callDescriptor);
					continue;
				}
				matcher = Constants.TASK_COMPLETE.matcher(line);
				if (matcher.matches()) {
					LogEntryDescriptor callDescriptor = getCallDescriptor(line);
					endCall(callDescriptor);
					continue;
				}
				LogEntryDescriptor callDescriptor = getCallDescriptor(line);
				TreeObject unclassified = logView.new TreeObject(callDescriptor);
				unclassified.order = order;
				try {
					Stack<TreeParent> stack = getStack(callDescriptor.task);
					findParent(stack, callDescriptor).addChild(unclassified);
					
//					getStack(callDescriptor.task).peek().addChild(unclassified);
				} catch (Exception e) {
					System.err.println("ERROR:" + line);
					e.printStackTrace();
					break;
				}
			}
			reader.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}