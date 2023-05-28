package jdbc.controller;

import jdbc.dao.ReservaDAO;
import jdbc.modelo.Reserva;

public class ReservasController {
	
	private ReservaDAO reservaDAO; 
	
	public void guardar(Reserva nuevaReserva) {
		reservaDAO.guardar(nuevaReserva);
	}
	
}
