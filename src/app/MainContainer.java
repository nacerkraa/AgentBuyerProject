package app;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;

public class MainContainer {
	public static void main(String[] args) {
		
		try {
			
			Properties properties = new ExtendedProperties();
			properties.setProperty(Profile.GUI, "true");
			ProfileImpl profileImpl = new ProfileImpl(properties);
			
			Runtime runtime = Runtime.instance();
			AgentContainer container = runtime.createMainContainer(profileImpl);
			
			container.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
