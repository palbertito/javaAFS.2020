// Clase de cliente que define la interfaz a las aplicaciones.
// Proporciona la misma API que RandomAccessFile.
package afs;

import java.rmi.*;
import java.io.*;

public class VenusFile {

	private String nombreArchivo;
	private RandomAccessFile randomAccessFile;
	private boolean editado;
	public String modo;
	private Venus venus;

	public static final String cacheDir = "Cache/";

	public VenusFile(Venus venus, String nombreArchivo, String modo)
			throws RemoteException, IOException, FileNotFoundException {
			File file = new File(cacheDir + nombreArchivo);
			this.nombreArchivo = nombreArchivo;
			this.venus = venus;
			this.modo = modo;
			this.editado = false;
			ViceReader viceReader = venus.getVice().download(nombreArchivo, modo);
			if (!file.exists()) {		
				RandomAccessFile copiaCache = new RandomAccessFile(cacheDir + nombreArchivo, "rw");
				// leemos por bloque
				byte buffer[] = viceReader.read(venus.getBlockSize());
				while (buffer != null) {
					copiaCache.write(buffer);
					buffer = viceReader.read(venus.getBlockSize());
				}
				copiaCache.close();
				viceReader.close();
				randomAccessFile = new RandomAccessFile(cacheDir + nombreArchivo, modo);
			} else {
				// si esta en cache
				viceReader.close();
				randomAccessFile = new RandomAccessFile(cacheDir + nombreArchivo, modo);
			}
			

	}

	public int read(byte[] b) throws RemoteException, IOException {
		return randomAccessFile.read(b);
	}

	public void write(byte[] b) throws RemoteException, IOException {
		randomAccessFile.write(b);
		editado = true;
	}

	public void seek(long p) throws RemoteException, IOException {
		randomAccessFile.seek(p);
	}

	public void setLength(long l) throws RemoteException, IOException {
		randomAccessFile.setLength(l);
		editado = true;
	}

	public void close() throws RemoteException, IOException {
		try {
			if (modo.equals("rw")) {
				if (editado) {
					byte buffer[] = null;
					ViceWriter viceWriter = venus.getVice().upload(nombreArchivo, modo);
					randomAccessFile.seek(0);
					long length = randomAccessFile.length();
					long offset = randomAccessFile.getFilePointer();
					int tam = venus.getBlockSize();
					viceWriter.setLength(length);

					// caso en el que podemos leer todo el tamanyo
					while (length >= offset + tam) {
						buffer = new byte[tam];
						randomAccessFile.read(buffer);
						offset = randomAccessFile.getFilePointer();
						viceWriter.write(buffer);
					}
					// caso en el que no podemos leer todo el tamanyo
					if (offset < length) {
						int tamBuf = (int) (length - offset);
						buffer = new byte[tamBuf];
						randomAccessFile.read(buffer);
						viceWriter.write(buffer);
					}
					viceWriter.close();
				}
			} else {
				// cerrar sin haber escrito en el fichero xq modo r
				ViceWriter viceWriter = venus.getVice().upload(nombreArchivo, modo);
				viceWriter.close();

			}
			randomAccessFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
