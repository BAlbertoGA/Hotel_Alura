package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huespedes;
import jdbc.modelo.Reserva;

public class HuespedDAO {
	
	
	final private Connection connection;
	
	public HuespedDAO(Connection connection) {
		this.connection= connection;
	}
	
	public void guardar(Huespedes huespedes) {
		try {
			
			PreparedStatement preparedStatement;
			// String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono
			preparedStatement = connection.prepareStatement(
					"INSERT INTO huespedes"
						+ "(nombre, apellido, fecha_nacimiento, nacionalidad, telefono, reserva_id)"
						+ "VALUES (?, ?, ?, ?, ? ,?)",
						Statement.RETURN_GENERATED_KEYS);
			
			try (preparedStatement){
				preparedStatement.setString(1, huespedes.getNombre());
				preparedStatement.setString(2, huespedes.getApellido());
				preparedStatement.setDate(3, huespedes.getFechaNacimiento());
				preparedStatement.setString(4, huespedes.getNacionalidad());
				preparedStatement.setString(5, huespedes.getTelefono());
				preparedStatement.setInt(6, huespedes.getReservaId());
				
				preparedStatement.execute();
				
				final ResultSet resultSet = preparedStatement.getGeneratedKeys();
				
				try(resultSet){
					while (resultSet.next()) {
						huespedes.setId(resultSet.getInt(1));
					}
				}
			}
			
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public List<Huespedes> listar() {
		List<Huespedes> resultado = new ArrayList<>();
		
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		
		try (con) {
			final PreparedStatement statement = con
					.prepareStatement("SELECT ID, NOMBRE, APELLIDO, FECHA_NACIMIENTO, NACIONALIDAD, TELEFONO, RESERVA_ID FROM HUESPEDES");
			try (statement){
				statement.execute();
				
				final ResultSet resultSet = statement.getResultSet();
				
				try (resultSet) {
					while (resultSet.next()) {
						Huespedes fila = new Huespedes(resultSet.getInt("ID"), 
								resultSet.getString("NOMBRE"),
								resultSet.getString("APELLIDO"),
								resultSet.getDate("FECHA_NACIMIENTO"), 
								resultSet.getString("NACIONALIDAD"),
								resultSet.getString("TELEFONO"),
								resultSet.getInt("RESERVA_ID"));
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
