import java.io.*;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        processBuilderExample(args, getPath());
    }

    private static void processBuilderExample(String[] args, String path) {
        ArrayList<String> miComando = new ArrayList<String>();
        boolean copy = createCopy();

        for (String arg : args) { // Recorremos el vector y lo listamos en un String sD:\
            miComando.add(arg);
        }
        try {
            miComando.add(path); // Unimos el comando a la Ruta.
            Process pb = new ProcessBuilder(miComando).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            String line;
            if (copy) {
                String dowloadPath = "C:\\Users\\" + System.getProperty("user.name") + "\\Downloads";
                String copyName = getCopyName(dowloadPath);
                File file = new File(dowloadPath + "\\" + copyName); // Search the user.
                BufferedWriter bw = new BufferedWriter(new FileWriter(file)); // Write and print the result.
                clearConsole();
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    bw.write(line);
                    bw.newLine();
                }
                bw.write("PID: " + pb.pid());
                bw.close();

                System.out.println("\n------------------- La copia se ha creado con exito. -------------------");
                System.out.println("Se encuentra en: \n" + dowloadPath);
                System.out.println("Con el nombre: \n" + copyName);
                openFolder(dowloadPath + "\\" + copyName);

            } else {
                clearConsole();
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }  
            br.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private static void clearConsole() {
        // Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    private static boolean createCopy() {
        boolean ans = true;
        // 1 - Crear log.
        // 2 - No Crear log.
        // 0 - Salir
        int menuNum;

        System.out.println("\n------------------------------------------------" +
        "\n----------------- Opciones Log -----------------" +
        "\n------------------------------------------------" +
        "\n\n1 - Crear log. " +
        "\n2 - No Crear log. " +
        "\n0 - Salir");

        menuNum = InputManager.readNumInRange("\nInserta numero de opcion:", 0, 2);

        switch(menuNum) {
            case 0:
                break;
            case 1:
                ans = true;
                break;
            case 2:
                ans = false;
                break; 
        } 
        return ans;
    }

    private static String getCopyName(String dowloadPath) {
        String ans = "";
        String copyName = "";
        do {
            copyName = InputManager.readString("Inserta el nombre que le quieres dar a la copia") + ".txt";
            File f = new File(dowloadPath + "\\" + copyName);
            if(f.exists()){
                System.out.println("Un archivo con ese nombre ya existe");
                System.out.println("¿Desea sobrescribirlo? (s/n)");
                ans = InputManager.readChar("");
            } else {
                ans = "s";
            }
        } while(!ans.equalsIgnoreCase("s")); 

        return copyName;
    }

    private static void openFolder(String path) {
        try {
            new ProcessBuilder("notepad",  path).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getPath() {
        String carpeta = InputManager.readString("Inserta la ruta de la carpeta cuyo contenido desea listar ");
        File ncarpeta = new File(carpeta);
        while (!ncarpeta.exists()) {
            carpeta = InputManager.readString("La carpeta no existe, inserte otra ruta: ");
            ncarpeta = new File(carpeta);
        }

        while (!ncarpeta.isDirectory()) {
            carpeta = InputManager.readString("La direccion no es una carpeta, inserte otra ruta: ");
            ncarpeta = new File(carpeta);
        }

        return carpeta;
    }
    
}
