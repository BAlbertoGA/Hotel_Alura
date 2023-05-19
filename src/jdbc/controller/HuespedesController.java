package jdbc.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import jdbc.dao.HuespedDAO;
import jdbc.dao.ReservaDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huespedes;
import jdbc.modelo.Reserva;

public class HuespedesController {
	
	private HuespedDAO huespedDAO; 
	
	public HuespedesController () {
		var factory = new ConnectionFactory();
		this.huespedDAO = new HuespedDAO(factory.recuperaConexion());
	}

	public void guardar(Huespedes huespedes, Integer reservaId) {
		
		System.out.println(huespedes + " controller");
		huespedes.setReservaId(reservaId);
		this.huespedDAO.guardar(huespedes);
	}

	public List<Huespedes> listar() {
		return huespedDAO.listar();
	}

	public int eliminar(Integer id) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		
		try(con){
			final PreparedStatement statement = con
					.prepareStatement("DELETE FROM HUESPEDES WHERE ID = ?");
			try(statement){
				statement.setInt(1, id);
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
				return updateCount;
			}
		}
	}

	public void modificar(String nombre, String apellido, Date fechaN, String nacionalidad, String telefono,
			Integer idReservas, Integer idReservas2) {
		
		
	}
}
