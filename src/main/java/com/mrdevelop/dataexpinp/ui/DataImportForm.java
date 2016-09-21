package com.mrdevelop.dataexpinp.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.themes.ValoTheme;

public class DataImportForm extends FormLayout {

    private TextField databaseUrl = new TextField("Database url");
    private TextField databaseUser = new TextField("Database user");
    private TextField databasePassword = new TextField("Database password");
    private TextField email = new TextField("Email");
    
    private PopupDateField birthdate = new PopupDateField("Birthday");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private MyUI myUi;

    public DataImportForm(MyUI myUi) {
        this.myUi = myUi;
        setSizeUndefined();
        
        
        addComponents(databaseUrl, databaseUser, databasePassword);
    }
    


    

}
