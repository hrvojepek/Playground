package hr.hpek.playing.dao;


import java.util.List;

import hr.hpek.playing.entity.Address;


public interface AddressDAO {
	
    public void save(Address address);
    
    public void delete(int addressID);
     
    public Address get(int addressID);
     
    public List<Address> getAll();

}
