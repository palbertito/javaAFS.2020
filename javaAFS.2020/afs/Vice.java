// Interfaz de servidor que define los métodos remotos para iniciar
// la carga y descarga de ficheros
package afs;

import java.io.*;
import java.rmi.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public interface Vice extends Remote {
	/* añada los parámetros que requiera */
	public ViceReader download(String nombreArchivo, String modo)
			throws RemoteException, FileNotFoundException, IOException;

	/* añada los parámetros que requiera */
	public ViceWriter upload(String nombreArchivo,String modo)
			throws RemoteException, FileNotFoundException, IOException;

	/* añada los métodos remotos que requiera */
}
