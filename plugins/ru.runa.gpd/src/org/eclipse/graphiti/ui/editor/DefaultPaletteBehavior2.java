package org.eclipse.graphiti.ui.editor;

import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.graphiti.ui.internal.editor.GFPaletteRoot2;

public class DefaultPaletteBehavior2 extends DefaultPaletteBehavior {

    public DefaultPaletteBehavior2(DiagramBehavior diagramBehavior) {
        super(diagramBehavior);
    }

    protected PaletteRoot createPaletteRoot() {
        return new GFPaletteRoot2(diagramBehavior.getDiagramTypeProvider());
    }

}
