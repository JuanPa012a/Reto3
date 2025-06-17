package com.devsenior.pablo.view;

public class LoanForms extends Menu {
    
    public void iniciar(BookForms formBook, UserForms formUser) {
        Integer opcion;
        boolean estado = false;
        while (!estado)
            try {
                System.out.println("|--- MENU PRESTAMOS ---|");
                MenuOption.showMenu(MenuOption.LOAN_MENU);
                opcion = entrada.nextInt();
                var menuselected = MenuOption.selectOption(MenuOption.LOAN_MENU, opcion);
                switch (menuselected) {
                    case AGREGAR_PRESTAMO -> {
                        System.out.println("=== AGREGAR PRESTAMO ===");
                        var book = formBook.getFormBook();
                        var user = formUser.getUserForm();
                        service.addLoan(user, book);
                        System.out.println("Prestamo agregado con éxito!");
                    }
                    case DEVOLVER_LIBRO -> {
                        System.out.println("=== DEVOLVER PRESTAMO ===");
                        var book = formBook.getFormBook();
                        var user = formUser.getUserForm();
                        service.returnLoan(user.getId(), book.getId());
                        System.out.println("Se ha devuelto el libro con éxito");
                    }
                    case MOSTRAR_PRESTAMOS -> {
                        System.out.println("=== LISTA DE PRESTAMOS ===");
                        service.getLoans().stream().forEach(System.out::println);
                    }
                    case CAMBIAR_LIMITE_PRESTAMO ->{
                        System.out.println("=== N° MAXIMO DE PRESTAMOS ===");
                        System.out.println("Antes: "+service.getMaxLoans());
                        System.out.println("Ingresa la cantidad actual: ");
                        var cantidad = entrada.nextLong();
                        service.setMaxLoans(cantidad);
                        System.out.println("Modificado con éxito!");
                    }
                    case VOLVER_PRESTAMOS -> estado = true;
                    default -> throw new Exception("Opcion no valida: " + menuselected);
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                entrada.cleanBuffer();
            }
    }
}
