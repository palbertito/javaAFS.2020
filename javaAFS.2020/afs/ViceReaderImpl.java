// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la descarga de un fichero
package afs;

import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class ViceReaderImpl extends UnicastRemoteObject implements ViceReader {
	private static final String AFSDir = "AFSDir/";

	//Parametros
	private String nombreArchivo;
	private RandomAccessFile randomAccessFile;


	public ViceReaderImpl(String nombreArchivo, String modo) throws FileNotFoundException, RemoteException {
        this.nombreArchivo = nombreArchivo;
        randomAccessFile = new RandomAccessFile(AFSDir+nombreArchivo, modo);
	}

	public byte[] read(int tam) throws RemoteException, IOException {
		byte []buf = null;
		long length = this.randomAccessFile.length();
		long offset = this.randomAccessFile.getFilePointer();

		//hemos llegado al final del archivo
		if (offset == length) {
			return buf;
		}
		//caso en el que podemos leer todo el tamanyo pedido
		if (length >= offset + tam) {
			buf = new byte[tam];
		//caso en el no podemos leer todo el tamanyo pedido
		} else if(length < offset + tam) {
			buf = new byte[(int) (length - offset)];
		}
		randomAccessFile.read(buf);
		return buf;
	}

	public void close() throws RemoteException, IOException {
		randomAccessFile.close();
	}

}
