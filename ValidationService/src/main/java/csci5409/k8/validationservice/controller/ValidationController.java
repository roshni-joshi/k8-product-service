package csci5409.k8.validationservice.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import csci5409.k8.validationservice.model.RequestData;
import csci5409.k8.validationservice.util.ValidationUtil;

@RestController
public class ValidationController {

	@Autowired
	private ValidationUtil validationUtil;
	
	@PostMapping("/store-file")
	public String storeFile(@RequestBody RequestData data) {
		JSONObject response = validationUtil.storeProductsData(data);
		return response.toString();
	}
	
	@PostMapping("/calculate")
	public String validateData(@RequestBody RequestData data) {
		JSONObject response = validationUtil.validate(data);
		if(response == null) {
			response = validationUtil.callCalculationService(data);
			response.put("file", data.getFile());
		}
		return response.toString();
	}
}
