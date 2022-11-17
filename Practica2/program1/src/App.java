import java.io.*;

public class App {

    static String charString;
    static String path;
    static String replaceCharString;

    public static void main(String[] args) throws Exception {
        start(args);
    }

    private static void start(String[] args) {
        checkArgs(args);
    }

    /* This method checks how many args get the program */
    private static void checkArgs(String[] args) {
        switch(args.length == 2 ? 1 : args.length == 3 ? 2 : 0) {
            case 0: // Case 1, no args
                viewMenu();
                break;
            case 1: // Case 2, charString and path
                System.out.println(getOccurrencesNum(args[0], args[1]));
                System.exit(0);
                break;
                
            case 2: // Case 3, charString, path and replacement charString
                replaceCharString(args[0], args[1], args[2]);
                System.exit(0);
                break;
        }
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
            "\n\n1 - Introduce la ruta del archivo donde quieres buscar. " +
            "\n2 - Mostrar el número de ocurrencias del término en el archivo. " +
            "\n3 - Reemplazar palabra en el archivo" +
            "\n0 - Salir");
        
            menuNum = InputManager.readNumInRange("\nInserta numero de opcion:", 0, 3);
            menuFunc(menuNum);
        }

        /* This method is for the menu logic */
        private static void menuFunc(int menuNum) {
            switch(menuNum) {
                case 0: // Close
                    verifyClose();
                    break;
                case 1: // Get file path
                    getPath(0);
                    break;
                case 2: // Show number of occurrences
                    verifyData();
                    showOccurrencesNum();
                    break;
                case 3: // Perform word replacement
                    verifyData();
                    getReplaceCharString();
                    replaceCharString(path, charString, replaceCharString);
                    break;
            }
        }

    /* -- Menu -- */

    /* Occurrence Number */

        private static void verifyData() {
            if (path == null) {
                getPath(1);
                getCharString();
            } else {
                String ans = "";
                do {
                    System.out.println("La ruta a la cual se va ha acceder es :\n" + path );
                    System.out.println("¿Quieres usar esa ruta? (s/n)");
                    ans = InputManager.readChar("");     
                } while(!ans.equalsIgnoreCase("s") && !ans.equalsIgnoreCase("n"));

                if (ans.equalsIgnoreCase("n")) {
                    getPath(1);
                    getCharString();
                }
            }

        }

        /* This method show the number of occurrences in the file */
        private static void showOccurrencesNum() {
            System.out.print("\n-------------------------------------------" +
                            "\n---------------- Resultado ----------------" +
                            "\n-------------------------------------------" +
                            "\n\nSe han encontrado " + getOccurrencesNum(path, charString) + " ocurrencias de la palabra " + charString + " en el archivo.\n");
            verifyClose();
        }

        /* This method gets the number of occurrences in the file */
        private static int getOccurrencesNum(String path, String word) {
            int count = 0;
            try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) { 
                String line;
                while ((line = br.readLine()) != null) {
                    count += (line.length() - line.replace(word, "").length()) / word.length();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return count;
        } 

    /* Occurrence Number */

    /* Replace CharString  */

        /* This method replaces the char string with another one in a copied file 
        private static void replaceCharString(String path, String word, String repalceWord) {
            File file = new File(path);
            String dowloadPath = file.getParent();
            String copyName = getCopyName(dowloadPath);
            
            File copFile = new File(dowloadPath + "\\" + copyName);
            
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(copFile))) {
                BufferedReader br = new BufferedReader(new FileReader(new File(path)));
                String line;

                while ((line = br.readLine()) != null) {
                    bw.write(line.replaceAll(word, repalceWord));
                    bw.newLine();
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */

        private static void replaceCharString(String path, String word, String repalceWord) {
            File file = new File(path);
            String dowloadPath = file.getParent();
            String copyName = "NUEVO_" + file.getName();
            
            File copFile = new File(dowloadPath + "\\" + copyName);
            
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(copFile))) {
                BufferedReader br = new BufferedReader(new FileReader(new File(path)));
                String line;

                while ((line = br.readLine()) != null) {
                    bw.write(line.replaceAll(word, repalceWord));
                    bw.newLine();
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    /* Replace CharString  */

    /* Other Methods */

        /* This method get the new name for the file where the copy is to be made. */
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

        /* This method verify that user want to close the program. */
        private static void verifyClose() {
            String ans = "";
            do {
                System.out.println("¿Desea cerrar? (s/n)");
                ans = InputManager.readChar("");     
            } while(!ans.equalsIgnoreCase("s") && !ans.equalsIgnoreCase("n"));

            if (ans.equalsIgnoreCase("n")) {
                viewMenu();
            }
        }

        /* This method collects the file path */
        private static void getPath(int data) {
            String arch = InputManager.readString("Introduce la ruta del archivo donde quieres buscar");
            File narch = new File(arch);
            while (!narch.exists()) {
                arch = InputManager.readString("El archivo no existe, inserte otra ruta: ");
                narch = new File(arch);
            }
            while (!narch.isFile()) {
                arch = InputManager.readString("La direccion no es una archivo, inserte otra ruta: ");
                narch = new File(arch);
            }
            path = arch;
            if (data == 0) {
                viewMenu();
            }
        }
        
        private static void getCharString() {
            charString = InputManager.readString("Introduzca el termino a buscar");
        }

        private static void getReplaceCharString() {
            replaceCharString = InputManager.readString("Introduzca el nuevo termino");
        }

    /* Other Methods */
}
