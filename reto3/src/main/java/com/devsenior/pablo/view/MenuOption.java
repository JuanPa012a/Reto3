package com.devsenior.pablo.view;

import com.devsenior.pablo.exception.indexOutFoundError;

public enum MenuOption {
    // Main Menu Options
    LIBROS("Libros"),
    USUARIOS("Usuarios"),
    PRESTAMOS("Préstamos"),
    SALIDA("Salir"),

    // Book Menu Options
    AGREGAR_LIBRO("Agregar Libro"),
    ELIMINAR_LIBRO("Eliminar Libro"),
    CONSULTAR_LIBRO("Consultar Libro"),
    VOLVER_LIBROS("Volver"),

    // User Menu Options
    AGREGAR_USUARIO("Agregar Usuario"),
    ELIMINAR_USUARIO("Eliminar Usuario"),
    CONSULTAR_USUARIO("Consultar Usuario"),
    VOLVER_USUARIOS("Volver"),

    // Loan Menu Options
    AGREGAR_PRESTAMO("Agregar Préstamo"),
    DEVOLVER_LIBRO("Devolver Libro"),
    MOSTRAR_PRESTAMOS("Mostrar Préstamos"),
    CAMBIAR_LIMITE_PRESTAMO("Cambiar Límite Préstamo"),
    VOLVER_PRESTAMOS("Volver");

    private final String displayName;
    public static final MenuOption[] MAIN_MENU = { SALIDA, LIBROS, USUARIOS, PRESTAMOS };
    public static final MenuOption[] BOOK_MENU = { VOLVER_LIBROS, AGREGAR_LIBRO, ELIMINAR_LIBRO, CONSULTAR_LIBRO };
    public static final MenuOption[] USER_MENU = { VOLVER_USUARIOS, AGREGAR_USUARIO, ELIMINAR_USUARIO, CONSULTAR_USUARIO };
    public static final MenuOption[] LOAN_MENU = { VOLVER_PRESTAMOS, AGREGAR_PRESTAMO, DEVOLVER_LIBRO,
            MOSTRAR_PRESTAMOS, CAMBIAR_LIMITE_PRESTAMO };

    MenuOption(String displayName) {
        this.displayName = displayName;
    }

    public static void showMenu(MenuOption[] menuOptions) {
        for (int i = 0; i < menuOptions.length; i++) {
            System.out.println(i + ") " + menuOptions[i].displayName);
        }
    }
    
    public static MenuOption selectOption(MenuOption[] menuOptions, Integer selection) {
        if(selection < 0 || selection >= menuOptions.length) {
            throw new indexOutFoundError("Ese dato está fuera del rango, intenta nuevamente");
        }
        
        return menuOptions[selection];
    }
} 
    

