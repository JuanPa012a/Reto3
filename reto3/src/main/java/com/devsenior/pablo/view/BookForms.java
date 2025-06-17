package com.devsenior.pablo.view;

import static com.devsenior.pablo.Utils.IdGenerator.generateBookId;
import com.devsenior.pablo.exception.BookNotFoundException;
import com.devsenior.pablo.model.Book;
import static com.devsenior.pablo.view.MenuOption.selectOption;


public class BookForms extends Menu{
    private Integer opcion;

    public void iniciar() {
        boolean estado = false;
        while (!estado) {
            try {
                System.out.println("|--- MENU LIBROS ---|");
                MenuOption.showMenu(MenuOption.BOOK_MENU);
                opcion = entrada.nextInt();
                var menuselected = selectOption(MenuOption.BOOK_MENU, opcion);
                switch (menuselected) {
                    case AGREGAR_LIBRO -> {
                        System.out.println("=== AGREGAR LIBRO ===");
                        var id = generateBookId();

                        System.out.println("Ingresa el ISBN del libro: ");
                        var isbn = entrada.next();

                        System.out.println("Ingresa el titulo del libro:");
                        var title = entrada.next();

                        System.out.println("Ingresa el nombre del autor: ");
                        var autor = entrada.next();

                        var book = new Book(id , isbn, title, autor);
                        service.getBookService().addBook(book);
                        System.out.println("Guardado con éxito!");
                    }
                    case ELIMINAR_LIBRO -> {
                        System.out.println("=== ELIMINAR LIBRO ===");

                        var book = getFormBook();
                        service.getBookService().deleteBook(book);
                        System.out.println("Eliminado con éxito!");

                    }
                    case CONSULTAR_LIBRO -> {
                        System.out.println("=== CONSULTAR LIBRO ===");
                        var book = getFormBook();
                        service.getAllLoansFromBook(book.getId()).stream()
                                .forEach(l -> System.out.println(l));
                    }

                    case VOLVER_LIBROS -> {
                       
                        estado = true;
                    }
                    default -> {
                        
                    }
                }
            }catch (Exception ex) {
                System.out.println("Error: "+ex.getMessage());
                entrada.cleanBuffer();
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
            System.out.println("Error: "+ex.getMessage());
        }
        return opx;
    }

    public Book getFormBook() {
        service.getBookService().isEmpty();
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
