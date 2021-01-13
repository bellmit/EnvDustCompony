package com.tcb.env.gateway.filter;

import java.util.ArrayList;

public interface SplitPacketI {
	
	public static final byte[] NoHEAD = null;
	
	public ArrayList<byte[]> unpacking(byte[] date,int len) throws SplitPacketException;
	
	public byte[] getMsgHead();
}
