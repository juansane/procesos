import java.util.*;

import java.io.*;;

public class App {
    final static String PROGRAM1_PATH = "E:\\DAM2\\Procesos\\Practica_2\\Workspace\\program1\\bin";

    static ArrayList<String> pathList = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        start();
    }

    private static void start() {
        viewMenu();
    }

    /* -- Menu -- */

    /* This method is for the menu view */
    private static void viewMenu() {

        // 1 - Introduce la ruta del archivo donde quieres buscar.
        // 2 - Mostrar el número de ocurrencias del término en el archivo.
        // 3 - Realizar reemplazo
        // 0 - Salir

        int menuNum;
        System.out.println("\n-----------------------------------------------" +
                "\n---------------- Opciones Menu ----------------" +
                "\n-----------------------------------------------" +
                "\n\n1 - Listar archivos a explorar. " +
                "\n2 - Añadir archivo. " +
                "\n3 - Eliminar archivo. " +
                "\n4 - Buscar número de apariciones de una cadena. " +
                "\n5 - Reemplazar cadena. " +
                "\n0 - Salir");

        menuNum = InputManager.readNumInRange("\nInserta numero de opcion:", 0, 5);
        menuFunc(menuNum);
    }

    /* This method is for the menu logic */
    private static void menuFunc(int menuNum) {
        switch (menuNum) {
            case 0: // Close
                if (!verify("¿Desea cerrar?")) {
                    viewMenu();
                }
                break;
            case 1:
                listFilestoOpen();
                break;
            case 2:
                addFile();
                break;
            case 3:
                delleteFile();
                break;
            case 4:
                searchCharString();
                break;
            case 5:
                replaceCharString();
                break;
        }
    }

    /* -- Menu -- */

    /* -- File List -- */

    /* This method verify, to open one file of the list */
    private static void listFilestoOpen() {
        viewFileList();
        if (verify("¿Desea visualizar alguno?")) {
            openFile();
        } else {
            viewMenu();
        }
    }

    /* This method prints the list */
    private static void viewFileList() {

        System.out.println("\n-----------------------------------------------" +
                "\n------------- Listado de archivos -------------" +
                "\n-----------------------------------------------\n");

        for (int i = 0; i < pathList.size(); i++) {
            File file = new File(pathList.get(i));
            System.out.println(i + " - Nombre: " + file.getName() +
                    "\n    Ruta: " + file.getPath() + "\n");
        }
    }

    /* This method add file to the list */
    private static void addFile() {
        pathList.add(getPath());
        viewMenu();
    }

    /* This method dellete file on the list */
    private static void delleteFile() {
        viewFileList();
        pathList.remove(getNumList("\n¿Que archivo quieres borrar?"));
        viewMenu();
    }

    /* -- File List -- */

    /* -- Search CharString -- */

    private static void searchCharString() {
        String charString = InputManager.readString("Introduzca el termino a buscar");
        try {
            int exitValue = -1;
            Process start = null;
            System.out.println("\n-----------------------------------------------" +
                    "\n------------- Listado de archivos -------------" +
                    "\n-----------------------------------------------\n");
            for (int i = 0; i < pathList.size(); i++) {
                File file = new File(pathList.get(i));
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder("java.exe", "-cp", PROGRAM1_PATH, "App",
                            pathList.get(i), charString);
                    start = processBuilder.start();
                    try (BufferedReader ipBuf = new BufferedReader(new InputStreamReader(start.getInputStream()))) {
                        String line = null;
                        while ((line = ipBuf.readLine()) != null) {
                            System.out.println(i + " - Nombre: " + file.getName() +
                                    "\n    Ruta: " + file.getPath() +
                                    "\n    Coincidences: " + line);
                        }
                    }
                    try (BufferedReader errBuf = new BufferedReader(new InputStreamReader(start.getErrorStream()))) {
                        String line;
                        while ((line = errBuf.readLine()) != null) {
                            // LOGGER.warning(line);
                            System.out.println(line);
                        }
                    }
                    start.waitFor();
                    exitValue = start.exitValue();
                } finally {
                    if (start != null) {
                        start.destroy();
                    }
                }
                if (exitValue != 0) {
                    System.out.println("Algo ha salido mal ");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* -- Search CharString -- */

    /* -- Replace String -- */

    private static void replaceCharString() {
        String charString = InputManager.readString("Introduzca el termino a buscar");
        String replaceCharString = InputManager.readString("Introduzca el nuevo termino");

        int exitValue = -1;
        Process start = null;
        for (int i = 0; i < pathList.size(); i++) {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder("java.exe", "-cp", PROGRAM1_PATH, "App",
                        pathList.get(0), charString, replaceCharString);
                start = processBuilder.start();
                try (BufferedReader ipBuf = new BufferedReader(new InputStreamReader(start.getInputStream()))) {
                    String line = null;
                    while ((line = ipBuf.readLine()) != null) {
                        // Logger.info(line);
                        System.out.println(line);
                    }
                }
                try (BufferedReader errBuf = new BufferedReader(new InputStreamReader(start.getErrorStream()))) {
                    String line;
                    while ((line = errBuf.readLine()) != null) {
                        // LOGGER.warning(line);
                        System.out.println(line);
                    }
                }
                start.waitFor();
                exitValue = start.exitValue();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (start != null) {
                    start.destroy();
                }
            }
        }
        System.out.println(exitValue);

        /*
         * try {
         * int i = 0;
         * Process pb = new ProcessBuilder("java.exe", "-cp", PROGRAM1_PATH, "App",
         * pathList.get(i), charString, replaceCharString).start();
         * System.out.println(pb.exitValue());
         * pb.destroy();
         * if (pb.exitValue() != 0) {
         * System.out.println("Algo a salido mal");
         * }
         * } catch (IOException e) {
         * e.printStackTrace();
         * } catch (Exception e) {
         * e.printStackTrace();
         * }
         */
    }

    /* -- Replace String -- */

    /* -- Other Methods -- */

    /* This method Open the file with notepad */
    private static void openFile() {
        try {
            new ProcessBuilder("notepad", pathList.get(getNumList("\n¿Que archivo quieres abrir?"))).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* This method verify yes or no. */
    private static boolean verify(String text) {
        String ans = "";
        do {
            System.out.println(text + " (s/n)");
            ans = InputManager.readChar("");
        } while (!ans.equalsIgnoreCase("s") && !ans.equalsIgnoreCase("n"));

        if (ans.equalsIgnoreCase("n")) {
            return false;
        } else {
            return true;
        }
    }

    /* This method collects the file path */
    private static String getPath() {
        String arch = InputManager.readString("Introduce la ruta del archivo que quiere añadir a la lista: ");
        File narch = new File(arch);
        while (!narch.exists()) {
            arch = InputManager.readString("El archivo no existe, inserte otra ruta: ");
            narch = new File(arch);
        }
        while (!narch.isFile()) {
            arch = InputManager.readString("La direccion no es una archivo, inserte otra ruta: ");
            narch = new File(arch);
        }
        return arch;
    }

    private static int getNumList(String text) {
        System.out.println(text);
        return InputManager.readNumInRange("Indica el numero de archivo: ", 0, pathList.size());
    }

    /* -- Other Methods -- */

}