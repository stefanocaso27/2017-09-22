package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.formulaone.model.Peso;
import it.polito.tdp.formulaone.model.Pilota;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;

public class FormulaOneDAO {

	public List<Season> getAllSeasons() {
		String sql = "SELECT year, url FROM seasons ORDER BY year";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Season> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Season(rs.getInt("year"), rs.getString("url")));
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Race> getAllRaces(Map<Integer, Race> idMap) {
		
		String sql = "SELECT * FROM races WHERE TIME <> 'NULL' ";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Race> list = new ArrayList<>();
			
			while (rs.next()) {
				Race r = new Race(rs.getInt("raceId"), Year.of(rs.getInt("year")), rs.getInt("round"),
						rs.getInt("circuitId"), rs.getString("name"),
						rs.getTimestamp("date").toLocalDateTime().toLocalDate(), rs.getTime("time").toLocalTime(),
						rs.getString("url"));
				
				list.add(r);
				idMap.put(rs.getInt("raceId"), r);
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Race> getGareAnno(int anno, Map<Integer, Race> idMap) {
		String sql = "SELECT r.raceId " + 
				"FROM races r " + 
				"WHERE r.YEAR = ? " ; 
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			List<Race> list = new ArrayList<Race>();
			
			while (rs.next()) {
				Race r = idMap.get(rs.getInt("raceId"));
				
				list.add(r);
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Peso> getPesiArchi(int anno, Map<Integer, Race> idMap) {
		
		String sql = "SELECT COUNT(re1.driverId) AS pilota, ra1.raceId AS gara1, ra2.raceId AS gara2 " + 
				"FROM races ra1, results re1, races ra2, results re2 " + 
				"WHERE re1.statusId = 1 AND re2.statusId = 1 AND re1.driverId = re2.driverId " + 
				"		 AND ra2.raceId < ra1.raceId AND ra2.raceId = re2.raceId AND ra1.raceId = re1.raceId " + 
				"		 				AND ra1.YEAR = ? AND ra2.YEAR = ? " + 
				"GROUP BY gara1, gara2";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			ResultSet rs = st.executeQuery();
			List<Peso> list = new ArrayList<Peso>();
			
			while (rs.next()) {
				Race r1 = idMap.get(rs.getInt("gara1"));
				Race r2 = idMap.get(rs.getInt("gara2"));
				Double p = rs.getDouble("pilota");
				
				Peso peso = new Peso(r1, r2, p);
				
				list.add(peso);
			}
			
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	
	public List<Pilota> getPilotiGara(int id, Map<Integer, Pilota> mappa) {
		String sql = "SELECT distinct l.driverId AS id " + 
				"FROM laptimes l " + 
				"WHERE l.raceId = ? ";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			List<Pilota> list = new ArrayList<Pilota>();
			
			while (rs.next()) {
				Pilota p = new Pilota(rs.getInt("id"));
				
				mappa.put(rs.getInt("id"), p);
				list.add(p);
			}
			
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<Integer, Integer> getTempi(int giro, int id) {
		String sql = "SELECT l.lap AS giro, l.milliseconds AS tempo " + 
				"FROM laptimes l " + 
				"WHERE l.raceId = ? AND l.driverId = ? " ;
		
		Map<Integer, Integer> mappa = new HashMap<Integer, Integer>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, giro);
			st.setInt(2, id);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				int n1 = rs.getInt("giro");
				int n2 = rs.getInt("tempo");
				
				mappa.put(n1, n2);
			}
			
			conn.close();
			return mappa;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
