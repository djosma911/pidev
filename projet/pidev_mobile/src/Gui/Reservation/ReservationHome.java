/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui.Reservation;

import Entity.User;
import Gui.User.UserHome;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author hanin
 */
public class ReservationHome extends Form {

    public ReservationHome(User u, Form previous) {
     
        setTitle("Home");
        Button back = new Button();
        back.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, back.getStyle()));
        back.addActionListener((evt) -> {
        new UserHome(u,previous).show();
        });
        Container container = new Container(new FlowLayout(Component.LEFT));
        container.add(back);
        addComponent(container);
        setLayout(BoxLayout.y());
        
        add(new Label("Choose an option"));
        //Button btnAddReservation = new Button("Add Reservation");
        Button btnAddReservation = new Button("Add Reservation");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ADD, "Button", 6);
        btnAddReservation.setIcon(icon);
        //Button btnListReservations = new Button("List Reservations");
        Button btnListReservations = new Button("List Reservations");
        FontImage icon2 = FontImage.createMaterial(FontImage.MATERIAL_CALENDAR_VIEW_DAY, "Button", 6);
        btnListReservations.setIcon(icon2);

        Resources  res = null;
        btnAddReservation.addActionListener(e-> new AddReservationForm(u,previous,res).show());
        //btnListTasks.addActionListener(e-> new ListTasksForm(this).show());
        btnListReservations.addActionListener(e-> new ListReservations(u,this).show());
        add(btnAddReservation);
        add(btnListReservations);

    }
    
}
