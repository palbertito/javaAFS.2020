// Clase de cliente que inicia la interacción con el servicio de
// ficheros remotos
package afs;

import java.net.MalformedURLException;
import java.rmi.*;

public class Venus {
	
	private String host;
	private int port;
	private int blockSize;

	private Vice vice;

	public Venus() throws NotBoundException, MalformedURLException, RemoteException {
		this.host = System.getenv("REGISTRY_HOST");
		this.port = Integer.parseInt(System.getenv("REGISTRY_PORT"));
		this.blockSize = Integer.parseInt(System.getenv("BLOCKSIZE"));
		this.vice = (Vice) Naming.lookup("//" + host + ":" + port + "/AFS");
	}

	public String getHost() {
		return this.host;
	}

	public int getPort() {
		return this.port;
	}

	public int getBlockSize() {
		return this.blockSize;
	}

	public Vice getVice() {
		return this.vice;
	}

}
