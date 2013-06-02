package Listener;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;

public class Listener implements RowSetListener{

	@Override
	public void cursorMoved(RowSetEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("courser moved");
	}

	@Override
	public void rowChanged(RowSetEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Reihe geändert!!");
	}

	@Override
	public void rowSetChanged(RowSetEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("rowset geändert!");
	}

}
