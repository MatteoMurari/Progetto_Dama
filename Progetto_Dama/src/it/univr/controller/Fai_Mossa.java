package it.univr.controller;

import it.univr.model.Damiera;
import it.univr.model.Mossa;

public class Fai_Mossa {
	
	public void esegui(Damiera dam, Mossa mos)
	{
		//si occupa di compiere le dovute modifiche alla damiera dam, secondo la mossa mos.
		
		if(mos.getPercorso()==null)
			dam.muovi(mos.getInizio(), mos.getFine());
		else
		{
			int i=0;
			if(mos.getPercorso()!=null)
			{
				while(i<mos.getPercorso().length-2)
				{
					dam.presa(mos.getPercorso()[i], mos.getPercorso()[i+1], mos.getPercorso()[i+2]);
					i=i+2;
				}
				
				dam.presa(mos.getPercorso()[i], mos.getPercorso()[i+1], mos.getFine());
			}
		}		
	}

}
