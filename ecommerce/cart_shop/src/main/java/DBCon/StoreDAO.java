package DBCon;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import Model.Products;

public interface StoreDAO {
	ArrayList<String> getAllCategories();

	ArrayList<Products> getAllProducts() throws SQLException;

	ArrayList<Products> getAllProductsId(String id) throws SQLException;

	ArrayList<Products> getAllProductSort(String catid) throws SQLException;

	ArrayList<Products> getCatProductsSort(String catid, String sortid) throws SQLException;
	
	void insertProducts(Map<Integer, Integer> proidQuantityMap) throws SQLException;
	 public ArrayList<ArrayList<Products>> getOrders(String Order_id) throws SQLException;

}