package com.mrdevelop.dataexpinp.ui;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("com.mrdevelop.dataexpinp.MyAppWidgetset")
public class MyUI extends UI {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MyUI.class);

    private DataSourceForm dataSourceForm;
    private DataImportForm dataImportForm; 

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        dataSourceForm = new DataSourceForm(this);
        dataImportForm = new DataImportForm(this);

        Label label = new Label("Data Import Export Tool");
        layout.addComponent(label);
        
        TabSheet tabsheet = new TabSheet();
        layout.addComponent(tabsheet);

        // Create the first tab
        VerticalLayout tab1 = new VerticalLayout();
        tab1.addComponent(dataSourceForm);
        tab1.setCaption("Data source");
        tabsheet.addTab(tab1, "Data source");

        // This tab gets its caption from the component caption
        VerticalLayout tab2 = new VerticalLayout();
        tab2.addComponent(dataImportForm);
        tab2.setCaption("Inporting");
        tabsheet.addTab(tab2, "Data importing");
        
        Button importButton = new Button("Import");
        importButton.addClickListener(e -> {
            LOGGER.debug("importing data...");
        });
        
        HorizontalLayout buttonsSection = new HorizontalLayout();
        buttonsSection.addComponent(importButton);
        layout.addComponent(buttonsSection);
        
        layout.setMargin(true);
        layout.setSpacing(true);

        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
