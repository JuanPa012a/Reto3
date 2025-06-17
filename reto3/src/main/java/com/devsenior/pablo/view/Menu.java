package com.devsenior.pablo.view;

import java.util.Scanner;

import static com.devsenior.pablo.Utils.IdGenerator.generateBookId;
import com.devsenior.pablo.exception.BookNotFoundException;
import com.devsenior.pablo.model.Book;
import com.devsenior.pablo.service.BookService;
import com.devsenior.pablo.service.LoanSerivce;
import com.devsenior.pablo.service.UserService;

public class Menu {
    private final LoanSerivce service;
    private final Scanner entrada;
    private Boolean estado;
    private Integer opcion;

    public Menu() {
        service = new LoanSerivce(new BookService(), new UserService());
        entrada = new Scanner(System.in);
        estado = true;
    }

    public void start() {
        new GeneralForms();
    }

    protected class GeneralForms {
        private final BookForms formBook = new BookForms();
        private final UserForms formUser = new UserForms();
        private final LoanForms formLoan = new LoanForms();

        public GeneralForms() {
            while (estado) {
                System.out.println("|--- MENU PRINCIPAL ---|");
                MenuOption.showMenu(MenuOption.MAIN_MENU);
                System.out.println("Selecciona la opcion que deseas: ");
                try {
                    opcion = entrada.nextInt();
                    switch (MenuOption.MAIN_MENU[opcion]) {
                        case LIBROS -> formBook.iniciar();
                        case USUARIOS -> formUser.iniciar();
                        case PRESTAMOS -> formLoan.iniciar();
                        case SALIDA -> estado = false;
                        default -> System.out.println("Opcion no valida");
                    }
                } catch (Exception ex) {
                    System.out.println("El dato ingresado no es valido");
                    entrada.nextLine();
                }
            }
        }
    }

    protected class BookForms {
        public void iniciar() {
            boolean estado = false;
            while(!estado){
            try {
                System.out.println("|--- MENU LIBROS ---|");
                MenuOption.showMenu(MenuOption.BOOK_MENU);
                opcion = entrada.nextInt();
                switch (MenuOption.BOOK_MENU[opcion]) {
                    case AGREGAR_LIBRO -> {
                        var id = generateBookId();

                        System.out.println("Ingresa el ISBN del libro: ");
                        var isbn = entrada.next();

                        System.out.println("Ingresa el titulo del libro:");
                        var title = entrada.next();

                        System.out.println("Ingresa el nombre del autor: ");
                        var autor = entrada.next();

                        var book = new Book(id + "", isbn, title, autor);
                        service.getBookService().addBook(book);
                        System.out.println("Guardado con éxito!");
                    }
                    case ELIMINAR_LIBRO -> {

                        var book = getFormBook();
                        service.getBookService().deleteBook(book);
                        System.out.println("Eliminado con éxito!");

                    }
                    case CONSULTAR_LIBRO -> {
                        var book = getFormBook();
                        service.getAllLoansFromBook(book.getId()).stream()
                        .forEach(l -> System.out.println(l));
                    }
                   
                    case VOLVER_LIBROS -> {
                        estado = true;
                    }
                    default -> {
                        System.out.println("Dato invalido, intenta nuevamente");
                    }
                }
            } catch (Exception ex) {
                System.out.println("Dato invalido, intenta nuevamente");
                entrada.nextLine();
            }
        }
        }

        private void showAllBooks() {
            service.getBookService().getAllBooks().stream()
                    .forEach(System.out::println);
        }

        private opcionBusqueda WhereFindBook() {
            opcionBusqueda opx = opcionBusqueda.ID;
            boolean ciclo = false;
            try {
                while (!ciclo) {
                    for (opcionBusqueda op : opcionBusqueda.values()) {
                        System.out.println(op.ordinal() + ") " + op.name());
                    }
                    System.out.println("¿Por cual opcion va a consultar?");
                    opcion = entrada.nextInt();
                    switch (opcionBusqueda.values()[opcion]) {
                        case ID -> {
                            opx = opcionBusqueda.values()[opcion];
                            ciclo = true;
                        }
                        case ISBN -> {
                            opx = opcionBusqueda.values()[opcion];
                            ciclo = true;
                        }
                        default -> System.out.println("Opcion invalido");
                    }
                }
            } catch (Exception ex) {
                System.out.println("El dato ingresado es invalido");
            }
            return opx;
        }

        private Book getFormBook() {
            var mod = WhereFindBook();

            
            switch (mod) {
                case ID -> {
                    showAllBooks();
                    System.out.println("Ingresa el ID del libro: ");
                    String id = entrada.next();
                    return service.getBookService().getBookById(id);
                    

                }
                case ISBN -> {
                    showAllBooks();
                    System.out.println("Ingresa el ISBN del libro: ");
                    String isbn = entrada.next();
                    return service.getBookService().getBookByIsbn(isbn);

                }
            }
            throw new BookNotFoundException("El libro no se encuentra registrado");
        
        }

        private enum opcionBusqueda {
            ID, ISBN
        }
    }

    protected class UserForms {
        public void iniciar() {
            boolean estado = false;
            while(!estado){
                try{
                System.out.println("|--- MENU USUARIOS ---|");
                MenuOption.showMenu(MenuOption.USER_MENU);
                opcion = entrada.nextInt();
                switch (MenuOption.USER_MENU[opcion]) {
                    case AGREGAR_USUARIO->{
                        var name = entrada.next();
                    }
                    case ELIMINAR_USUARIO -> {}
                    case CONSULTAR_USUARIO -> {}
                    default -> System.out.println("Dato invalido");
                }
            }catch(Exception ex){
                
            }
            }
            
        }
    }

    protected class LoanForms {
        public void iniciar() {
            System.out.println("|--- MENU PRESTAMOS ---|");
            MenuOption.showMenu(MenuOption.LOAN_MENU);
        }
    }

    private enum MenuOption {
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
        private static final MenuOption[] MAIN_MENU = { SALIDA, LIBROS, USUARIOS, PRESTAMOS };
        private static final MenuOption[] BOOK_MENU = { VOLVER_LIBROS, AGREGAR_LIBRO, ELIMINAR_LIBRO, CONSULTAR_LIBRO };
        private static final MenuOption[] USER_MENU = { VOLVER_USUARIOS, AGREGAR_USUARIO, ELIMINAR_USUARIO, CONSULTAR_USUARIO };
        private static final MenuOption[] LOAN_MENU = { VOLVER_PRESTAMOS, AGREGAR_PRESTAMO, DEVOLVER_LIBRO,
                MOSTRAR_PRESTAMOS, CAMBIAR_LIMITE_PRESTAMO };

        MenuOption(String displayName) {
            this.displayName = displayName;
        }

        public static void showMenu(MenuOption[] menuOptions) {
            for (int i = 0; i < menuOptions.length; i++) {
                System.out.println(i + ") " + menuOptions[i].displayName);
            }
        }
    }
}
