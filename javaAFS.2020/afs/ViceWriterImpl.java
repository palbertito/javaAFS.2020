// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la carga de un fichero
package afs;

import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class ViceWriterImpl extends UnicastRemoteObject implements ViceWriter {

    private static final String AFSDir = "AFSDir/";

    private String nombreArchivo;
    private RandomAccessFile randomAccessFile;

    /* añada los parámetros que requiera */
    public ViceWriterImpl(String nombreArchivo, String modo) throws RemoteException, FileNotFoundException {
        this.nombreArchivo = nombreArchivo;
        randomAccessFile = new RandomAccessFile(AFSDir+nombreArchivo, modo);
    }

    public void write(byte[] b) throws RemoteException, IOException {
        randomAccessFile.write(b);
    }

    public void close() throws RemoteException, IOException {
        randomAccessFile.close();
    }

    public void setLength(long length) throws RemoteException, IOException {
        randomAccessFile.setLength(length);
    }
}

