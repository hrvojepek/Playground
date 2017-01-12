package hr.hpek.playing.serviceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import hr.hpek.playing.entity.Address;
import hr.hpek.playing.service.AddressService;

//@Service
public class AddressServiceImpl implements AddressService{
	
	private JdbcTemplate jdbc;
	
	public AddressServiceImpl(DataSource ds) {
		this.jdbc = new JdbcTemplate(ds);
	}

	@Override
	public void save(Address address) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int addressID) {
		String sql = "DELETE FROM contact WHERE contact_id=?";
	    jdbc.update(sql, addressID);
		
	}

	@Override
	public Address get(int addressID) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<Address> getAll() {
//		String sql = "SELECT * FROM address";
//	    List<Address> listAddress = jdbc.query(sql, new RowMapper<Address>() {
//	 
//	        @Override
//	        public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
//	            Address address = new Address();
//	            
//	            address.setId(rs.getLong("id"));
//	            address.setStreet(rs.getString("street"));
//	            
//	            return address;
//	        }
//	 
//	    });
//	 
//	    return listAddress;
//	}
	
	@Override
	public List<Address> getAll() {
		List<Address> as = new ArrayList<>();
		Address a = new Address();
		a.setId(1l);
		a.setStreet("MAKSIMIRSKA");
		as.add(a);
		return as;
	}

}
