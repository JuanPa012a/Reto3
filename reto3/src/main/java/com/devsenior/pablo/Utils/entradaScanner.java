package com.devsenior.pablo.Utils;

import java.io.InputStream;
import java.util.Scanner;

import com.devsenior.pablo.exception.ScannerError;

public final class entradaScanner {
    private final Scanner scanner;

    public entradaScanner(InputStream source) {
        if (source == null) {
            throw new ScannerError("El InputStream no puede ser null");
        }
        this.scanner = new Scanner(source);
    }

    // Métodos delegados al Scanner con manejo de errores
    public String next() {
            if (!scanner.hasNextLine()) {
                throw new ScannerError("No hay más líneas para leer");
            }
            return scanner.next();
    }

    public int nextInt() {
              if (!scanner.hasNextInt()) {
                throw new ScannerError("El caracter ingresado no cumple con lo solicitado");
            }
            return scanner.nextInt();
    }
    public Long nextLong(){
        if (!scanner.hasNextLong()) {
            throw new ScannerError("El caracter ingresado no cumple con lo solicitado");
        }
        return scanner.nextLong();
    }

    public double nextDouble() {
       
            if (!scanner.hasNextDouble()) {
                throw new ScannerError("El siguiente valor no es un número decimal");
            }
            return scanner.nextDouble();
       
    }

    public boolean hasNext() {
            return scanner.hasNext();
        
    }

    public void close() {
            scanner.close();
    }

    // Método para limpiar el buffer después de una lectura incorrecta
    public void cleanBuffer() {
            scanner.nextLine();
    }
}
