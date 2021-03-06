package ru.runa.gpd.editor.graphiti.add;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;

import ru.runa.gpd.editor.graphiti.GaProperty;
import ru.runa.gpd.editor.graphiti.StyleUtil;
import ru.runa.gpd.editor.graphiti.layout.LayoutTextAnnotationFeature;
import ru.runa.gpd.lang.model.bpmn.TextAnnotation;

public class AddTextAnnotationFeature extends AddElementFeature {
    @Override
    public boolean canAdd(IAddContext context) {
        return true;
    }

    @Override
    public PictogramElement add(IAddContext context) {
        TextAnnotation textAnnotation = (TextAnnotation) context.getNewObject();
        Dimension bounds = getBounds(context);
        ContainerShape containerShape = Graphiti.getPeCreateService().createContainerShape(context.getTargetContainer(), true);
        Rectangle main = Graphiti.getGaService().createInvisibleRectangle(containerShape);
        Graphiti.getGaService().setLocationAndSize(main, context.getX(), context.getY(), bounds.width, bounds.height);
        final Shape lineShape = Graphiti.getPeCreateService().createShape(containerShape, false);
        Polyline polyline = Graphiti.getGaService().createPlainPolyline(lineShape,
                new int[] { LayoutTextAnnotationFeature.EDGE, 0, 0, 0, 0, 0, LayoutTextAnnotationFeature.EDGE, 0 });
        polyline.getProperties().add(new GaProperty(GaProperty.ID, LayoutTextAnnotationFeature.POLYLINE));
        polyline.setStyle(StyleUtil.getTextAnnotationPolylineStyle(getDiagram()));
        final Shape textShape = Graphiti.getPeCreateService().createShape(containerShape, false);
        MultiText descriptionText = Graphiti.getGaService().createMultiText(textShape, textAnnotation.getDescription());
        descriptionText.getProperties().add(new GaProperty(GaProperty.ID, GaProperty.DESCRIPTION));
        descriptionText.setVerticalAlignment(Orientation.ALIGNMENT_TOP);
        descriptionText.setStyle(StyleUtil.getTextStyle(getDiagram(), textAnnotation));
        // link both, the container as well as the text shape so direct editing
        // works together
        // with updating and property handling
        link(containerShape, textAnnotation);
        link(textShape, textAnnotation);
        Graphiti.getPeCreateService().createChopboxAnchor(containerShape);
        layoutPictogramElement(containerShape);
        return containerShape;
    }
}
