import java.net.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

public class download {
	
	final int maxSamples = 100;
	int numSamplesLoaded = 0;
	String urlservice = "http://iotserver1.dis.eafit.edu.co/weather/";
	
	class Sample {
		float temp;
		float humid;
		Date timestamp;
	}
	
	Sample[] samples = new Sample[maxSamples];
	
	float predtemp;
	float predhumid;
	
	public void sendPrediction(String username, float predtemp, float predhumid, Date timestamp) {
		
	}
	
	public void loadSample(String line) {
		String[] tokens = line.split(",");
		Sample sample = new Sample();
		sample.temp = Float.parseFloat(tokens[1]);
		sample.humid = Float.parseFloat(tokens[2]);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		try {
			sample.timestamp =  dateFormat.parse(tokens[3].replaceAll(".000Z$", "+0000"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		samples[numSamplesLoaded] = sample;
		numSamplesLoaded++;
	}
	
	public void calcPrediction() {
		int N;
		float acumtemp = 0;
		float acumhumid = 0;
		for (int i=0; i< numSamplesLoaded;i++) {
			acumtemp=acumtemp+samples[i].temp;
			acumhumid=acumhumid+samples[i].humid;
		}
		predtemp = acumtemp/numSamplesLoaded;
		predhumid = acumhumid/numSamplesLoaded;
		
		System.out.println("predtemp:"+predtemp);
		System.out.println("predhumid:"+predhumid);
		
		
	}
	
	public void connectionAndLoad(String url, String filename) {
		numSamplesLoaded = 0;
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filename));
			URL urlservice = new URL(url);
			URLConnection urlserviceconn = urlservice.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlserviceconn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				loadSample(inputLine);
				out.write(inputLine+"\n");
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Datos guardados en: "+filename);
		
	}
	
	public void run(String username) {
		String url = urlservice+username;
		String filename = username+".csv";
		connectionAndLoad(url, filename);
		
	}

	public static void main(String[] args) {
		download dload = new download();
		dload.run(args[0]);
		dload.calcPrediction();
	}
}