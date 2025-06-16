# CORDIAL SALUDO COMUNIDAD DEV SENIOR
Aqui se realiza la entrega del reto 3 ! 
Acontinuacion se explicara un poco del proceso y sobre las funcionalidades

## JERARQUIA Y CLASES
Este proyecto se encuentra con modelos, excepciones, servicio y el testing
### Modelos:
* Book:
  Modelo de un libro en la vida real
* Loan: 
  Modelo de los prestamos (obtiene un libro y un usuario)
* User: 
  Modelo que maneja a una persona en la vida real
* ### Enums:
* LoanState: 
  Este es el encargado de manejar el estado de los prestamos
### Excepciones:
* BookAlreadyExistException : Manejo de error cuando un libro ya existe
* BookLentYetException : Manejo de error cuando ya un libro tiene un prestamo
* BookNotFoundException : Manejo de error cuando un libro no es encontrado
* LoanNotFoundException : Manejo de error cuando no hay prestamos
* MaxLoansException : Manejo de error cuando el usuario llega al máximo de prestamos disponibles
* UserNotFoundException : Manejo de error cuando el usuario no existe
### Servicios
* BookService : Gestionamiento del modelo Book 
* UserService : Gestionamiento del modelo User
* LoanService : Gestionamiento del modelo Loan
### Test
* BookService : Se realizan pruebas para agregar, eliminar y obtener el modelo Book por medio de su ID e ISBN donde se garantiza la funcionalidad y resoluciones de errores
* UserService : Se realizan pruebas para agregar, eliminar y obtener el modelo User por medio de su ID donde se garantiza la funcionalidad y resoluciones de errores
* LoanService : Se realizan pruebas para agregar, devolver y validar situaciones hipoteticamente probables cuando la aplicacion este en funcionamiento en tiempo real,
validando todo tipo de probabilidades, cuando ya un usuario realizo muchos prestamos, cuando un libro ya fue prestado
# METODOS Y FUNCIONES
Se cuentan con metodos donde podemos validar si al realizar un prestamo, se realizan consultas al usuario si tiene la validacion de aprobar su prestamo junto con el libro si tambien 
cuenta con su disponibilidad de uso y disposicion, tambien se pueden obtener los prestamos que un usuario ha llegado a realizar durante el ciclo de vida de la aplicacion 
