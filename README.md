# FinanceStudio Backend

API REST para la gestion financiera personal, construida con Spring Boot 4 y MongoDB.

## Stack Tecnologico

| Componente | Tecnologia | Version |
|------------|-----------|---------|
| Lenguaje | Java | 21 |
| Framework | Spring Boot | 4.0.6 |
| Base de datos | MongoDB Atlas | - |
| Seguridad | Spring Security + JWT | jjwt 0.12.6 |
| Build Tool | Maven | - |
| Arquitectura | Hexagonal / Modular | Spring Modulith 2.0.6 |
| Boilerplate | Lombok | - |
| API Docs | SpringDoc OpenAPI | 3.0.2 |

## Arquitectura

El proyecto sigue una arquitectura **Hexagonal (puertos y adaptadores)** organizada en modulos con **Spring Modulith**:

```
src/main/java/com/sebas/finance/
├── FinanceApplication.java              # Punto de entrada
├── SecurityConfig.java                  # Configuracion de seguridad
└── module/
    ├── auth/                            # Modulo de autenticacion
    │   ├── domain/
    │   │   ├── model/                   # AuthTokens, AuthCredentials
    │   │   ├── repository/              # AuthRepository (puerto)
    │   │   └── exception/               # InvalidCredentials, EmailAlreadyExists, InvalidToken
    │   ├── application/
    │   │   ├── service/                 # AuthService (interfaz + impl)
    │   │   └── usecase/                 # Register, Login, RefreshToken
    │   └── infrastructure/
    │       ├── adapter/                 # AuthAdapter (implementa AuthRepository)
    │       ├── controller/              # AuthController + DTOs
    │       └── security/                # JwtService, JwtFilter, PasswordService
    ├── user/                            # Modulo de usuarios
    │   ├── domain/
    │   │   ├── model/                   # User, UserId, UserEmail, UserPassword, UserFullName
    │   │   ├── repository/              # UserRepository (puerto)
    │   │   └── exception/               # UserExist, UserNotExits, UserIsNotValid
    │   ├── application/
    │   │   ├── service/                 # UserService (interfaz + impl)
    │   │   └── usecase/                 # Save, Update, Delete, FindById, FindAll
    │   └── infrastructure/
    │       ├── adapter/                 # UserAdapter, UserMongoRepository
    │       ├── controller/              # UserController + DTOs
    │       ├── mapper/                  # UserMapper
    │       └── persistence/             # UserDocument (MongoDB)
    ├── financial/                         # Modulo financiero
    │   ├── domain/
    │   │   ├── model/                   # Entry, EntryId, EntryMonth, EntryCategory, EntryLabel, EntryAmount
    │   │   ├── repository/              # FinancialEntryRepository (puerto)
    │   │   └── exception/               # EntryNotFound, InvalidMonth, FutureMonth, InvalidEntryData
    │   ├── application/
    │   │   ├── service/                 # FinancialEntryService (interfaz + impl)
    │   │   └── usecase/                 # SaveEntry, DeleteEntry, FindByMonth, FindByYear, GetSummary
    │   └── infrastructure/
    │       ├── adapter/                 # FinancialEntryAdapter, FinancialEntryMongoRepository
    │       ├── controller/              # FinancialEntryController + DTOs
    │       ├── mapper/                  # FinancialEntryMapper
    │       └── persistence/             # FinancialEntryDocument (MongoDB)
    └── shared/
        └── infrastructure/
            └── config/                  # UserBeanConfig, AuthBeanConfig, FinancialBeanConfig
```

### Capas

| Capa | Responsabilidad |
|------|----------------|
| **Domain** | Modelos de negocio, value objects, interfaces de repositorio (puertos), excepciones del dominio |
| **Application** | Casos de uso (un clase por operacion), servicios de orquestacion |
| **Infrastructure** | Controladores REST, adaptadores (implementacion de puertos), DTOs, persistencia, seguridad |

## Modulos

### Auth Module

Modulo de autenticacion y autorizacion basado en JWT.

**Funcionalidades:**
- Registro de usuarios con contrasenas encriptadas (BCrypt)
- Inicio de sesion con validacion de credenciales
- Generacion de access tokens (15 min) y refresh tokens (7 dias)
- Renovacion de tokens via refresh token
- Filtro JWT en la cadena de seguridad de Spring

**Seguridad:**
- Contrasenas hasheadas con BCrypt (10 rounds)
- JWT con HMAC-SHA using jjwt 0.12.6
- Sesiones STATELESS (sin estado en servidor)
- Filtro `JwtAuthenticationFilter` extrae el usuario del header `Authorization: Bearer <token>`

### User Module

Modulo de gestion de usuarios con operaciones CRUD completas.

**Funcionalidades:**
- Crear, actualizar, eliminar usuarios
- Consultar usuario por ID
- Listar todos los usuarios

### Financial Module

Modulo de gestion de entradas financieras (ingresos, gastos, ahorros) con aislamiento por usuario.

**Funcionalidades:**
- Crear, eliminar entradas financieras por mes
- Consultar entradas del mes o del anio completo
- Resumen financiero con totales, desglose por categoria y tasa de ahorro
- Aislamiento completo de datos por usuario (cada usuario solo ve sus propios registros)

**Categorias soportadas:** `income` (ingresos), `expense` (gastos), `savings` (ahorros)

**Coleccion MongoDB:** `financial_entries`
```json
{
  "_id": "uuid",
  "userId": "email@usuario.com",
  "month": "2024-05-01T00:00:00Z",
  "category": "income",
  "label": "Salario",
  "amount": 5000.00
}
```

**Validaciones:**
- El mes no puede ser futuro
- El concepto es obligatorio (max 60 caracteres)
- El monto debe ser mayor a 0
- La categoria debe ser `income`, `expense` o `savings`

## Endpoints

### Auth

| Metodo | Ruta | Descripcion | Body |
|--------|------|-------------|------|
| `POST` | `/api/v1/auth/register` | Registrar usuario | `{ "fullName", "email", "password" }` |
| `POST` | `/api/v1/auth/login` | Iniciar sesion | `{ "email", "password" }` |
| `POST` | `/api/v1/auth/refresh?refreshToken=` | Renovar tokens | Query param |

**Response exitoso (register/login):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Users

| Metodo | Ruta | Descripcion | Body |
|--------|------|-------------|------|
| `POST` | `/api/v1/users` | Crear usuario | `{ "userFullName", "userEmail", "userPassword" }` |
| `PUT` | `/api/v1/users/{id}` | Actualizar usuario | `{ "userFullName", "userEmail", "userPassword" }` |
| `DELETE` | `/api/v1/users/{id}` | Eliminar usuario | - |
| `GET` | `/api/v1/users` | Listar usuarios | - |
| `GET` | `/api/v1/users/{id}` | Obtener usuario por ID | - |

### Entries (Requiere JWT)

| Metodo | Ruta | Descripcion | Body / Params |
|--------|------|-------------|---------------|
| `GET` | `/api/v1/entries?month=2024-05` | Entradas del mes | Query param `month` (YYYY-MM) |
| `GET` | `/api/v1/entries/summary?month=2024-05&range=monthly` | Resumen financiero | Query params `month`, `range` (monthly\|annual) |
| `POST` | `/api/v1/entries` | Crear entrada | `{ "month", "category", "label", "amount" }` |
| `DELETE` | `/api/v1/entries` | Eliminar entrada | `{ "id" }` |

**Response GET /entries:**
```json
{
  "entries": [
    {
      "id": "uuid",
      "month": "2024-05",
      "category": "income",
      "label": "Salario",
      "amount": 5000.00
    }
  ]
}
```

**Response GET /entries/summary:**
```json
{
  "totals": { "income": 5000.00, "expense": 2000.00, "savings": 1500.00 },
  "breakdown": {
    "income": [{ "label": "Salario", "amount": 5000.00 }],
    "expense": [{ "label": "Arriendo", "amount": 1200.00 }],
    "savings": [{ "label": "Fondo de emergencia", "amount": 1500.00 }]
  },
  "rate": 30,
  "grandTotal": 8500.00
}
```

### Rutas Protegidas

Las rutas `/api/v1/auth/**` y `/api/v1/users/**` son publicas. Las rutas `/api/v1/entries/**` requieren autenticacion JWT. Cualquier otra ruta requiere un header `Authorization: Bearer <access-token>` valido.

## Configuracion

### Variables de Entorno

Copiar `.env.example` a `.env` y completar los valores:

```env
SPRING_DATA_MONGODB_URI=mongodb+srv://<username>:<password>@<cluster>.mongodb.net/?appName=<appName>
SPRING_DATA_MONGODB_DATABASE=<databaseName>
AUTH_JWT_SECRET=<base64-encoded-secret-min-32-bytes>
```

### Propiedades JWT

| Propiedad | Valor por defecto | Descripcion |
|-----------|-------------------|-------------|
| `auth.jwt.secret` | `${AUTH_JWT_SECRET}` | Clave secreta para firmar JWT (base64, min 32 bytes) |
| `auth.jwt.access-expiration-ms` | `900000` (15 min) | Duracion del access token |
| `auth.jwt.refresh-expiration-ms` | `604800000` (7 dias) | Duracion del refresh token |

### Ejecutar

```bash
# Compilar
./mvnw compile

# Ejecutar
./mvnw spring-boot:run

# Empaquetar
./mvnw clean package
```

## Estructura de un Caso de Uso

Los casos de uso son clases puras (sin anotaciones Spring) que se instancian via `@Bean` en la configuracion compartida:

```java
@RequiredArgsConstructor
public class LoginUseCase {

    private final AuthRepository authRepository;
    private final PasswordService passwordService;
    private final JwtService jwtService;

    public AuthTokens execute(String email, String rawPassword) {
        // logica de negocio
    }
}
```

```java
@Configuration
public class AuthBeanConfig {
    @Bean
    public LoginUseCase loginUseCase(AuthRepository authRepository,
                                     PasswordService passwordService,
                                     JwtService jwtService) {
        return new LoginUseCase(authRepository, passwordService, jwtService);
    }
}
```
