package csci5409.k8.calculationservice.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import csci5409.k8.calculationservice.model.RequestData;

@Component
public class CalculationUtil {

	public JSONObject getProductCalculation(RequestData data) {
		JSONObject response = new JSONObject();
		String product = data.getProduct();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File("/roshni_PV_dir/" + data.getFile())));
			String[] productHeading = reader.readLine().split(",");
			if(!productHeading[0].trim().equals("product") || !productHeading[1].trim().equals("amount")) {
				throw new Exception("Invalid heading");
			}
			String line;
			int count = 0;
			while((line = reader.readLine()) != null) {
				String[] productData = line.split(",");
				if(productData.length != 2) {
					throw new Exception("Invalid columns count");
				}
				if(!Pattern.matches("[a-zA-Z]+", productData[0].trim())) {
					throw new Exception("Invalid product name");
				}
				int productCount = Integer.parseInt(productData[1].trim());
				if(productData[0].equals(product)) {
					count = count + productCount;
				}
			}
			response.put("sum", count);
		} catch (Exception e) {
			try {
				reader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			response.put("error", "Input file not in CSV format.");
		}
		return response;
	}
}
