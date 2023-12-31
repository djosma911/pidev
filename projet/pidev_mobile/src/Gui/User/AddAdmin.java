/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gui.User;

import Entity.Role;
import Entity.User;
import Services.ServiceUser;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import java.util.ArrayList;

/**
 *
 * @author MSI
 */
public class AddAdmin extends Form{
    private Role r;
    private boolean isValidEmail(String email) {
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    int atIndex = email.indexOf('@');
    int dotIndex = email.lastIndexOf('.');

    return (atIndex > 0 && dotIndex > atIndex);
    
}
    public AddAdmin(User u,Form prev) {
        setTitle("Inscription");
        Button back = new Button();
        back.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, back.getStyle()));
        back.addActionListener((evt) -> {
        new UserHome(u,prev).show();
        });
        Container container = new Container(new FlowLayout(Component.LEFT));
        container.add(back);
        addComponent(container);
        setLayout(BoxLayout.yCenter());
        Label lnom = new Label("nom:");
        TextField tnom = new TextField("","votre nom");
        Label lprenom = new Label("Prénom:");
        TextField tprenom = new TextField("","votre prénom");
        Label lusername = new Label("votre username");
        TextField tusername = new TextField("","Username:");
        Label lemail = new Label("Adresse E-mail:");
        TextField temail = new TextField("","votre adresse e-mail");
        Label lmdp = new Label("Mot de passe:");
        TextField tmdp = new TextField();
        tmdp.setHint("votre mot de passe");
        tmdp.setConstraint(TextField.PASSWORD);
        Label lmdp2 = new Label("Comfirmation mot de passe:");
        TextField tmdp2 = new TextField();
        tmdp2.setHint("Entrer votre mot de passe de nouveau");
        tmdp2.setConstraint(TextField.PASSWORD);
        Label lnum_tel = new Label("Numéro de téléphone:");
        TextField tnum_tel = new TextField("","votre numéro de téléphone");
        tnum_tel.setConstraint(TextField.PHONENUMBER);
        Label lcin = new Label("Numéro de carte d'identité:");
        TextField tcin = new TextField("","Votre numéro de carte d'identité");
        tcin.setConstraint(TextField.NUMERIC);
        ComboBox<String> combo = new ComboBox<>();
        ArrayList<Role> roles = ServiceUser.getService().getAllRoles();

// Step 3: Iterate over the roles and extract role names
        for (Role role : roles) {
          String idrole = String.valueOf(role.getId_role());

    // Step 4: Add the role name to the ComboBox
         combo.addItem(idrole);
         
     }
        /*ComboBox<String> role = new ComboBox<>();
        role.addItem("1");
        role.addItem("2");
        role.addItem("3");*/
        combo.addActionListener(evt -> {
        String selectedOption = combo.getSelectedItem();
        //System.out.println("Selected option: " + selectedOption);
        Role selectedRole= ServiceUser.getService().getRole(Integer.parseInt(selectedOption));
        this.r=selectedRole;
        });
        
        Button addU = new Button("Ajouter");
        addU.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String testemail = temail.getText().trim();
                if(tnom.getText().isEmpty()|| tprenom.getText().isEmpty()||tusername.getText().isEmpty()||temail.getText().isEmpty()||tmdp.getText().isEmpty()||tmdp2.getText().isEmpty()||tnum_tel.getText().isEmpty()||tcin.getText().isEmpty()){
                Dialog.show("error", "Vous devez remplir tous les champs", "OK",null);      
                }else{
                if(tmdp.getText().equals(tmdp2.getText())){
                    if(tnum_tel.getText().length()<=8){
                        if(tcin.getText().length()<=8){
                            if(isValidEmail(testemail)){
                                if(tmdp.getText().length()<=8){
                User user = new User(tnom.getText(),tprenom.getText(),tusername.getText(),temail.getText(),tmdp.getText(),Integer.parseInt(tnum_tel.getText()),Integer.parseInt(tcin.getText()),r);
                if(ServiceUser.getService().addAdmin(user)){
                    Dialog.show("success", "compte ajouté avec succés", "OK",null);
                    new UserHome(u,prev).show();
                }else{
                Dialog.show("error", "ajout a échoué", "OK",null);
                }
            }else{
              Dialog.show("error", "mot de passe doit contenir au minimum 8 caractéres", "OK",null);                  
                                }}else{
                Dialog.show("error", "Vous devez saisir une adresse e-mail valide", "OK",null);
                            }}else{
                 Dialog.show("error", "Vous devez saisir CIN valide", "OK",null);      
                        }}else{
                  Dialog.show("error", "Vous devez saisir un numéro de téléphone valide", "OK",null);
                    }}else{
                Dialog.show("error", "Vous devez saisir deux mots de passe identiques", "OK",null);   
                }
            } }
        }));
        addAll(lnom,tnom,lprenom,tprenom,lusername,tusername,lemail,temail,lmdp,tmdp,lmdp2,tmdp2,lnum_tel,tnum_tel,lcin,tcin);
        addComponent( combo);
        add(addU);
       
}
}
