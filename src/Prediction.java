import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Prediction {
	
	/* Variables y métodos para cargar los datos a un arreglo llamado "samples"
	 * sobre el cual se realizará el calculo de la predicción futura de la temp y humid
	 */
	
	final int maxSamples = 1000;
	int numSamplesLoaded = 0;
	
	class Sample {
		float temp;
		float humid;
		Date timestamp;
	}
	
	Sample[] samples = new Sample[maxSamples];
	
	public void loadSample(String line) {
		String[] tokens = line.split(",");
		Sample sample = new Sample();
		sample.temp = Float.parseFloat(tokens[1]);
		sample.humid = Float.parseFloat(tokens[2]);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		try {
			sample.timestamp =  dateFormat.parse(tokens[3].replaceAll(".000Z$", "+0000"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		samples[numSamplesLoaded] = sample;
		if (numSamplesLoaded < maxSamples) // asegura que no sobrepasará el número máximo de muestras cargadas.
			numSamplesLoaded++;
	}
	
	public void getTempHumidByUser(String username) {
		String service1 = "http://iotserver1.dis.eafit.edu.co/weather/"+username;
		numSamplesLoaded = 0;
		try {
			URL urlservice = new URL(service1);
			URLConnection urlserviceconn = urlservice.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlserviceconn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println("getTempHumidByUser="+inputLine);
				loadSample(inputLine);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveTempHumidByUser(String username) {
		String service1 = "http://iotserver1.dis.eafit.edu.co/weather/"+username;
		numSamplesLoaded = 0;
		String filename = username+".csv";
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filename));
			URL urlservice = new URL(service1);
			URLConnection urlserviceconn = urlservice.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlserviceconn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println("saveTempHumidByUser="+inputLine);
				out.write(inputLine+"\n");
				loadSample(inputLine);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				System.out.println("sendPrediction="+inputLine+"\n");
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getPrediction() {
		String idhome = "emontoya";
		String service3 = "http://iotserver1.dis.eafit.edu.co/weather/getMyPrediction"+"?idhome="+idhome;
		try {
			URL urlservice = new URL(service3);
			URLConnection urlserviceconn = urlservice.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlserviceconn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println("getPrediction="+inputLine+"\n");
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		
		/* Este programa se ejecuta así:
		 * 
		 * en Linux o Mac:
		 * 	 $ java Prediction emontoya
		 *   $ cd iotws-prediction/bin  (se asume que alli esta la clase: Prediction.class, de lo contrario ir al directorio donde este
		 * 
		 * en Windows:
		 * 
		 * c:\> cd iotws-prediction/bin
		 * c:\> java Prediction emontoya
		 * 
		 */
		
		Prediction prediction = new Prediction();
		prediction.getTempHumidByUser(args[0]);
		prediction.saveTempHumidByUser("emontoya");
		prediction.sendPrediction();
		prediction.getPrediction();
	}

}
