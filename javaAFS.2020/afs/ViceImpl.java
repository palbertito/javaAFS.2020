// Implementación de la interfaz de servidor que define los métodos remotos
// para iniciar la carga y descarga de ficheros
package afs;
import java.rmi.*;
import java.io.*;
import java.rmi.server.*;

public class ViceImpl extends UnicastRemoteObject implements Vice {
    public ViceImpl() throws RemoteException  {
    }
    public ViceReader download(String nombreArchivo, String modo /* añada los parámetros que requiera */)
          throws RemoteException, FileNotFoundException{
    	ViceReader viceReader = new ViceReaderImpl(nombreArchivo, modo);
       return viceReader;
    
    }
    public ViceWriter upload(String nombreArchivo, String modo /* añada los parámetros que requiera */)
          throws RemoteException , FileNotFoundException {
        ViceWriter viceWriter = new ViceWriterImpl(nombreArchivo, modo);
        return viceWriter;
    }
}
