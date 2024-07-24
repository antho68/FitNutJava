package org.aba.web.controller;

import org.aba.web.SessionBean;
import org.aba.web.manager.ApplicationManager;
import org.aba.web.utils.CommonUtils;
import org.primefaces.event.MenuActionEvent;
import org.primefaces.model.menu.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.event.ActionEvent;
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
        rootMenu.setCommand("#{menuController.setFunction}");
        rootMenu.setParam("url", "/home.xhtml");
        model.getElements().add(rootMenu);

        //Nutition
        DefaultSubMenu nutSM = new DefaultSubMenu();
        nutSM.setLabel("Nutrition");
        nutSM.setIcon("pi pi-align-justify");
        model.getElements().add(nutSM);

        DefaultMenuItem ingredientList = new DefaultMenuItem();
        ingredientList.setValue("Alliment");
        ingredientList.setIcon("pi pi-align-justify");
        ingredientList.setCommand("#{menuController.setFunction}");
        ingredientList.setParam("url", "/ingredient/ingredients.xhtml");
        nutSM.getElements().add(ingredientList);

        DefaultMenuItem ingredientTypeList = new DefaultMenuItem();
        ingredientTypeList.setValue("Type d'alliment");
        ingredientTypeList.setIcon("pi pi-align-justify");
        ingredientTypeList.setCommand("#{menuController.setFunction}");
        ingredientTypeList.setParam("url", "/ingredient/ingredientTypes.xhtml");
        nutSM.getElements().add(ingredientTypeList);

        //Exercise
        DefaultSubMenu exerciseSM = new DefaultSubMenu();
        exerciseSM.setLabel("Fitness");
        exerciseSM.setIcon("pi pi-align-justify");
        model.getElements().add(exerciseSM);

        DefaultMenuItem exerciseList = new DefaultMenuItem();
        exerciseList.setValue("Exercice");
        exerciseList.setIcon("pi pi-align-justify");
        exerciseList.setCommand("#{menuController.setFunction}");
        exerciseList.setParam("url", "/exercise/exercises.xhtml");
        exerciseSM.getElements().add(exerciseList);

        DefaultMenuItem exerciseTypeList = new DefaultMenuItem();
        exerciseTypeList.setValue("Type d'exercice");
        exerciseTypeList.setIcon("pi pi-align-justify");
        exerciseTypeList.setCommand("#{menuController.setFunction}");
        exerciseTypeList.setParam("url", "/exercise/exerciseTypes.xhtml");
        exerciseSM.getElements().add(exerciseTypeList);
    }

    public void setFunction(ActionEvent event)
    {
        MenuItem menuItem = ((MenuActionEvent) event).getMenuItem();
        String url = menuItem.getParams().get("url").get(0);

        sessionBean.setLastPageCalled(url);
        CommonUtils.navigateTo(url, true);
    }
}