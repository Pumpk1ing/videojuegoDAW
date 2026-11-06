import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        // ---- Parte declarativa
        int opcion = 0;
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        // Arrays lineales que contienen los nombres de las plantas, su precio en la tienda, el dinero que producen
        //  y los símbolos gráficos para represnetar en las parcelas
        final String[] NOMBRES_PLANTAS = {"Hierbajo Feliz", "Girasol Tristón", "Arbusto Enfadado", "Hiperdestructor de mundos"};
        final int[] PRECIO_PLANTAS = {35, 80, 150, 0};
        final int[] PRODUCCION_PLANTAS = {20, 50, 100, 999};
        final String[] SIMBOLO_PLANTAS = {" :D", " :(", ">:(", "|*|"};

        // Arrays lineales que contienen los nombres de los productos de la tienda que NO son plantas y su precio
        final String[] NOMBRES_PRODUCTOS = {"Néctar de avispa", "Savia hipercalórica", "Granja killer 9000"};
        final int[] PRECIO_PRODUCTOS = {10, 30, 0};
        final int[] EFECTO_PRODUCTOS = {5, 10, -999};

        // Variables de la partida (se irán actualizando con las acciones del juego)
        int dinero = 0;             // dinero actual
        int turno = 0;              // número de turno (se resetea)
        int renta = 0;              // cuánto cuesta la próxima renta
        final int turnosPorRenta = 8;     // cada cuántos turnos se paga
        int turnosHastaRenta = 0;   // cuenta atrás

        // Array donde declaro las parcelas en las que se guardan las plantas y los productos del inventario
        int[] parcelas = new int[5];
        int[] productosGuardados = new int[NOMBRES_PRODUCTOS.length];
        int[] bonusParcelas = new int[5];


        System.out.println();
        System.out.println("------EL DINERO CRECE DE LOS ÁRBOLES------");
        System.out.println("       1. Empezar una partida");
        System.out.println("       2. Salir del programa");
        System.out.print("          Elige una opción: ");

        opcion = Integer.parseInt(buffer.readLine());

        switch (opcion) {
            case 1: {
                    int accion = 0;
                    dinero = 200;
                    turno = 1;
                    renta = 100; // valores iniciales de una partida + declaro la variable acción
                    turnosHastaRenta = turnosPorRenta;
                    for (int i = 0; i < parcelas.length; i++) {
                        parcelas[i] = -1;  // recorre las parcelas al iniciar la partida y se resetean
                    }
                    for (int i = 0; i < productosGuardados.length; i++){
                        productosGuardados[i] = 0; // recorre el inventario por medio de la constante de nombres
                        // y los resetea a 0 al empezar una partida 
                    }

                    boolean finalPartida = false; // variable booleana que declara el final de la partida

                    while (!finalPartida) { // mientras que la partida siga en curso, hace lo que está dentro

                        // variables esenciales de la partida (turno, dinero, renta a pagar y turnos hasta que tengas que pagarla)
                        System.out.println();
                        System.out.println("Turno " + turno + " | Dinero: " + dinero + " | Renta a pagar: " + renta + " en " + turnosHastaRenta + " turnos");
                        System.out.println();

                        // comienza el dibujo de la parcela
                        for (int i = 0; i < parcelas.length; i++) {
                            System.out.print("+-------+ "); // hace el "techo" de la parcela por cada parcela que existe
                        }
                        System.out.println();

                        for (int i = 0; i < parcelas.length; i++) {
                            if (parcelas[i] == -1) { // crea el tronco de la parcela si no tiene plantas
                                System.out.print("|       | ");
                            } else { // si la parcela tiene una planta, le pone el tronco con el símbolo de la planta
                                System.out.print("|  " + SIMBOLO_PLANTAS[parcelas[i]] + "  | ");
                            }
                        }
                        System.out.println();
                        for (int i = 0; i < parcelas.length; i++) {
                            System.out.print("|-------| "); // dibuja una separación entre la planta y el número de parcela
                        }
                        System.out.println();
                        for (int i = 0; i < parcelas.length; i++) {
                            System.out.print("|  [" + (i + 1) + "]  | "); // indica el número de parcela que es, tiene que
                            // sumar 1 ya que el array comienza en 0

                        }
                        System.out.println();
                        for (int i = 0; i < parcelas.length; i++) {
                            System.out.print("+-------+ ");
                        }
                        System.out.println();

                        System.out.println("1. Comprar plantas");
                        System.out.println("2. Comprar productos");
                        System.out.println("3. Gestionar productos");
                        System.out.println("4. Pasar de turno");
                        System.out.println("5. Terminar partida");
                        System.out.print(": ");

                        accion = Integer.parseInt(buffer.readLine());
                        switch (accion) {
                        case 1: {
                                int accionPlanta;
                                System.out.println("        --PLANTAS A COMPRAR--");
                                for (int i = 0; i < NOMBRES_PLANTAS.length; i++) { // recorre el array NOMBRES_PLANTAS
                                    System.out.println((i + 1) + ". " + SIMBOLO_PLANTAS[i] + " " + NOMBRES_PLANTAS[i]
                                    + " - " + PRECIO_PLANTAS[i] + " dinero (produce " + PRODUCCION_PLANTAS[i] + "por turno)");
                                } // recorre todos los nombres de las plantas y a cada una le pone su símbolo, nombre, precio y producción
                                // al haber un println; cada recorrido es una línea, por lo que las plantas salen ordenadas
                                System.out.println("0. Cancelar");
                                System.out.println("Dinero disponible: " + dinero);
                                System.out.print("¿Qué planta quieres comprar?: ");

                                accionPlanta = Integer.parseInt(buffer.readLine());
                                if (accionPlanta == 0) { // si se escribe 0 se cancela la compra
                                    System.out.println("Compra cancelada.");
                                } else if (accionPlanta < 1 | accionPlanta > NOMBRES_PLANTAS.length) {
                                    System.out.println("Planta no válida."); // si se pone un número menor a 1 o mayor que el dominio no se acepta la compra
                                } else { // si se hace correctamente
                                    int planta = accionPlanta - 1; // resta 1 pues los Array comienzan en 0
                                    if (dinero < PRECIO_PLANTAS[planta]) {
                                        System.out.println("No tienes dinero suficiente para " + NOMBRES_PLANTAS[planta] + ".");
                                    } else {
                                        System.out.print("¿En qué parcela (1-" + parcelas.length + ")? "); // elige la parcela
                                        int parcelaElegida;
                                        parcelaElegida = Integer.parseInt(buffer.readLine()) - 1; // se escribe 1-5, el array usa 0-4
                                        if (parcelaElegida < 0 || parcelaElegida >= parcelas.length) {
                                            System.out.println("Parcela no válida.");
                                        } else if (parcelas[parcelaElegida] != -1) {
                                            System.out.println("Esa parcela ya está ocupada.");
                                        } else {
                                            dinero = dinero - PRECIO_PLANTAS[planta];
                                            parcelas[parcelaElegida] = planta;
                                        }
                                    }
                                }
                                break;
                            }

                        case 2: {
                                System.out.println("        --PRODUCTOS A COMPRAR--");
                                for (int i = 0; i < NOMBRES_PRODUCTOS.length; i++) {
                                    System.out.println((i + 1) + ". " + NOMBRES_PRODUCTOS[i] + " - " + PRECIO_PRODUCTOS[i]
                                    + " dinero (da +" + EFECTO_PRODUCTOS[i] + " de producción por turno)"); // mismo array que la tienda de plantas pero para los productos
                                }
                                System.out.println("0. Cancelar");
                                System.out.println("Dinero disponible: " + dinero);
                                System.out.print("¿Qué producto quieres comprar?: ");

                                int accionProducto = Integer.parseInt(buffer.readLine());
                                if (accionProducto == 0) {
                                    System.out.println("Compra cancelada.");
                                } else if (accionProducto < 1 || accionProducto > NOMBRES_PRODUCTOS.length) {
                                    System.out.println("Producto no válido.");
                                } else {
                                    int producto = accionProducto - 1; 
                                    if (dinero < PRECIO_PRODUCTOS[producto]) {
                                        System.out.println("No tienes dinero suficiente para " + NOMBRES_PRODUCTOS[producto] + ".");
                                    } else {
                                        dinero = dinero - PRECIO_PRODUCTOS[producto];
                                        productosGuardados[producto]++;  // Suma una unidad de ese producto al inventario
                                        System.out.println("Has comprado " + NOMBRES_PRODUCTOS[producto] + ".");
                                    }
                                }
                                break;
                            }
                        case 3: {
                                System.out.println("        --INVENTARIO--");
                                for (int i = 0; i < NOMBRES_PRODUCTOS.length; i++) {
                                    System.out.println((i + 1) + ". " + NOMBRES_PRODUCTOS[i]
                                    + " - tienes " + productosGuardados[i] + " (da +" + EFECTO_PRODUCTOS[i] + " de producción por turno)");
                                }
                                System.out.println("0. Cancelar");
                                System.out.print("¿Qué producto quieres usar?: ");

                                int accionUso = Integer.parseInt(buffer.readLine());
                                if (accionUso == 0) {
                                    System.out.println("Uso cancelado.");
                                } else if (accionUso < 1 || accionUso > NOMBRES_PRODUCTOS.length) {
                                    System.out.println("Producto no válido.");
                                } else {
                                    int producto = accionUso - 1;
                                    if (productosGuardados[producto] <= 0) {
                                        System.out.println("No tienes " + NOMBRES_PRODUCTOS[producto] + ".");
                                    } else {
                                        System.out.print("¿En qué parcela lo quieres usar (1-" + parcelas.length + ")? ");
                                        int parcelaElegida = Integer.parseInt(buffer.readLine()) - 1;
                                        if (parcelaElegida < 0 || parcelaElegida >= parcelas.length) {
                                            System.out.println("Parcela no válida.");
                                        } else if (parcelas[parcelaElegida] == -1) {
                                            System.out.println("Esa parcela está vacía, no hay planta a la que dárselo.");
                                        } else {
                                            productosGuardados[producto]--;
                                            bonusParcelas[parcelaElegida] += EFECTO_PRODUCTOS[producto];
                                            System.out.println("Has usado " + NOMBRES_PRODUCTOS[producto] + " en "
                                            + NOMBRES_PLANTAS[parcelas[parcelaElegida]] + " (parcela " + (parcelaElegida + 1) + ").");
                                        }
                                    }
                                }
                                break;
                            }
                        case 4: {
                                //
                                int ingresos = 0; // creo la variable que sube el dinero cada turno
                                for (int i = 0; i < parcelas.length; i++) {
                                    if (parcelas[i] != -1) {
                                        ingresos += PRODUCCION_PLANTAS[parcelas[i]] + bonusParcelas[i];
                                    }
                                }
                                dinero += ingresos;
                                System.out.println("Tus plantas han producido " + ingresos + " de dinero.");
                                turno++; // avance de turno
                                turnosHastaRenta--;
                                if (turnosHastaRenta == 0) {
                                    if (dinero >= renta) {
                                        dinero = dinero - renta;
                                        System.out.println("Has pagado la renta de " + renta + ".");
                                        turnosHastaRenta = turnosPorRenta;
                                        renta += 50;
                                    } else {
                                        System.out.println("No puedes pagar la renta. Has perdido en el turno " + turno + ".");
                                        finalPartida = true;
                                    }
                                }
                                break;
                            }
                        case 5:
                            finalPartida = true;
                            break;
                        default:
                            System.out.println("Acción no válida.");
                        }
                    }
                    break;

                }
            case 2:
                System.out.println("¡Hasta pronto!"); 
                break;
            default:
                System.out.println("Opción no válida. Elige entre 1 y 2.");
        }
    }
}
