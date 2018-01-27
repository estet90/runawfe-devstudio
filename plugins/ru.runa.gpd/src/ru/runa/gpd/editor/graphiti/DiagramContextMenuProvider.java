package ru.runa.gpd.editor.graphiti;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.graphiti.ui.editor.DiagramEditorContextMenuProvider;
import org.eclipse.graphiti.ui.platform.IConfigurationProvider;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.internal.IObjectActionContributor;
import org.eclipse.ui.internal.ObjectActionContributorManager;

import ru.runa.gpd.editor.StructuredSelectionProvider;

public class DiagramContextMenuProvider extends DiagramEditorContextMenuProvider {
	private final IDiagramTypeProvider diagramTypeProvider;
    private final DiagramEditor diagramEditor;

    /**
     * @param viewer
     *            The EditPartViewer, for which the context-menu shall be
     *            displayed.
     * @param registry
     *            The action-registry, which contains the actions corresponding
     *            to the menu-items.
     * @param configurationProvider
     *            the configuration provider
     */
    public DiagramContextMenuProvider(EditPartViewer viewer, ActionRegistry registry, IConfigurationProvider configurationProvider, DiagramEditor diagramEditor) {
        super(viewer, registry, configurationProvider);
        this.diagramTypeProvider = configurationProvider.getDiagramTypeProvider();
        this.diagramEditor = diagramEditor;
    }

    @Override
    protected void addDefaultMenuGroupUndo(IMenuManager manager) {
    }

    @Override
    protected void addDefaultMenuGroupSave(IMenuManager manager) {
    }

    @Override
    protected void addDefaultMenuGroupEdit(IMenuManager manager) {
    }

    @Override
    protected void addDefaultMenuGroupPrint(IMenuManager manager) {
    }

    @Override
    protected void addDefaultMenuGroupRest(IMenuManager manager) {
        PictogramElement pes[] = getEditor().getSelectedPictogramElements();
        if (pes.length == 1) {
            Object object = getDiagramTypeProvider().getFeatureProvider().getBusinessObjectForPictogramElement(pes[0]);
            ISelectionProvider selectionProvider = new StructuredSelectionProvider(object);
            Set<IObjectActionContributor> alreadyContributed = new HashSet<>();
            ObjectActionContributorManager.getManager().contributeObjectActions(getEditor(), manager, selectionProvider, alreadyContributed);
        }
    }

    @Override
    protected void addAlignmentSubMenu(IMenuManager manager, String group) {
    }

    private IDiagramTypeProvider getDiagramTypeProvider() {
        return this.diagramTypeProvider;
    }

    private DiagramEditor getEditor() {
        return diagramEditor;
    }
}
