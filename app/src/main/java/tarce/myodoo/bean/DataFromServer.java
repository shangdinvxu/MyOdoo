/**
 * DataFromServer.java
 * @author Jason Lu
 * @date 2014-2-27
 * @version 1.0
 */
package tarce.myodoo.bean;

import java.io.Serializable;

/**
 * @author Jason
 * 
 */
public class DataFromServer implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6254058541739267643L;
	
	private int errorCode = -1;
	private String errMsg = "";
	private Object returnValue = null;
	

	public int getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(int errorCode)
	{
		this.errorCode = errorCode;
	}

	public String getErrMsg()
	{
		return errMsg;
	}

	public void setErrMsg(String errMsg)
	{
		this.errMsg = errMsg;
	}

	public Object getReturnValue()
	{
		return returnValue;
	}

	public void setReturnValue(Object returnValue)
	{
		this.returnValue = returnValue;
	}

}
