package dev.iesfranciscodelosrios.acdmusic.Services;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FilesS {
    public boolean CopyFile(String pathFile, String destination) throws IOException {

        File file = new File(destination+"."+getExtension(pathFile));
        if (!file.exists()){
            Path fuente = Paths.get(pathFile);
            Path destino = Paths.get(destination+"."+getExtension(pathFile));
            //copiar el archivo en la carpeta de destino y reemplazarlo si existe
            Files.copy(fuente, destino, StandardCopyOption.REPLACE_EXISTING);
            return true;
        }
        return false;
    }
    public String getExtension(String pathFile){
        //Dividimos el path para obtener la extension del archivo entre otros
        String[] aux=pathFile.split("\\.");
        return aux[aux.length-1];
    }
}
