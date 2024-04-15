package csci5409.k8.calculationservice.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import csci5409.k8.calculationservice.model.RequestData;
import csci5409.k8.calculationservice.util.CalculationUtil;

@RestController
public class CalculationController {

	@Autowired
	private CalculationUtil calculationUtil;
	
	@PostMapping("/getCalculation")
	public String getCalculation(@RequestBody RequestData data) {
		JSONObject response = calculationUtil.getProductCalculation(data);
		return response.toString();
	}
}
