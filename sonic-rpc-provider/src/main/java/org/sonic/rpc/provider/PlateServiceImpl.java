package org.sonic.rpc.provider;

import org.sonic.rpc.provider.annotation.SService;
import org.sonic.tcp.rpc.api.PlateService;

@SService

public class PlateServiceImpl implements PlateService {
	public String applyOnePalte(String name){
		return name +"" + PlateGenerateUtil.geneateOnePlate();
	}
}
