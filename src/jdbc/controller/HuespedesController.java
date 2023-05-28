package jdbc.controller;

import jdbc.dao.HuespedDAO;
import jdbc.dao.ReservaDAO;
import jdbc.modelo.Huespedes;
import jdbc.modelo.Reserva;

public class HuespedesController {
	
	private HuespedDAO huespedDAO; 

	public void guardar(Huespedes huespedes, Integer reservaId) {
		
		System.out.println(huespedes + " controller");
		huespedes.setReservaId(reservaId);
		huespedDAO.guardar(huespedes);
	}
}
