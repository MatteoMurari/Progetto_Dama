package it.univr.model;

public class FuoriDamException extends IllegalArgumentException {
	
	public FuoriDamException()
	{
		super("Fuori dal bordo della damiera.");
	}
	
	public FuoriDamException(String msg)
	{
		super(msg);
	}

}
