package org.aba.web.controller;

import org.aba.web.SessionBean;
import org.aba.web.manager.ApplicationManager;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;


/**
 * Controller for menu
 *
 * @author aba
 */
@Component("menuController")
@Scope("session")
public class MenuController implements Serializable
{
    private static final long serialVersionUID = -4553121725271421456L;

    @Autowired
    private ApplicationManager applicationManager;
    @Autowired
    private SessionBean sessionBean;

    private MenuModel model; // Model for the view

    public MenuModel getMenu()
    {
        if (model == null)
        {
            buildMenu();
        }
        return model;
    }

    /**
     * builds the menue
     */
    private void buildMenu()
    {
        String appName = "fitNutWeb";
        model = new DefaultMenuModel();

        DefaultMenuItem rootMenu = new DefaultMenuItem();
        rootMenu.setValue("Home");
        rootMenu.setUrl("home.xhtml");
        model.getElements().add(rootMenu);

        //Nutition
        DefaultSubMenu nutSM = new DefaultSubMenu();
        nutSM.setLabel("Nutrition");
        nutSM.setIcon("pi pi-align-justify");
        model.getElements().add(nutSM);

        DefaultMenuItem ingredientList = new DefaultMenuItem();
        ingredientList.setValue("Alliment");
        ingredientList.setIcon("pi pi-align-justify");
        ingredientList.setUrl("ingredient/ingredients.xhtml");
        nutSM.getElements().add(ingredientList);

        DefaultMenuItem ingredientTypeList = new DefaultMenuItem();
        ingredientTypeList.setValue("Type d'alliment");
        ingredientTypeList.setIcon("pi pi-align-justify");
        ingredientTypeList.setUrl("ingredient/ingredientTypes.xhtml");
        nutSM.getElements().add(ingredientTypeList);

        //Exercise
        DefaultSubMenu exerciseSM = new DefaultSubMenu();
        exerciseSM.setLabel("Fitness");
        exerciseSM.setIcon("pi pi-align-justify");
        model.getElements().add(exerciseSM);

        DefaultMenuItem exerciseList = new DefaultMenuItem();
        exerciseList.setValue("Exercice");
        exerciseList.setIcon("pi pi-align-justify");
        exerciseList.setUrl("exercise/exercises.xhtml");
        exerciseSM.getElements().add(exerciseList);

        DefaultMenuItem exerciseTypeList = new DefaultMenuItem();
        exerciseTypeList.setValue("Type d'exercice");
        exerciseTypeList.setIcon("pi pi-align-justify");
        exerciseTypeList.setUrl("exercise/exerciseTypes.xhtml");
        exerciseSM.getElements().add(exerciseTypeList);

    }
}