package com.devsenior.pablo.view;

import static com.devsenior.pablo.Utils.IdGenerator.generateUserId;
import com.devsenior.pablo.model.User;

public class UserForms extends Menu {
    private Integer opcion;

    public void iniciar() {
        boolean estado = false;
        while (!estado) {
            try {
                System.out.println("|--- MENU USUARIOS ---|");
                MenuOption.showMenu(MenuOption.USER_MENU);
                opcion = entrada.nextInt();
                var menuselected = MenuOption.selectOption(MenuOption.USER_MENU, opcion);
                switch (menuselected) {
                    case AGREGAR_USUARIO -> {
                        System.out.println("=== AGREGAR USUARIO ===");
                        var id = generateUserId();
                        System.out.println("Ingresa tu nombre: ");
                        var name = entrada.next();
                        var user = new User(id, name);
                        service.getUserService().addUser(user);
                        System.out.println("Se ha guardado con éxito!");
                    }
                    case ELIMINAR_USUARIO -> {
                        System.out.println("=== ELIMINAR USUARIO ===");
                        var user = getUserForm();
                        service.getUserService().deleteUser(user);
                        System.out.println("Lo has eliminado con éxito!");
                    }
                    case CONSULTAR_USUARIO -> {
                        System.out.println("=== CONSULTAR USUARIO ===");
                        showUsers();
                        System.out.println("Ingresa el ID: ");
                        var id = entrada.next();
                        service.getAllLoansFromUser(id).stream()
                                .forEach(System.out::println);
                    }
                    case VOLVER_USUARIOS -> estado = true;
                    default -> throw new Exception("Opcion no valida: " + menuselected);

                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
                entrada.cleanBuffer();
            }
        }

    }

    public User getUserForm() {
        service.getUserService().isEmpty();
        showUsers();
        System.out.println("Ingresa el ID del usuario: ");
        var id = entrada.next();
        return service.getUserService().getUserbyId(id);
    }

    private void showUsers() {
        service.getUserService().getUsers().stream()
                .forEach(System.out::println);
    }
}
