package org.aba.web.controller.searchForm;

import java.io.Serializable;

public abstract class AbstractSearchForm implements Serializable
{
    private static final long serialVersionUID = 1308272462825195421L;

    protected AbstractSearchForm()
    {
    }

    public abstract void initSearchFormData();

    public abstract void clearForm();
}
