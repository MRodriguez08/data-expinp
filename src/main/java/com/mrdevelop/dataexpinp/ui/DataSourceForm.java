package com.mrdevelop.dataexpinp.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
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

public class DataSourceForm extends FormLayout {

    private NativeSelect dataSourceType = new NativeSelect("Data Source Type");
    private NativeSelect status = new NativeSelect("Status");
    private PopupDateField birthdate = new PopupDateField("Birthday");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private MyUI myUi;

    public DataSourceForm(MyUI myUi) {
        this.myUi = myUi;
        setSizeUndefined();
        
        dataSourceType.addItems(DataSourceType.values());
        
        ImageUploader receiver = new ImageUploader();

        // Create the upload with a caption and set receiver later
        Upload upload = new Upload("File", receiver);
        upload.setButtonCaption("Start Upload");
        upload.addSucceededListener(receiver);

        addComponents(dataSourceType, upload);
    }
    
 // Implement both receiver that saves upload in a file and
    // listener for successful upload
    class ImageUploader implements Receiver, SucceededListener {
        public File file;

        public OutputStream receiveUpload(String filename,
                                          String mimeType) {
            // Create upload stream
            FileOutputStream fos = null; // Stream to write to
            try {
                // Open the file for writing.
                file = new File("/tmp/" + filename);
                fos = new FileOutputStream(file);
            } catch (final java.io.FileNotFoundException e) {
                new Notification("Could not open file<br/>",
                                 e.getMessage(),
                                 Notification.Type.ERROR_MESSAGE)
                    .show(Page.getCurrent());
                return null;
            }
            return fos; // Return the output stream to write to
        }

        public void uploadSucceeded(SucceededEvent event) {
            // Show the uploaded file in the image viewer
//            image.setVisible(true);
//            image.setSource(new FileResource(file));
        }
    };

   
}
