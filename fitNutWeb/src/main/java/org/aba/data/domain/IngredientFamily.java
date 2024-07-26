package org.aba.data.domain;

public class IngredientFamily extends AbstractDomain
{
    private String name;
    private String type;

    public IngredientFamily()
    {

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
