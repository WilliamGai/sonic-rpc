package org.sonic.srpc.excample.provider;

import org.sonic.rpc.core.annotation.SService;
import org.sonic.srpc.excample.api.PlateService;

@SService

public class PlateServiceImpl implements PlateService {
	public String applyOnePalte(String name){
		return name +"" + PlateGenerateUtil.geneateOnePlate();
	}

	@Override
	public String applyOnePalte(String name, String zoneName) {
		return name +"" + PlateGenerateUtil.geneateOnePlate(zoneName);
	}
}
