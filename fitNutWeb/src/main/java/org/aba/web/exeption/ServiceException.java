package org.aba.web.exeption;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class ServiceException extends Exception
{
    private static final long serialVersionUID = 2939996118379002717L;
    private String exceptionType;
    private String exceptionValue;
    private String[] exceptionValues;
    private Integer exceptionCode;

    public ServiceException(String exceptionType, String exceptionValue)
    {
        this.init(exceptionType, exceptionValue, null);
    }

    public ServiceException(String exceptionType, String[] exceptionValues)
    {
        this.init(exceptionType, exceptionValues, null);
    }

    public ServiceException(String exceptionType, String exceptionValue, Throwable cause)
    {
        super(exceptionValue, cause);
        this.init(exceptionType, exceptionValue, null);
    }

    public ServiceException(String exceptionType, String[] exceptionValues, Throwable cause)
    {
        super(cause);
        this.init(exceptionType, exceptionValues, null);
    }

    public ServiceException(String exceptionType, Integer exceptionCode)
    {
        this.init(exceptionType, (String) null, exceptionCode);
    }

    public ServiceException(String exceptionType, Integer exceptionCode, String exceptionValue)
    {
        this.init(exceptionType, exceptionValue, exceptionCode);
    }

    public ServiceException(String exceptionType, Integer exceptionCode, String[] exceptionValues)
    {
        this.init(exceptionType, exceptionValues, exceptionCode);
    }

    public ServiceException(String exceptionType, Integer exceptionCode, Throwable cause)
    {
        super(cause.getMessage(), cause);
        this.init(exceptionType, (String) null, exceptionCode);
    }

    protected void init(String exceptionType, String exceptionValue, Integer exceptionCode)
    {
        Validate.notBlank(exceptionType);
        this.exceptionType = exceptionType;
        this.exceptionValue = exceptionValue;
        this.exceptionCode = exceptionCode;
    }

    protected void init(String exceptionType, String[] exceptionValues, Integer exceptionCode)
    {
        Validate.notBlank(exceptionType);
        this.exceptionType = exceptionType;
        this.exceptionValues = exceptionValues;
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionType()
    {
        return this.exceptionType;
    }

    public String getExceptionValue()
    {
        return this.exceptionValue;
    }

    public String[] getExceptionValues()
    {
        return this.exceptionValues;
    }

    public Integer getExceptionCode()
    {
        return this.exceptionCode;
    }

    @Override
    public String getMessage()
    {
        if (this.getCause() != null)
        {
            return super.getMessage();
        }
        else
        {
            return this.exceptionCode != null ? "ExceptionCode >" + this.exceptionCode + "<;" : ("");
        }
    }

    @Override
    public String toString()
    {
        String s = StringUtils.isNotEmpty(this.exceptionType) ? "Exception Type :" + this.exceptionType : "";
        s += this.exceptionCode != null ? " Exception Code :" + this.exceptionCode : "";

        if (this.exceptionValues != null)
        {
            s += this.exceptionValues != null ? " Exception Values :" + this.exceptionValues : "";
        }
        else if (StringUtils.isNotEmpty(this.exceptionValue))
        {
            s += StringUtils.isNotEmpty(this.exceptionValue) ? " Exception Value :" + this.exceptionValue : "";
        }

        String message = " Message :" + getLocalizedMessage();
        return (message != null) ? (s + ": " + message) : s;
    }
}

