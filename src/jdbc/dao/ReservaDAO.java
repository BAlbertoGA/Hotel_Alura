package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.modelo.Reserva;

public class ReservaDAO {
	
	final private Connection connection;
	
	public ReservaDAO (Connection connection) {
		this.connection = connection;
	}
	
	public void guardar(Reserva reserva) {
		System.out.println(reserva + " dao");
		try {
			
			PreparedStatement preparedStatement;
			
			preparedStatement = connection.prepareStatement(
					"INSERT INTO reservas"
						+ "(fecha_entrada, fecha_salida, valor, forma_pago)"
						+ "VALUES (?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);
			
			try (preparedStatement){
				preparedStatement.setDate(1, reserva.getFechaEntrada());
				preparedStatement.setDate(2, reserva.getFechaSalida());
				preparedStatement.setString(3, reserva.getValor());
				preparedStatement.setString(4, reserva.getFormaPago());
				
				preparedStatement.execute();
				
				final ResultSet resultSet = preparedStatement.getGeneratedKeys();
				
				try(resultSet){
					while (resultSet.next()) {
						reserva.setId(resultSet.getInt(1));
					}
				}
			}
			
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
}
