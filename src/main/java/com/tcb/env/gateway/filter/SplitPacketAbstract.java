package com.tcb.env.gateway.filter;

public abstract class SplitPacketAbstract implements SplitPacketI {

	/**
	 * ճ����Ʊ�ʶ(Ĭ����Ҫ���)
	 */
	public boolean stickPackage = true;
	

	public boolean isStickPackage() {
		return stickPackage;
	}

	public void setStickPackage(boolean stickPackage) {
		this.stickPackage = stickPackage;
	}
	
	/*
	@Override
	public ArrayList<byte[]> unpacking(byte[] date, int len)
			throws SplitPacketException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getMsgHead() {
		// TODO Auto-generated method stub
		return null;
	}
	*/

	public boolean isNoHead() throws RuntimeException
	{
		boolean nohead = false;
		if(getMsgHead() == NoHEAD)
		{
			nohead = true;
		}
		else if(getMsgHead().length == 0)
		{
			throw new RuntimeException("��Ϣͷ���Ȳ���Ϊ0");
		}
		return nohead;
	}
	
}
