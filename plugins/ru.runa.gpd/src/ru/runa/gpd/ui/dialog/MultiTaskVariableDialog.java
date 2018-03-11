package ru.runa.gpd.ui.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ru.runa.gpd.Localization;
import ru.runa.gpd.util.VariableMapping;

import com.google.common.base.Strings;

public class MultiTaskVariableDialog extends Dialog {
    private final List<String> processVariables;
    private String processVariable = "";
    private String formVariable = "";
    private final VariableMapping oldMapping;

    protected MultiTaskVariableDialog(List<String> processVariables, VariableMapping oldMapping) {
        super(Display.getCurrent().getActiveShell());
        this.processVariables = processVariables;
        this.oldMapping = oldMapping;
        if (oldMapping != null) {
            this.processVariable = oldMapping.getName();
            this.formVariable = oldMapping.getMappedName();
        }
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        String message = oldMapping != null ? Localization.getString("Subprocess.UpdateVariableMapping") : Localization
                .getString("Subprocess.CreateVariableMapping");
        newShell.setText(message);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite area = (Composite) super.createDialogArea(parent);
        GridLayout layout = new GridLayout(1, false);
        area.setLayout(layout);

        final Composite composite = new Composite(area, SWT.NONE);
        final GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        composite.setLayout(gridLayout);
        composite.setLayoutData(new GridData());

        Label labelProcessVariable = new Label(composite, SWT.NONE);
        labelProcessVariable.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
        labelProcessVariable.setText(Localization.getString("Subprocess.ProcessVariableName") + ":");

        Composite varComposite = new Composite(composite, SWT.NONE);
        varComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        varComposite.setLayout(new GridLayout(2, false));
        final Text varName = new Text(varComposite, SWT.READ_ONLY | SWT.BORDER);
        GridData processVariableTextData = new GridData(GridData.FILL_HORIZONTAL);
        processVariableTextData.minimumWidth = 200;
        varName.setLayoutData(processVariableTextData);
        varName.setText(getProcessVariable());
        Button selectButton = new Button(varComposite, SWT.PUSH);
        selectButton.setText("...");
        selectButton.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
        selectButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String result = new ChooseVariableNameDialog(processVariables).openDialog();
                if (result != null) {
                    processVariable = result;
                    varName.setText(processVariable);
                }
            }
        });

        Label labelTaskVariable = new Label(composite, SWT.NONE);
        labelTaskVariable.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
        labelTaskVariable.setText(Localization.getString("MultiTask.FormVariableName") + ":");
        final Text taskVariableField = new Text(composite, SWT.BORDER);
        GridData subprocessVariableTextData = new GridData(GridData.FILL_HORIZONTAL);
        subprocessVariableTextData.minimumWidth = 200;
        taskVariableField.setLayoutData(subprocessVariableTextData);
        taskVariableField.setText(getFormVariable());
        taskVariableField.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                formVariable = taskVariableField.getText();
                updateButtons();
            }
        });
        return area;
    }

    private void updateButtons() {
        getButton(OK).setEnabled(
                !Strings.isNullOrEmpty(formVariable) && processVariables.contains(processVariable) && !processVariables.contains(formVariable));
    }

    public String getAccess() {
        return VariableMapping.USAGE_MULTIINSTANCE_LINK + "," + VariableMapping.USAGE_WRITE + "," + VariableMapping.USAGE_READ;
    }

    public String getProcessVariable() {
        return processVariable;
    }

    public String getFormVariable() {
        return formVariable;
    }
}
