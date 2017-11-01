package dataLayer.dataAccessObjects.sqlite;

import java.util.ArrayList;
import java.util.List;
import businessObjects.ITrainer;
import dataLayer.businessObjects.Trainer;
import dataLayer.dataAccessObjects.ITrainerDao;
import exceptions.NoNextTrainerFoundException;
import exceptions.NoPreviousTrainerFoundException;
import exceptions.NoTrainerFoundException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TrainerDaoSqlite implements ITrainerDao {

	private static String CLASSNAME = "org.sqlite.JDBC";
	private static String CONNECTIONSTRING = "jdbc:sqlite:trainer.db";

	private Connection con = null;
	private Statement st = null;
	private ResultSet rs = null;

	private static String CREATETABLEQUERY = "CREATE TABLE IF NOT EXISTS trainer (id integer primary key, name text DEFAULT '', age integer DEFAULT 0, experience integer DEFAULT 0);";

	public TrainerDaoSqlite() {
		try {
			Class.forName(CLASSNAME);
			con = DriverManager.getConnection(CONNECTIONSTRING);
			st = con.createStatement();
			st.executeQuery(CREATETABLEQUERY);
			con.commit();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public ITrainer create() throws IOException {
		try {
			rs = st.executeQuery("INSERT INTO trainer (name) values('');");
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			return last();
		} catch (NoTrainerFoundException e) {
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public void delete(ITrainer trainer) throws NoTrainerFoundException {
		int id = trainer.getId();
		try {
			st.executeQuery("DELETE FROM trainer WHERE id = " + id + ";");
			con.commit();
		} catch (SQLException e) {
			throw new NoTrainerFoundException(e.getMessage());
		}
	}

	@Override
	public ITrainer first() throws NoTrainerFoundException {
		Trainer trainer = new Trainer();
		try {
			rs = st.executeQuery("SELECT * FROM trainer ORDER BY id LIMIT 1;");
			rs.first();
			trainer.setId(rs.getInt("id"));
			trainer.setName(rs.getString("name"));
			trainer.setAlter(rs.getInt("age"));
			trainer.setErfahrung(rs.getInt("experience"));
		} catch (SQLException e) {
			throw new NoTrainerFoundException(e.getMessage());
		}
		return trainer;
	}

	@Override
	public ITrainer last() throws NoTrainerFoundException {
		Trainer trainer = new Trainer();
		try {
			rs = st.executeQuery("SELECT * FROM trainer ORDER BY id DESC LIMIT 1;");
			rs.first();
			trainer.setId(rs.getInt("id"));
			trainer.setName(rs.getString("name"));
			trainer.setAlter(rs.getInt("age"));
			trainer.setErfahrung(rs.getInt("experience"));
		} catch (SQLException e) {
			throw new NoTrainerFoundException(e.getMessage());
		}
		return trainer;
	}

	@Override
	public ITrainer next(ITrainer trainer) throws NoNextTrainerFoundException {
		Trainer returntrainer = new Trainer();
		try {
			rs = st.executeQuery("SELECT FROM trainer WHERE id > " + trainer.getId() + " ORDER BY id LIMIT 1;");
			rs.first();
			returntrainer.setId(rs.getInt("id"));
			returntrainer.setName(rs.getString("name"));
			returntrainer.setAlter(rs.getInt("age"));
			returntrainer.setErfahrung(rs.getInt("experience"));
		} catch (SQLException e) {
			throw new NoNextTrainerFoundException(e.getMessage());
		}
		return returntrainer;
	}

	@Override
	public ITrainer previous(ITrainer trainer) throws NoPreviousTrainerFoundException {
		Trainer returntrainer = new Trainer();
		try {
			rs = st.executeQuery("SELECT FROM trainer WHERE id < " + trainer.getId() + " ORDER BY id DESC LIMIT 1;");
			rs.first();
			returntrainer.setId(rs.getInt("id"));
			returntrainer.setName(rs.getString("name"));
			returntrainer.setAlter(rs.getInt("age"));
			returntrainer.setErfahrung(rs.getInt("experience"));
		} catch (SQLException e) {
			throw new NoPreviousTrainerFoundException(e.getMessage());
		}
		return returntrainer;
	}

	@Override
	public void save(ITrainer trainer) throws IOException {
		try {
			st.executeQuery("UPDATE trainer name = " + trainer.getName() + ", age = " + trainer.getAlter()
					+ ", experience = " + trainer.getErfahrung() + " WHERE id = " + trainer.getId() + ";");
			con.commit();
		} catch (SQLException e) {
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public List<ITrainer> select() throws NoTrainerFoundException {
		List<ITrainer> liste = new ArrayList<ITrainer>();
		try {
			rs = st.executeQuery("SELECT * FROM trainer ORDER BY id");
			while(rs.next()){
				Trainer trainer = new Trainer();
				trainer.setId(rs.getInt("id"));
				trainer.setName(rs.getString("name"));
				trainer.setAlter(rs.getInt("age"));
				trainer.setErfahrung(rs.getInt("experience"));
				liste.add(trainer);
			}
		} catch (SQLException e) {
			throw new NoTrainerFoundException(e.getMessage());
		}
		return liste;
	}

	@Override
	public ITrainer select(int id) throws NoTrainerFoundException {
		Trainer trainer = new Trainer();
		try {
			rs = st.executeQuery("SELECT FROM trainer WHERE id = " + id + ";");
			rs.first();
			trainer.setId(rs.getInt("id"));
			trainer.setName(rs.getString("name"));
			trainer.setAlter(rs.getInt("age"));
			trainer.setErfahrung(rs.getInt("experience"));
		} catch (SQLException e) {
			throw new NoTrainerFoundException(e.getMessage());
		}
		return trainer;
	}
}
