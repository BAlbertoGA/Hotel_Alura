package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reserva;

public class ReservaDAO {
	
	final private Connection connection;
	
	public ReservaDAO (Connection connection) {
		this.connection = connection;
	}
	
	public void guardar(Reserva reserva) {
		
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
				preparedStatement.setInt(3, (int) reserva.getValor());
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
	
	public List<Reserva> listar() {
		List<Reserva> resultado = new ArrayList<>();
		
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		
		try (con) {
			final PreparedStatement statement = con
					.prepareStatement("SELECT ID, FECHA_ENTRADA, FECHA_SALIDA, VALOR, FORMA_PAGO FROM RESERVAS");
			try (statement){
				statement.execute();
				
				final ResultSet resultSet = statement.getResultSet();
				
				try (resultSet) {
					while (resultSet.next()) {
						Reserva fila = new Reserva(
								resultSet.getInt("ID"), 
								resultSet.getDate("FECHA_ENTRADA"), 
								resultSet.getDate("FECHA_SALIDA"), 
								resultSet.getInt("VALOR"),
								resultSet.getString("FORMA_PAGO"));
						resultado.add(fila);
					}
				}
			}
			return resultado;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
