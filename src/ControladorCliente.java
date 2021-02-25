import java.io.*;
import java.sql.SQLOutput;
import java.util.Scanner;

public class ControladorCliente {
    public static void main(String[] args) throws IOException {

        boolean ejecucion;

        String nombre = "";
        String lineaEdad = "";
        int edad;

        File fichero = new File("cliente.info");
        File ficheroTemporal = new File("clienteTemporal.info");

        FileInputStream streamEntrada;
        FileOutputStream streamSalida;

        ObjectInputStream lectorObjeto = null;
        ObjectOutputStream escritorObjeto = null;

        Cliente clienteNuevo = null;
        Cliente clienteTemporal;
        Scanner inputUsuario = new Scanner(System.in);

        ejecucion = true;

        System.out.println("Introduce el nombre: ");
        nombre = inputUsuario.nextLine();
        System.out.println("Introduce la edad: ");
        lineaEdad = inputUsuario.nextLine();
        do {

            try{

                edad = Integer.parseInt(lineaEdad);

                clienteNuevo = new Cliente(nombre, edad);

                if(!fichero.exists()){
                    streamSalida = new FileOutputStream(fichero);
                    escritorObjeto = new ObjectOutputStream(streamSalida);
                    escritorObjeto.writeObject(clienteNuevo);
                    escritorObjeto.close();

                    System.out.println(clienteNuevo.toString());
                } else {
                    streamEntrada = new FileInputStream(fichero);
                    lectorObjeto = new ObjectInputStream(streamEntrada);
                    clienteTemporal = (Cliente) lectorObjeto.readObject();

                    streamSalida = new FileOutputStream(ficheroTemporal);
                    escritorObjeto = new ObjectOutputStream(streamSalida);

                    while (clienteTemporal != null){
                        escritorObjeto.writeObject(clienteTemporal);
                        clienteTemporal = (Cliente) lectorObjeto.readObject();
                    }


                }


            }catch (IllegalArgumentException e){
                System.out.println("Error de entrada");
                System.out.println("Introduce la edad: ");
                lineaEdad = inputUsuario.nextLine();
            }catch (IOException e){
                escritorObjeto.writeObject(clienteNuevo);
                escritorObjeto.close();
                fichero.delete();
                ficheroTemporal.renameTo(fichero);
                ejecucion = false;
            }catch (ClassNotFoundException e){
                System.out.println("ClassNotFoundException");
            }

        } while (ejecucion);


        /*
        System.out.println("Introduce el nombre: ");
        nombre = inputUsuario.nextLine();

        System.out.println("Introduce la edad: ");
        edad = inputUsuario.nextInt();

        clienteNuevo = new Cliente(nombre, edad);

        System.out.println(clienteNuevo.toString());
         */


    }
}
