package com.parallels.utils.ui.log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import javax.management.Descriptor;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

import com.parallels.utils.ui.log.ViewContentProvider.LogEntryDescriptor;

/**
 * * This sample class demonstrates how to plug-in a new * workbench view. The
 * view shows data obtained from the * model. The sample creates a dummy model
 * on the fly, * but a real implementation would connect to the model *
 * available either in this or another plu g-in (e.g. the workspace). * The view
 * is connected to the model using a content provider. *
 * <p>
 * The view uses a label provider to define how model * objects should be pres
 * ented in the view. Each * view can present the same model objects using *
 * different labels and icons, if needed. Alternatively, * a single label
 * provider can be shared betwee n views * in order to ensure that objects of
 * the same type are * presented in the same way everywhere. *
 * <p>
 */
public class LogView extends ViewPart {
	/**
	 * * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.parallels.utils.ui.log.LogView";
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action action1;
	private Action action2;
	private Action doubleClickAction; 
	
	class TreeObject implements IAdaptable {
		private TreeParent parent;
		int order;
		LogEntryDescriptor desciptor;

		public TreeObject(LogEntryDescriptor desciptor) {
			this.desciptor = desciptor;
		}

		public String getName() {
			return desciptor != null ? 
					(desciptor.message != null && !desciptor.message.isEmpty()? 
							desciptor.message : (desciptor.method != null && !desciptor.method.isEmpty()? 
									desciptor.method : desciptor.raw)) : "<unknown>";
		}

		public void setParent(TreeParent parent) {
			this.parent = parent;
		}

		public TreeParent getParent() {
			return parent;
		}

		public String toString() {
			return getName();
		}

		public Object getAdapter(Class key) {
			return null;
		}
	}

	class TreeParent extends TreeObject {
		private ArrayList<TreeObject> children;

		public TreeParent(LogEntryDescriptor descriptor) {
			super(descriptor);
			children = new ArrayList<TreeObject>();
		}

		public void addChild(TreeObject child) {
			children.add(child);
			child.setParent(this);
		}

		public void removeChild(TreeObject child) {
			children.remove(child);
			child.setParent(null);
		}

		public TreeObject[] getChildren() {
			return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
		}

		public boolean hasChildren() {
			return children.size() > 0;
		}
	}

	class ViewLabelProvider extends LabelProvider {
		public String getText(Object obj) {
			return obj.toString();
		}

		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			if (obj instanceof TreeParent)
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			return PlatformUI.getWorkbench()
					.getSharedImages().getImage(imageKey);
		}
	}

	class NameSorter extends ViewerSorter {

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			if (e1 instanceof TreeObject && e2 instanceof TreeObject)
				return ((TreeObject) e1).order - ((TreeObject) e2).order;
			
			return super.compare(viewer, e1, e2);
		}
	}

	/** * The constructor. */
	public LogView() {
	}

	/**
	 * * This is a callback that will allow us * to create the viewer and
	 * initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new ViewContentProvider(this));
		viewer.setLabelProvider(new ViewLabelProvider(
				));
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite()); // Create the help context id for the
										// viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "POALogViewer.viewer");
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				LogView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager); // Other plug-ins can
														// contribute there
														// actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	void find(TreeParent root, Pattern pattern, Collection<TreeObject> out, boolean all) {
		for (TreeObject leaf : root.getChildren()) {
			if (pattern.matcher(leaf.desciptor.raw).matches()) {
				out.add(leaf);
				if(!all)
					return;
			}
			else if (leaf instanceof TreeParent) {
				find((TreeParent) leaf, pattern, out, all);
				if(!all && !out.isEmpty())
					return;
			}
		}
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
//				showMessage("Action 1 executed");
				// TODO: ctrf-f!!!!
//				String msg = ".*REST: DELETE /poa/service-users/5de01c67-332e-451d-891d-8df69178e43d.*";
//				String msg = ".*REST: .*|.*due to unregistering incoming link disabled.*";
//				String msg = ".*500 Internal Error.*";
//				String msg = ".*DELETE on http://endpoint.*";
				String msg = ".*DELETE FROM \"aps_resource_link\".*|.*DELETE on http://endpoint.a.kdonskov.aps.sw.ru/OwnCloud-ldap/users/21b5e57c-62ae-4b75-aa01-1db170759816 .*";//123f23a2-4ff2-4597-b197-196d3fe2e279 .*";
					//	"Nov 13 10:52:24 a : DBG [1:4485:b26ffb70:54 1:4758:ae3ffb70 SAAS]: [txn:17081 APSC] RQL: 'limit(0,1),serviceTemplateId=eq=4,implementing(http:%2F%2Fparallels.com%2Faps%2Ftypes%2Fpa%2FserviceTemplate%2F1.0)', AST: TOK_AND_FUNC(TOK_LIMIT_FUNC()TOK_EQ_FUNC( 'serviceTemplateId' '4')TOK_IMPLEMENTING_FUNC())";
				TreeParent root = ((ViewContentProvider) viewer.getContentProvider()).root;
				ArrayList<TreeObject> found = new ArrayList<TreeObject>();
				find(root, Pattern.compile(msg), found, true);
				System.err.println(found);
				viewer.setSelection(new StructuredSelection(found));
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		action2 = new Action() {
			public void run() {
				viewer.collapseAll();
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection).getFirstElement();
				showMessage("Double-click detected on " + obj.toString());
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(
				viewer.getControl().getShell(), "POA Log View", message);
	}

	/**
	 * * Passing the focus request t o the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}