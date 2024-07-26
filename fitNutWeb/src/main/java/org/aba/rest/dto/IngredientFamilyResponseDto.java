package org.aba.rest.dto;

import org.aba.data.domain.IngredientFamily;

import java.util.Collection;

public class IngredientFamilyResponseDto
{
    private Collection<IngredientFamily> datas;

    public IngredientFamilyResponseDto()
    {

    }

    public Collection<IngredientFamily> getDatas()
    {
        return datas;
    }

    public void setDatas(Collection<IngredientFamily> datas)
    {
        this.datas = datas;
    }

}
