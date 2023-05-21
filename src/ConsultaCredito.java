  import java.io.IOException;
  import java.lang.IllegalStateException;
  import java.nio.file.Paths;
  import java.util.NoSuchElementException;
  import java.util.Scanner;

public class ConsultaCredito {
    private final static OpcionMenu[] opciones = OpcionMenu.values();
    public static void main(String[] args){
         // obtiene la solicitud del usuario (saldo de cero, con crédito o con débito)
         OpcionMenu tipoCuenta = obtenerSolicitud();
         while (tipoCuenta != OpcionMenu.FIN){
             switch (tipoCuenta){
                case SALDO_CERO:               
                    System.out.printf("%nCuentas con saldos de cero:%n");
                    break;
                case SALDO_CREDITO:
                    System.out.printf("%nCuentas con saldos con credito:%n");
                    break;
                case SALDO_DEBITO:
                    System.out.printf("%nCuentas con saldos con debito:%n");
                    break;           
            }
         leerRegistros(tipoCuenta);
         tipoCuenta = obtenerSolicitud();
       }
    }
    
    private static OpcionMenu obtenerSolicitud(){
        int solicitud = 4;
         System.out.printf("%nEscriba solicitud%n%s%n%s%n%s%n%s%n",
         " 1 - Lista de cuentas con saldos de cero",
         " 2 - Lista de cuentas con saldos con credito",
         " 3 - Lista de cuentas con saldos con debito",
         " 4 - Terminar programa");
         
         try{
            Scanner entrada = new Scanner(System.in);
         do{
             System.out.printf("%n? ");
             solicitud = entrada.nextInt();
            } while ((solicitud < 1) || (solicitud > 4));
         }
         catch (NoSuchElementException noSuchElementException){
             System.err.println("Entrada invalida. Terminando.");
         }
         return opciones[solicitud - 1]; // devuelve valor de enum para la opción
    }
    private static void leerRegistros(OpcionMenu tipoCuenta){
        try (Scanner entrada = new Scanner(Paths.get("clientes.txt"))){
            while (entrada.hasNext()){
                int numeroCuenta = entrada.nextInt();
                String primerNombre = entrada.next();
                String apellidoPaterno = entrada.next();
                double saldo = entrada.nextDouble();
                
                if (debeMostrar(tipoCuenta, saldo)){
                    System.out.printf("%-10d%-12s%-12s%10.2f%n", numeroCuenta,
                        primerNombre, apellidoPaterno, saldo);
                } else {
                    entrada.nextLine();
                }
            }
        }
        catch (NoSuchElementException | 
            IllegalStateException | IOException e){
            System.err.println("Error al procesar el archivo. Terminando.");
            System.exit(1);
        }
    }
    
    private static boolean debeMostrar(OpcionMenu tipoCuenta, double saldo){
        if ((tipoCuenta == OpcionMenu.SALDO_CREDITO) && (saldo < 0))
            return true;
        else if ((tipoCuenta == OpcionMenu.SALDO_DEBITO) && (saldo > 0))
                return true;
        else if ((tipoCuenta == OpcionMenu.SALDO_CERO) && (saldo == 0))
            return true;
        
        return false;
    } 
}

