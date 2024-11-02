# GestionMagia

## Enlace del repositorio 

[Repositorio](https://github.com/ConcurrenteCasoPracticoTema2/demo.git)

Proyecto realizado por Jose Daniel Martín, Hugo Sanchez, Fernando Santamaría y Jose Antonio Oyono

## Introducción

Este proyecto de Java implementa una aplicación de gestión de magia utilizando Spring Boot y Maven. La aplicación permite gestionar usuarios, lanzar hechizos y almacenar información en una base de datos MySQL. Además, se implementan aspectos para validar datos y controlar privilegios.

## Estructura de Clases

### 1. Almacenamiento

`Almacenamiento` es una entidad que representa el almacenamiento de información en la base de datos. Esta clase contiene atributos como `id`, `usuario`, `metodo` y `timestamp`, que se utilizan para identificar y registrar la información almacenada.

```java
@Entity
@Table(name = "almacenamiento")
public class Almacenamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private int metodo;
    private LocalDateTime timestamp;

    // Getters and Setters
}
```

#### Métodos

- `getId` y `setId`: Obtiene y establece el ID del almacenamiento.
- `getUsuario` y `setUsuario`: Obtiene y establece el usuario asociado.
- `getMetodo` y `setMetodo`: Obtiene y establece el método de almacenamiento.
- `getTimestamp` y `setTimestamp`: Obtiene y establece la marca de tiempo del almacenamiento.

### 2. AlmacenamientoController

`AlmacenamientoController` es un controlador REST que maneja las operaciones CRUD para la entidad `Almacenamiento`. Utiliza `AlmacenamientoRepository` para interactuar con la base de datos y proporciona endpoints para obtener, crear y eliminar almacenamientos.

```java
@RestController
@RequestMapping("/almacenamientos")
public class AlmacenamientoController {
    @Autowired
    private AlmacenamientoRepository almacenamientoRepository;

    @GetMapping
    public List<Almacenamiento> getAllAlmacenamientos() {
        return almacenamientoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Almacenamiento getAlmacenamientoById(@PathVariable Long id) {
        return almacenamientoRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Almacenamiento createAlmacenamiento(@RequestBody Almacenamiento almacenamiento) {
        return almacenamientoRepository.save(almacenamiento);
    }

    @DeleteMapping("/{id}")
    public void deleteAlmacenamiento(@PathVariable Long id) {
        almacenamientoRepository.deleteById(id);
    }
}
```

#### Métodos

- `getAllAlmacenamientos`: Obtiene todos los almacenamientos.
- `getAlmacenamientoById`: Obtiene un almacenamiento por su ID.
- `createAlmacenamiento`: Crea un nuevo almacenamiento.
- `deleteAlmacenamiento`: Elimina un almacenamiento por su ID.

### 3. AlmacenamientoRepository

`AlmacenamientoRepository` es un repositorio JPA que maneja las operaciones de persistencia para la entidad `Almacenamiento`. Extiende `JpaRepository`, lo que proporciona métodos CRUD básicos sin necesidad de implementación adicional.

```java
public interface AlmacenamientoRepository extends JpaRepository<Almacenamiento, Long> {
}
```

### 4. AlmacenamientoService

`AlmacenamientoService` es un servicio que proporciona métodos para interactuar con el repositorio de `Almacenamiento`. Este servicio actúa como una capa intermedia entre el controlador y el repositorio, encapsulando la lógica de negocio.

```java
@Service
public class AlmacenamientoService {
    @Autowired
    private AlmacenamientoRepository almacenamientoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Almacenamiento> findAll() {
        return almacenamientoRepository.findAll();
    }

    public Almacenamiento save(Almacenamiento almacenamiento) {
        return almacenamientoRepository.save(almacenamiento);
    }

    public void deleteById(Long id) {
        almacenamientoRepository.deleteById(id);
    }

    public Almacenamiento findById(Long id) {
        return almacenamientoRepository.findById(id).orElse(null);
    }
}
```

#### Métodos

- `findAll`: Obtiene todos los almacenamientos.
- `save`: Guarda un almacenamiento.
- `deleteById`: Elimina un almacenamiento por su ID.
- `findById`: Obtiene un almacenamiento por su ID.

### 5. Aspecto

`Aspecto` es una clase que implementa aspectos para validar datos y controlar privilegios utilizando AOP (Aspect-Oriented Programming). Los aspectos permiten ejecutar código adicional antes o después de ciertos métodos, sin modificar el código original.

```java
@Aspect
@Component
public class Aspecto {
    @Autowired
    private AlmacenamientoRepository almacenamientoRepository;

    @Pointcut("execution(* org.example.gestionmagia.Hechizos.Hechizo.lanzar*(..))")
    public void lanzarMethods() {
    }

    @Pointcut("execution(* org.example.gestionmagia.Usuario.UsuarioService.save(..)) && args(usuario)")
    public void saveUsuarioMethod(Usuario usuario) {
    }

    @Before("saveUsuarioMethod(usuario)")
    public void validateEmail(Usuario usuario) {
        String email = usuario.getCorreo();
        if (!(email.endsWith("@gmail.com") || email.endsWith("@yahoo.es") || email.endsWith("@outlook.es"))) {
            throw new InvalidEmailException("Correo electrónico no válido: " + email + ". Debe ser un correo de gmail.com, yahoo.es o outlook.es");
        }
    }

    @Before("saveUsuarioMethod(usuario)")
    public void validatePassword(Usuario usuario) {
        String contraseña = usuario.getContraseña();
        if (contraseña == null || contraseña.trim().isEmpty()) {
            throw new InvalidPasswordException("La contraseña no puede estar vacía.");
        }
    }

    @Before("saveUsuarioMethod(usuario)")
    public void validateName(Usuario usuario) {
        String nombre = usuario.getNombre();
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new InvalidNameException("El nombre de usuario no puede estar vacío.");
        }
    }

    @Before("lanzarMethods() && args(usuario,..)")
    public void privilegiosLanzar(JoinPoint joinPoint, Usuario usuario) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        if (!usuario.isAdministrador()) {
            if (methodName.equals("lanzarHechizo4")) {
                throw new PrivilegeException("Acceso denegado: El usuario " + usuario.getNombre() + " no tiene privilegios para lanzar " + methodName);
            }
        }
    }

    @After("lanzarMethods() && args(usuario,..)")
    public void afterLanzarMethods(JoinPoint joinPoint, Usuario usuario) {
        String methodName = joinPoint.getSignature().getName();
        LocalDateTime currentTime = LocalDateTime.now();
        System.out.println("El método " + methodName + " ha sido invocado a las " + currentTime + " por el usuario " + usuario.getNombre());
    }

    public void captureThreadInfo(String threadName, Usuario usuario, int metodo) {
        Almacenamiento almacenamiento = new Almacenamiento();
        almacenamiento.setUsuario(usuario);
        almacenamiento.setMetodo(metodo);
        almacenamiento.setTimestamp(LocalDateTime.now());
        almacenamientoRepository.save(almacenamiento);
    }
}
```

#### Métodos

- `validateEmail`: Valida el correo electrónico del usuario.
- `validatePassword`: Valida la contraseña del usuario.
- `validateName`: Valida el nombre del usuario.
- `privilegiosLanzar`: Controla los privilegios para lanzar hechizos.
- `afterLanzarMethods`: Registra la invocación de métodos de lanzamiento de hechizos.
- `captureThreadInfo`: Captura información del hilo y la guarda en la base de datos.

### 6. Usuario

`Usuario` es una entidad que representa un usuario en la base de datos. Esta clase contiene atributos como `id`, `nombre`, `contraseña`, `correo` y `administrador`, que se utilizan para identificar y gestionar a los usuarios.

```java
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String contraseña;
    private String correo;
    private boolean administrador;

    // Getters and Setters
}
```

#### Métodos

- `getId` y `setId`: Obtiene y establece el ID del usuario.
- `getNombre` y `setNombre`: Obtiene y establece el nombre del usuario.
- `getContraseña` y `setContraseña`: Obtiene y establece la contraseña del usuario.
- `getCorreo` y `setCorreo`: Obtiene y establece el correo del usuario.
- `isAdministrador` y `setAdministrador`: Obtiene y establece si el usuario es administrador.

### 7. UsuarioController

`UsuarioController` es un controlador REST que maneja las operaciones CRUD para la entidad `Usuario`. Utiliza `UsuarioService` para interactuar con la base de datos y proporciona endpoints para obtener, crear y eliminar usuarios.

```java
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
    }
}
```

#### Métodos

- `getAllUsuarios`: Obtiene todos los usuarios.
- `getUsuarioById`: Obtiene un usuario por su ID.
- `createUsuario`: Crea un nuevo usuario.
- `deleteUsuario`: Elimina un usuario por su ID.

### 8. UsuarioRepository

`UsuarioRepository` es un repositorio JPA que maneja las operaciones de persistencia para la entidad `Usuario`. Extiende `JpaRepository`, lo que proporciona métodos CRUD básicos sin necesidad de implementación adicional.

```java
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByNombre(String nombre);
}
```

### 9. UsuarioService

`UsuarioService` es un servicio que proporciona métodos para interactuar con el repositorio de `Usuario`. Este servicio actúa como una capa intermedia entre el controlador y el repositorio, encapsulando la lógica de negocio.

```java
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario findByNombre(String nombre) {
        List<Usuario> usuarios = usuarioRepository.findByNombre(nombre);
        if (usuarios.isEmpty()) {
            return null;
        }
        return usuarios.get(0);
    }
}
```

#### Métodos

- `findAll`: Obtiene todos los usuarios.
- `save`: Guarda un usuario.
- `deleteById`: Elimina un usuario por su ID.
- `findById`: Obtiene un usuario por su ID.
- `findByNombre`: Obtiene un usuario por su nombre.

### 10. Hechizo

`Hechizo` es una clase que representa la lógica para lanzar hechizos utilizando `ExecutorService`. Esta clase contiene métodos para lanzar diferentes hechizos y registrar la información del hilo en la base de datos.

```java
@Component
public class Hechizo {
    private static final Logger logger = LoggerFactory.getLogger(Hechizo.class);

    @Autowired
    @Qualifier("singleThreadExecutor1")
    private ExecutorService singleThreadExecutor1;

    @Autowired
    @Qualifier("singleThreadExecutor2")
    private ExecutorService singleThreadExecutor2;

    @Autowired
    @Qualifier("singleThreadExecutor3")
    private ExecutorService singleThreadExecutor3;

    @Autowired
    @Qualifier("singleThreadExecutor4")
    private ExecutorService singleThreadExecutor4;

    @Autowired
    @Qualifier("fixedThreadPool3")
    private ExecutorService fixedThreadPool3;

    @Autowired
    private Aspecto aspecto;

    public void lanzarHechizo1(Usuario usuario) {
        singleThreadExecutor1.submit(() -> {
            String threadName = Thread.currentThread().getName();
            logger.info("Ejecutando lanzarHechizo1 en el hilo: {} de la pool: singleThreadExecutor1", threadName);
            aspecto.captureThreadInfo("singleThreadExecutor1", usuario, 1);
        });
    }

    public void lanzarHechizo2(Usuario usuario) {
        singleThreadExecutor2.submit(() -> {
            String threadName = Thread.currentThread().getName();
            logger.info("Ejecutando lanzarHechizo2 en el hilo: {} de la pool: singleThreadExecutor2", threadName);
            aspecto.captureThreadInfo("singleThreadExecutor2", usuario, 2);
        });
    }

    public void lanzarHechizo3(Usuario usuario) {
        singleThreadExecutor3.submit(() -> {
            String threadName = Thread.currentThread().getName();
            logger.info("Ejecutando lanzarHechizo3 en el hilo: {} de la pool: singleThreadExecutor3", threadName);
            aspecto.captureThreadInfo("singleThreadExecutor3", usuario, 3);
        });
    }

    public void lanzarHechizo4(Usuario usuario) {
        singleThreadExecutor4.submit(() -> {
            String threadName = Thread.currentThread().getName();
            logger.info("Ejecutando lanzarHechizo4 en el hilo: {} de la pool: singleThreadExecutor4", threadName);
            aspecto.captureThreadInfo("singleThreadExecutor4", usuario, 4);
        });
    }

    public void lanzarHechizoMultiple(Usuario usuario) {
        aspecto.captureThreadInfo("fixedThreadPool3", usuario, 5);
        fixedThreadPool3.submit(() -> {
            try {
                Thread.sleep(1000);
                String threadName = Thread.currentThread().getName();
                logger.info("Ejecutando lanzarHechizo1 en el hilo: {} de la pool: fixedThreadPool3", threadName);
                aspecto.captureThreadInfo("fixedThreadPool3", usuario, 1);
                lanzarHechizo1(usuario);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });
        fixedThreadPool3.submit(() -> {
            try {
                Thread.sleep(1000);
                String threadName = Thread.currentThread().getName();
                logger.info("Ejecutando lanzarHechizo2 en el hilo: {} de la pool: fixedThreadPool3", threadName);
                aspecto.captureThreadInfo("fixedThreadPool3", usuario, 2);
                lanzarHechizo2(usuario);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });
        fixedThreadPool3.submit(() -> {
            try {
                Thread.sleep(1000);
                String threadName = Thread.currentThread().getName();
                logger.info("Ejecutando lanzarHechizo3 en el hilo: {} de la pool: fixedThreadPool3", threadName);
                aspecto.captureThreadInfo("fixedThreadPool3", usuario, 3);
                lanzarHechizo3(usuario);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });
    }

    public void cerrarExecutor() {
        singleThreadExecutor1.shutdown();
        singleThreadExecutor2.shutdown();
        singleThreadExecutor3.shutdown();
        singleThreadExecutor4.shutdown();
    }
}
```

#### Métodos

- `lanzarHechizo1`, `lanzarHechizo2`, `lanzarHechizo3`, `lanzarHechizo4`: Lanza hechizos en diferentes `ExecutorService`.
- `lanzarHechizoMultiple`: Lanza múltiples hechizos utilizando un `ExecutorService` fijo.
- `cerrarExecutor`: Cierra los `ExecutorService`.

### 11. ExecutorServiceConfig

`ExecutorServiceConfig` es una clase de configuración que define varios `ExecutorService` utilizados en la aplicación. Estos servicios de ejecución permiten la ejecución de tareas en hilos separados, mejorando la concurrencia y el rendimiento de la aplicación.

```java
@Configuration
public class ExecutorServiceConfig {
    @Bean(name = "fixedThreadPool")
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(8);
    }

    @Bean(name = "fixedThreadPool2")
    public ExecutorService fixedThreadPool2() {
        return Executors.newFixedThreadPool(8);
    }

    @Bean(name = "singleThreadExecutor1")
    public ExecutorService singleThreadExecutor1() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean(name = "singleThreadExecutor2")
    public ExecutorService singleThreadExecutor2() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean(name = "singleThreadExecutor3")
    public ExecutorService singleThreadExecutor3() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean(name = "singleThreadExecutor4")
    public ExecutorService singleThreadExecutor4() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean(name = "customThreadPool")
    public ExecutorService customThreadPool() {
        return Executors.newCachedThreadPool();
    }

    @Bean(name = "fixedThreadPool3")
    public ExecutorService fixedThreadPool3() {
        return Executors.newFixedThreadPool(2);
    }

    @Bean(name = "fixedThreadPool4")
    public ExecutorService fixedThreadPool4() {
        return Executors.newFixedThreadPool(8);
    }
}
```

### 12. LoginMenu

`LoginMenu` es una clase que maneja la interfaz de usuario, contraseña o correo incorrectos. Inténtelo de nuevo.");
} else {
System.out.println("Bienvenido, " + loggedInUser.getNombre() + "!");
return loggedInUser;
}
} catch (InvalidEmailException e) {
System.out.println(e.getMessage());
System.out.println("Por favor, ingrese credenciales válidas.");
}

            System.out.println("1. Intentar de nuevo");
            System.out.println("2. Volver al menú anterior");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 2) {
                return null;
            }
        }

        return loggedInUser;
    }
}
```

#### Métodos

- `displayLoginMenu`: Muestra el menú de inicio de sesión y maneja la lógica de autenticación.

### 13. LoginService

`LoginService` es un servicio que maneja la lógica de autenticación de usuarios. Este servicio verifica las credenciales del usuario y devuelve el usuario autenticado si las credenciales son correctas.

```java
@Service
public class LoginService {
    @Autowired
    private UsuarioService usuarioService;

    public Usuario login(String nombre, String contraseña, String correo) {
        return usuarioService.findAll().stream()
                .filter(usuario -> usuario.getNombre().equals(nombre) &&
                        usuario.getContraseña().equals(contraseña) &&
                        usuario.getCorreo().equals(correo))
                .findFirst()
                .orElse(null);
    }
}
```

#### Métodos

- `login`: Autentica un usuario basado en su nombre, contraseña y correo.

### 14. MainMenu

`MainMenu` es una clase que maneja la interfaz de usuario para el menú principal. Proporciona opciones para iniciar sesión, registrarse o salir de la aplicación.

```java
@Component
public class MainMenu {
    @Autowired
    private LoginMenu loginMenu;
    @Autowired
    private Borrado borrado;
    @Autowired
    private RegisterMenu registerMenu;
    @Autowired
    private Menu menu;
    @Autowired
    private DataInitializer dataInitializer;

    public void displayMainMenu() {
        try {
            dataInitializer.initializeData();
        } catch (Exception e) {
            System.out.println("Error initializing data: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Elige una opción:");
            System.out.println("1. Logearte");
            System.out.println("2. Registrarte");
            System.out.println("3. Salir");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        Usuario usuario = loginMenu.displayLoginMenu();
                        if (usuario != null) {
                            menu.displayMenu(usuario);
                        }
                        break;
                    case 2:
                        registerMenu.displayRegisterMenu();
                        break;
                    case 3:
                        borrado.truncateUsuarioTable();
                        borrado.truncateAlmacenamientoTable();
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
    }
}
```

#### Métodos

- `displayMainMenu`: Muestra el menú principal y maneja la lógica de navegación.

### 15. Menu

`Menu` es una clase que maneja la interfaz de usuario para el menú de hechizos. Proporciona opciones para lanzar diferentes hechizos y registrar la información del hilo en la base de datos.

```java
@Component
public class Menu {
    @Autowired
    private Hechizo hechizo;
    @Autowired
    private Borrado borrado;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private Aspecto aspecto;
    @Autowired
    private MainMenu mainMenu;

    public void displayMenu(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Elige un hechizo para lanzar:");
            System.out.println("1. Hechizo 1");
            System.out.println("2. Hechizo 2");
            System.out.println("3. Hechizo 3");
            System.out.println("4. Hechizo 4");
            System.out.println("5. Ataque multiple");
            System.out.println("6. Volver al menú principal");
            System.out.println("7. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcion) {
                    case 1:
                        hechizo.lanzarHechizo1(usuario);
                        break;
                    case 2:
                        hechizo.lanzarHechizo2(usuario);
                        break;
                    case 3:
                        hechizo.lanzarHechizo3(usuario);
                        break;
                    case 4:
                        hechizo.lanzarHechizo4(usuario);
                        break;
                    case 5:
                        System.out.println("Ataque multiple");
                        hechizo.lanzarHechizoMultiple(usuario);
                        break;
                    case 6:
                        mainMenu.displayMainMenu();
                        return;
                    case 7:
                        System.out.println("Saliendo...");
                        hechizo.cerrarExecutor();
                        borrado.truncateUsuarioTable();
                        borrado.truncateAlmacenamientoTable();
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            } catch (PrivilegeException e) {
                System.out.println(e.getMessage());
            } catch (InvalidEmailException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
```

#### Métodos

- `displayMenu`: Muestra el menú de hechizos y maneja la lógica de lanzamiento de hechizos.

### 16. RegisterMenu

`RegisterMenu` es una clase que maneja la interfaz de usuario para el registro de nuevos usuarios. Proporciona un menú interactivo para que los usuarios ingresen sus datos y se registren en la aplicación.

```java
@Component
public class RegisterMenu {
    @Autowired
    private UsuarioService usuarioService;

    public void displayRegisterMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Elige una opción:");
            System.out.println("1. Crear usuario");
            System.out.println("2. Volver al menú anterior");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    try {
                        System.out.print("Ingrese el nombre de usuario: ");
                        String nombre = scanner.nextLine();
                        System.out.println();

                        System.out.print("Ingrese la contraseña: ");
                        String contraseña = scanner.nextLine();
                        System.out.println();

                        System.out.print("Ingrese el correo: ");
                        String correo = scanner.nextLine();
                        System.out.println();

                        Usuario nuevoUsuario = new Usuario();
                        nuevoUsuario.setNombre(nombre);
                        nuevoUsuario.setContraseña(contraseña);
                        nuevoUsuario.setCorreo(correo);
                        nuevoUsuario.setAdministrador(false);

                        usuarioService.save(nuevoUsuario);
                        System.out.println("Usuario creado exitosamente.");
                    } catch (InvalidEmailException | InvalidNameException | InvalidPasswordException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }
}
```

#### Métodos

- `displayRegisterMenu`: Muestra el menú de registro y maneja la lógica de creación de usuarios.

### 17. Borrado

`Borrado` es una clase que maneja la lógica para truncar las tablas de la base de datos. Proporciona métodos para vaciar las tablas `usuario` y `almacenamiento`.

```java
@Component
public class Borrado {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void truncateUsuarioTable() {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE usuario").executeUpdate();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }

    @Transactional
    public void truncateAlmacenamientoTable() {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE almacenamiento").executeUpdate();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }
}
```

#### Métodos

- `truncateUsuarioTable`: Trunca la tabla de usuarios.
- `truncateAlmacenamientoTable`: Trunca la tabla de almacenamiento.

### 18. DataInitializer

`DataInitializer` es una clase que inicializa datos en la base de datos. Proporciona un método para crear usuarios predeterminados si no existen.

```java
@Component
public class DataInitializer {
    @Autowired
    private UsuarioService usuarioService;

    public void initializeData() {
        if (usuarioService.findByNombre("admin") == null) {
            Usuario admin = new Usuario();
            admin.setNombre("admin");
            admin.setContraseña("admin");
            admin.setCorreo("admin@gmail.com");
            admin.setAdministrador(true);
            usuarioService.save(admin);
        }

        if (usuarioService.findByNombre("usuario") == null) {
            Usuario usuario = new Usuario();
            usuario.setNombre("usuario");
            usuario.setContraseña("usuario");
            usuario.setCorreo("usuario@gmail.com");
            usuario.setAdministrador(false);
            usuarioService.save(usuario);
        }
    }
}
```

#### Métodos

- `initializeData`: Inicializa datos en la base de datos si no existen.

### 19. GestionMagiaApplication

`GestionMagiaApplication` es la clase principal que inicia la aplicación Spring Boot. Implementa `CommandLineRunner` para ejecutar el menú principal al iniciar la aplicación.

```java
@SpringBootApplication
public class GestionMagiaApplication implements CommandLineRunner {
    @Autowired
    private MainMenu mainMenu;

    public static void main(String[] args) {
        SpringApplication.run(GestionMagiaApplication.class, args);
    }

    @Override
    public void run(String... args) {
        mainMenu.displayMainMenu();
    }
}
```

### 20. Configuración de Docker y Maven

El proyecto utiliza Docker para la base de datos MySQL y Maven para la gestión de dependencias y plugins.

#### `docker-compose.yml`

```yaml
services:
  mysql:
    image: mysql:9.0
    environment:
      - MYSQL_DATABASE=my-app
      - MYSQL_ROOT_PASSWORD=P4ssword!
    ports:
      - "3310:3306"
```

#### `pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>gestionmagia</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <name>gestionmagia</name>
    <description>Proyecto de gestión de magia</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.5.4</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <java.version>11</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.26</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.32</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.6</version>
        </dependency>
        <!-- Otras dependencias -->
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>io.fabric8</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>0.34.1</version>
                <executions>
                    <execution>
                        <id>docker-compose-up</id>
                        <phase>pre-integration-test</phase>
                        <goals>
                            <goal>up</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>docker-compose-down</id>
                        <phase>post-integration-test</phase>
                        <goals>
                            <goal>down</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <dockerComposeFile>${project.basedir}/src/main/docker/docker-compose.yml</dockerComposeFile>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### 21. Configuración de la Base de Datos

La configuración de la base de datos se encuentra en los archivos `application.yml` y `application.properties`.

#### `src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3310/my-app
    username: root
    password: P4ssword!
    hikari:
      connection-timeout: 30000
      maximum-pool-size: 10
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        jdbc:
          lob:
            non_contextual_creation: true
        id:
          new_generator_mappings: true
  docker:
    compose:
      lifecycle-management: start-only
server:
  port: 8080
```

#### `src/main/resources/application.properties`

```ini
spring.datasource.url=jdbc:mysql://localhost:3310/my-app
spring.datasource.username=root
spring.datasource.password=P4ssword!
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

## Problemas potenciales con la implementación de aspectos y sus soluciones

1. **Problemas con la definición de Pointcut**:
    - **Problema**: Definir pointcuts incorrectamente puede llevar a que los aspectos no se apliquen a los métodos deseados.
    - **Solución**: Utilizamos expresiones de pointcut precisas para apuntar a métodos específicos. Por ejemplo, `@Pointcut("execution(* org.example.gestionmagia.Hechizos.Hechizo.lanzar*(..))")` apunta a todos los métodos que comienzan con `lanzar` en la clase `Hechizo`.

2. **Orden y ejecución de aspectos**:
    - **Problema**: Los aspectos pueden no ejecutarse en el orden deseado, especialmente cuando se aplican múltiples aspectos al mismo método.
    - **Solución**: Aseguramos el orden correcto definiendo pointcuts separados y utilizando las anotaciones `@Before` y `@After` adecuadamente. Por ejemplo, los aspectos de validación se ejecutan antes de guardar un usuario.

3. **Manejo de excepciones en aspectos**:
    - **Problema**: Las excepciones no manejadas en los aspectos pueden interrumpir el flujo normal de la aplicación.
    - **Solución**: Manejamos las excepciones dentro de los métodos de los aspectos y lanzamos excepciones personalizadas como `InvalidEmailException`, `InvalidPasswordException` y `PrivilegeException` para proporcionar mensajes de error significativos.

4. **Inyección de dependencias en aspectos**:
    - **Problema**: Inyectar dependencias en aspectos puede ser complicado, especialmente si el aspecto no es gestionado por Spring.
    - **Solución**: Utilizamos `@Component` para asegurar que el aspecto sea gestionado por Spring y `@Autowired` para inyectar dependencias como `AlmacenamientoRepository`.

5. **Sobrecarga de rendimiento**:
    - **Problema**: Los aspectos pueden introducir una sobrecarga de rendimiento si no se usan con moderación.
    - **Solución**: Limitamos el uso de aspectos a operaciones críticas como validación y comprobaciones de privilegios. Además, utilizamos mecanismos eficientes de registro y captura de datos para minimizar el impacto en el rendimiento.

## Conclusión

Este proyecto de gestión de magia en Java utiliza Spring Boot y Maven para proporcionar una aplicación robusta y escalable. La aplicación incluye funcionalidades para gestionar usuarios, lanzar hechizos y almacenar información en una base de datos MySQL, con validaciones y control de privilegios implementados mediante aspectos.
```