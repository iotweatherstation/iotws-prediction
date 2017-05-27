import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Utilidades {
	
	public void getTempHumidByUser(String username) {
		String service1 = "http://iotserver1.dis.eafit.edu.co/weather/"+username;
		try {
			URL urlservice = new URL(service1);
			URLConnection urlserviceconn = urlservice.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlserviceconn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println("entry="+inputLine);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveTempHumidByUser(String username) {
		String service1 = "http://iotserver1.dis.eafit.edu.co/weather/"+username;
		String filename = username+".csv";
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filename));
			URL urlservice = new URL(service1);
			URLConnection urlserviceconn = urlservice.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlserviceconn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				out.write(inputLine+"\n");
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Datos guardados en: "+filename);
	}
	
	public void sendPrediction() {
		String idhome = "emontoya";
		String predTemp = "22.1";
		String predHumid = "87.3";
		String timestamp = "2017-05-27T08:13:12.0Z";
		
		String service2 = "http://iotserver1.dis.eafit.edu.co/weather/sendMyPrediction"+"?idhome="+idhome+"&predtemp="+predTemp+"&predhumid="+predHumid+"&timestamp="+timestamp;

		try {
			URL urlservice = new URL(service2);
			URLConnection urlserviceconn = urlservice.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlserviceconn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine+"\n");
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Utilidades utils = new Utilidades();
		utils.getTempHumidByUser(args[0]);
		utils.saveTempHumidByUser(args[0]);
		utils.sendPrediction();
	}

}
