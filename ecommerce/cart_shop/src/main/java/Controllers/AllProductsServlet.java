package Controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import DBCon.DB_Properties;
import DBCon.StoreDAO;
import Model.Products;

@WebServlet("/AllProductsServlet")
public class AllProductsServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) {
		StoreDAO gap = null;
		try {
			gap = new DB_Properties();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Products> products = null;
		ArrayList<ArrayList<String>> ars = new ArrayList<>();
		JSONObject ob = new JSONObject();
		try {
			products = gap.getAllProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Products it : products) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(it.getProduct_id() + "");
			temp.add(it.getProduct_name());
			temp.add(it.getProduct_price() + "");
			temp.add(it.getProduct_image());
			temp.add(it.getProduct_catid() + "");
			ars.add(temp);
		}
		try {
			ob.put("AllProducts", ars);
			res.getWriter().write(ob.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
