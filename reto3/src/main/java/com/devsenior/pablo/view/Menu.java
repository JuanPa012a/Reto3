package com.devsenior.pablo.view;

import com.devsenior.pablo.Utils.entradaScanner;
import com.devsenior.pablo.service.BookService;
import com.devsenior.pablo.service.LoanSerivce;
import com.devsenior.pablo.service.UserService;
import static com.devsenior.pablo.view.MenuOption.MAIN_MENU;

public class Menu {
    public  static final LoanSerivce service = new LoanSerivce(new BookService(), new UserService());
    public  final entradaScanner entrada = new entradaScanner(System.in);
    private Boolean estado;
    private Integer opcion;
   

    public Menu() {
        estado = true;
    }

    public void start() {
      extracted();
      System.out.println("Muchas gracias por usar el programa!\nHasta luego!");
      entrada.close();
      System.exit(0);
    }
    

    private GeneralForms extracted() {
        return new GeneralForms();
        
    }

    protected class GeneralForms {
        protected  final BookForms formBook = new BookForms();
        protected final UserForms formUser = new UserForms();
        private final LoanForms formLoan = new LoanForms();

        public GeneralForms() {
            while (estado) {
                System.out.println("|--- MENU PRINCIPAL ---|");
                MenuOption.showMenu(MenuOption.MAIN_MENU);
                System.out.println("Selecciona la opcion que deseas: ");
                try {
                    opcion = entrada.nextInt();
                    var menuselected = MenuOption.selectOption(MAIN_MENU, opcion);
                    switch (menuselected) {
                        case LIBROS -> formBook.iniciar();
                        case USUARIOS -> formUser.iniciar();
                        case PRESTAMOS -> formLoan.iniciar(formBook, formUser);
                        case SALIDA -> estado = false;
                        default -> System.out.println("Opcion no valida");
                    }
                } catch (Exception ex) {
                    System.out.println("Error: "+ex.getMessage());
                    entrada.cleanBuffer();
                }
            }
        }
    }

   
}
