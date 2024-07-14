package org.aba.rest.dto;

import org.aba.web.exeption.ServiceException;


/**
 * Dto for responding result of worker with updated qty of records
 * @author aba
 */
public class HubConnectorUpdatedRowsResponse extends MessageResponseDto
{
	private boolean successful;
	private Integer updatedRows;
	private Integer errorCode;

	public HubConnectorUpdatedRowsResponse()
	{
	}

	public HubConnectorUpdatedRowsResponse(String message, ServiceException se)
	{
		super(message, se);
		this.successful = false;
	}

	public HubConnectorUpdatedRowsResponse(String message, Integer updatedRows)
	{
		super(message);
		this.successful = true;
		this.updatedRows = updatedRows;
	}

	public HubConnectorUpdatedRowsResponse(boolean successful, String message, Integer updatedRows
			, Integer errorCode)
	{
		this(successful, message, updatedRows, errorCode, null);
	}

	public HubConnectorUpdatedRowsResponse(boolean successful, String message, Integer updatedRows
			, Integer errorCode, ServiceException se)
	{
		super(message, se);
		this.successful = successful;
		this.updatedRows = updatedRows;
		this.errorCode = errorCode;
	}

	public boolean isSuccessful()
	{
		return successful;
	}

	public Integer getUpdatedRows()
	{
		return updatedRows;
	}

	public Integer getErrorCode()
	{
		return errorCode;
	}

	@Override
	public String toString()
	{
		return "MagoConnectorUpdatedRowsResponse [successful=" + successful + ", message=" + getMessage()
				+ ", updatedRows=" + updatedRows + ", errorCode=" + errorCode + ", ServiceException=" + getSe() + "]";
	}
}