package mcps.po.services;

import com.sun.jersey.api.core.PackagesResourceConfig;



public class MyApplication extends PackagesResourceConfig {
	public MyApplication(){
		super("mcps.po.rest");
	}
}
