package sg.edu.nus.cs2020;

/**
 * SampleCloudClient
 * @author DCSSLG
 * 
 * This is an example of how to use the Cloud Manager library.
 *
 */
public class SampleCloudClient {

	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args){
		
		// Start up a cloud server using Amazon EC2.
		// Each server should handle 1000 records.
		CloudManager manager = new CloudManager(ICloudManager.CloudProvider.AmazonEC2, 1000);
		
		// Get the current status of the server
		manager.getStatus();
		
		// Start up the cloud, loading the cloud resource "datafile.txt".
		// Use 10 servers.
		manager.initiliazeCloud("datafile.txt", 10);
		
		// Get the current status
		manager.getStatus();
		
		/*
		 * You can now use scheduleServer and executePhase to perform sorting.
		 */
		
		// Shut down the cloud server
		// Write the output to the file "data-out.txt"
		manager.shutDown("data-out.txt");
	}
}
