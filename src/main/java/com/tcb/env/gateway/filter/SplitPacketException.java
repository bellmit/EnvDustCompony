package com.tcb.env.gateway.filter;

public class SplitPacketException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4985525468928901088L;

	public SplitPacketException()
	{
		super();
	}
	
	public SplitPacketException(String msg)
	{
		super(msg);
	}

	public SplitPacketException(Throwable cause)
	{
		super(cause);
	}
	
	public SplitPacketException(String msg,Throwable cause)
	{
		super(msg,cause);
	}
	
}
