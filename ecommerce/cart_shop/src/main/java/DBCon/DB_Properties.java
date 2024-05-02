package DBCon;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import Model.Products;

public class DB_Properties implements StoreDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private ArrayList<String> categories;
	private ArrayList<Products> products;
	private ArrayList<ArrayList<Products>>orders;
	private CallableStatement cs;

	public DB_Properties() throws Exception {
		Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
				"1234");
		categories = new ArrayList<>();
		products = new ArrayList<>();
	}

	public ArrayList<String> getAllCategories() {
		try {
			cs = con.prepareCall("{call getAllCategories()}");
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				categories.add(rs.getInt(1) + "");
				categories.add(rs.getString(2));
			}
			rs.close();
			cs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}

	@Override
	public ArrayList<Products> getAllProducts() throws SQLException {

		cs = con.prepareCall("{call getAllProd}");
		rs = cs.executeQuery();
		products = getProductsFromResultSet(rs);
		rs.close();
		cs.close();
		return products;
	}

	@Override
	public ArrayList<Products> getAllProductsId(String id) throws SQLException {

		cs = con.prepareCall("{call getProdByCat(?)}");
		cs.setInt(1, Integer.parseInt(id));
		rs = cs.executeQuery();
		products = getProductsFromResultSet(rs);
		rs.close();
		cs.close();
		return products;
	}

	public ArrayList<Products> getProductsFromResultSet(ResultSet rs) {
		try {
			while (rs.next()) {
				Products p = new Products();
				p.setProduct_id(rs.getInt("proid"));
				p.setProduct_name(rs.getString("name"));
				p.setProduct_price(rs.getInt("price"));
				p.setProduct_image(rs.getString("imgpath"));
				p.setProduct_catid(rs.getInt("catid"));
				products.add(p);
				System.out.println(rs.getInt("proid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public ArrayList<Products> getAllProductSort(String sortid) throws SQLException {
		cs = con.prepareCall("{call getAllProdSort(?)}");
		cs.setInt(1, Integer.parseInt(sortid));
		rs = cs.executeQuery();
		products = getProductsFromResultSet(rs);
		rs.close();
		cs.close();
		return products;
	}

	@Override
	public ArrayList<Products> getCatProductsSort(String catid, String sortid) throws SQLException {
		cs = con.prepareCall("{call getCatProdSort(?,?)}");
		cs.setInt(1, Integer.parseInt(catid));
		cs.setInt(2, Integer.parseInt(sortid));
		rs = cs.executeQuery();
		products = getProductsFromResultSet(rs);
		rs.close();
		cs.close();
		return products;
	}
	
    public void insertProducts(Map<Integer, Integer> proidQuantityMap) throws SQLException {
        String sql = "SELECT insert_product(?, ?)"; // Assuming "insert_product" is a stored function

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            for (Map.Entry<Integer, Integer> entry : proidQuantityMap.entrySet()) {
                int proid = entry.getKey();
                int quantity = entry.getValue();

                // Set parameters and execute the SQL statement
                stmt.setInt(1, proid);
                stmt.setInt(2, quantity);
                stmt.execute();
            }
        }
        
    }
    public ArrayList<ArrayList<Products>> getOrders(String Order_id) throws SQLException {
        ArrayList<ArrayList<Products>> allOrders = new ArrayList<>();

        {
        	   String query = "SELECT p.* FROM orders_orders o " +
                       "JOIN products_225 p ON o.Order_id = p.Order_id " +
                       "WHERE o.Order_id = ?";
               ps =con.prepareStatement(query);
            ps.setString(1, Order_id);
         rs = ps.executeQuery();

            // Iterate through the result set and add order details to the ArrayList
            while (rs.next()) {
                // Extract data from the ResultSet to create a Products object
                int proid = rs.getInt("proid");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                String imgpath = rs.getString("imgpath");
                int catid = rs.getInt("catid");

                // Create a new Products object with extracted data
                Products product = new Products();
                product.setProduct_id(proid);
                product.setProduct_name(name);
                product.setProduct_price(price);
                product.setProduct_image(imgpath);
                product.setProduct_catid(catid);

                // Add the Products object to an ArrayList representing one order
                ArrayList<Products> orderDetails = new ArrayList<>();
                orderDetails.add(product);

                // Add the ArrayList of order details to the list of all orders
                allOrders.add(orderDetails);
            }

        return allOrders;
    }
}
}