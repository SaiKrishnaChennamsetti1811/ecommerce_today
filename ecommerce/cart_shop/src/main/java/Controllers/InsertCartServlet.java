package Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import DBCon.DB_Properties;
import DBCon.StoreDAO;
import Model.Item;

@WebServlet("/InsertCartServlet")


public class InsertCartServlet extends HttpServlet{
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		StoreDAO gap = null;
		try {
			gap = new DB_Properties();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  response.setContentType("application/json");
		  System.out.println("vgvhjhj");
	        BufferedReader reader = request.getReader();
	        StringBuilder jsonData = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            jsonData.append(line);
	        }
	        Gson gson = new Gson();
	        System.out.println("hii");
	        JsonObject jsonObject = gson.fromJson(jsonData.toString(), JsonObject.class);
	        System.out.println("hello");
	        JsonArray itemsArray = jsonObject.getAsJsonArray("var1");
	        Map<Integer, Integer> proidQuantityMap = new HashMap<>();
	        for (JsonElement element : itemsArray) {
	            JsonObject itemObject = element.getAsJsonObject();
	            int proid = itemObject.get("key").getAsInt();
	            int quantity = itemObject.get("data").getAsInt();
	            System.out.println(proid+" ffff "+quantity);
	            proidQuantityMap.put(proid, quantity);
	        };
	        try {
				gap.insertProducts(proidQuantityMap);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        }

	       
}