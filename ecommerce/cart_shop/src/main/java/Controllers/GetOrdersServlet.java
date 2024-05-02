package Controllers;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import DBCon.DB_Properties;
import DBCon.StoreDAO;
import Model.Products;

@WebServlet("/GetOrdersServlet")
public class GetOrdersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String Order_id = request.getParameter("Order_id");

        // Instantiate DB_Properties to access the database methods
        StoreDAO db = null;
        try {
            db = new DB_Properties();
            ArrayList<ArrayList<Products>> orders = db.getOrders(Order_id);

            // Convert orders ArrayList to JSON
            Gson gson = new Gson();
            String jsonOrders = gson.toJson(orders);

            // Send JSON response
            response.getWriter().write(jsonOrders);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception according to your application's requirements
            response.getWriter().write("Error: Unable to fetch orders.");
        } finally {
            
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
