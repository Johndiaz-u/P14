package com.example.demo;



import com.example.demo.Modelos.Customer;

import com.example.demo.Services.CustomerService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


import java.util.List;



@SpringUI
@Theme("valo") //Tema Plantilla por defecto.
public class MyUI extends UI{



    private CustomerService service = CustomerService.getInstance();
    private Grid<Customer> grid = new Grid<>(Customer.class);
    private TextField filterText = new TextField();
    private CustomerForm form = new CustomerForm(this);

    public void updateList() {
        List<Customer> customers = service.findAll(filterText.getValue());
        grid.setItems(customers);
    }


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        filterText.setPlaceholder("Buscar..");
        filterText.addValueChangeListener(e -> updateList());
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
        clearFilterTextBtn.setDescription("Limpiar filtro");
        clearFilterTextBtn.addClickListener(e -> filterText.clear());

        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterText, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        Button addCustomerBtn = new Button("Add");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setCustomer(new Customer());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filtering, addCustomerBtn);

        grid.setColumns("firstName", "lastName", "email");

        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setSizeFull();
        grid.setSizeFull();
        main.setExpandRatio(grid, 1);

        layout.addComponents(toolbar, main);


        updateList();

        setContent(layout);

        form.setVisible(false);

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                form.setVisible(false);
            } else {
                form.setCustomer(event.getValue());
            }
        });
    }




}
