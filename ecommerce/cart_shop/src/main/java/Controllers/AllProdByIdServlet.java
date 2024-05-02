package Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import DBCon.DB_Properties;
import DBCon.StoreDAO;
import Model.Products;

/**
 * Servlet implementation class AllProdById
 */
@WebServlet("/AllProdByIdServlet")
public class AllProdByIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
		String id = req.getParameter("val");
		if (id.equals("0")) {
			try {
				products = gap.getAllProducts();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				products = gap.getAllProductsId(id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (Products it : products) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(it.getProduct_id() + "");
			temp.add(it.getProduct_name());
			temp.add(it.getProduct_price() + "");
			temp.add(it.getProduct_image());
			temp.add(it.getProduct_catid() + "");
			ars.add(temp);
			System.out.println(ars);
		}
		try {
			ob.put("AllProducts", ars);
			res.getWriter().write(ob.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
