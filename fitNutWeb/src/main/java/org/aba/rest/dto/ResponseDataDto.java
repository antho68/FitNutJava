package org.aba.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Collection;


/**
 * Dto for responding data with a summary and a data container
 *
 * @author aba
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"data"})
public class ResponseDataDto
{
    private Collection<?> data;

    public Collection<?> getData()
    {
        return data;
    }

    public void setData(Collection<?> data)
    {
        this.data = data;
    }

    public ResponseDataDto withData(Collection<?> data)
    {
        this.data = data;
        return this;
    }
}