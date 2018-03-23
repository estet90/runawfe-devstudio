package ru.runa.gpd.editor.graphiti;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.KeyHandler;
import org.eclipse.graphiti.ui.editor.DiagramBehavior;
import org.eclipse.graphiti.ui.editor.DiagramEditor;

public class CustomDiagramBehavior extends DiagramBehavior {
    private KeyHandler keyHandler;

	public CustomDiagramBehavior(DiagramEditor diagramEditor) {
		super(diagramEditor);
	}

    protected ContextMenuProvider createContextMenuProvider() {
        return new DiagramContextMenuProvider(getDiagramContainer().getGraphicalViewer(),
				getDiagramContainer().getActionRegistry(),
				getConfigurationProvider(), getEditor());
    }

    
    @Override
    protected KeyHandler getCommonKeyHandler() {
    	// TODO graphiti update
        if (keyHandler == null) {
            keyHandler = new DiagramActionBarContributor().createKeyHandler(getEditor().getActionRegistry());
        }
        return keyHandler;
    }
    
    private DiagramEditor getEditor() {
    	return (DiagramEditor) getDiagramContainer();
    }
}
