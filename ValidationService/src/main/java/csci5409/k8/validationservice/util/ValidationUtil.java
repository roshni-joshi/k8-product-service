package csci5409.k8.validationservice.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import csci5409.k8.validationservice.model.RequestData;

@Component
public class ValidationUtil {

	private static final String INVALID_INPUT = "Invalid JSON input.";
	private static final String FILE_NOT_FOUND = "File not found.";
	private static final String STORE_FILE_ERROR = "Error while storing the file to the storage.";
	
	@Value("${calculation.service.server.url}")
	private String calculationServerUrl;
	
	public JSONObject storeProductsData(RequestData data) {
		JSONObject response = null;
		if(data.getFile() == null || data.getFile().trim().isEmpty() || data.getFile().trim().isBlank()) {
			response = new JSONObject();
			response.put("file", JSONObject.NULL);
			response.put("error", INVALID_INPUT);
			return response;
		}
		try {
			File productsFile = new File("/roshni_PV_dir/" + data.getFile());
			if(productsFile.exists()) {
				productsFile.delete();
			}
			productsFile.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(productsFile));
			writer.write(data.getData());
			writer.close();
			response = new JSONObject();
			response.put("file", data.getFile());
			response.put("message", "Success.");
		} catch (Exception e) {
			response = new JSONObject();
			response.put("file", data.getFile());
			response.put("error", STORE_FILE_ERROR);
		}
		return response;
	}
	
	public JSONObject validate(RequestData data) {
		JSONObject response = null;
		if(data.getFile() == null || data.getFile().trim().isEmpty() || data.getFile().trim().isBlank() ||
				data.getProduct().trim().isEmpty() || data.getProduct().trim().isBlank()) {
			response = new JSONObject();
			response.put("file", JSONObject.NULL);
			response.put("error", INVALID_INPUT);
			return response;
		}
		
		if(!Files.exists(Paths.get("/roshni_PV_dir/" + data.getFile()))) {
			response = new JSONObject();
			response.put("file", data.getFile());
			response.put("error", FILE_NOT_FOUND);
			return response;
		}
		return response;
	}
	
	public JSONObject callCalculationService(RequestData data) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<RequestData> requestEntity = new HttpEntity<RequestData>(data);
		ResponseEntity<String> response = restTemplate.exchange(calculationServerUrl + "getCalculation", HttpMethod.POST, requestEntity, String.class);
		JSONObject responseJson = new JSONObject(response.getBody());
		return responseJson;
	}
}
