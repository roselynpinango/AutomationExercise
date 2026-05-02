# AutomationExercise — Proyecto Integrador de Selenium

Proyecto de automatización de pruebas sobre [automationexercise.com](https://automationexercise.com) usando Selenium, TestNG y Java.

---

## Requisitos previos

| Herramienta | Versión mínima |
|---|---|
| Java JDK | 22 |
| Maven | 3.8+ |
| Google Chrome | última versión |
| (Opcional) VSCode + Extension Pack for Java | para ejecutar desde el IDE |

---

## Configurar la API Key de Gemini (solo para Laboratorio6_E3)

El `Laboratorio6_E3` usa la librería **AutoHeal Locator** que llama a la API de Google Gemini para sanar selectores rotos automáticamente. Los demás tests **no requieren** esta configuración.

### 1. Obtener la API Key (gratis)

1. Ingresá a [aistudio.google.com/app/apikey](https://aistudio.google.com/app/apikey)
2. Iniciá sesión con tu cuenta de Google
3. Hacé clic en **"Create API key"**
4. Copiá la key generada

### 2. Configurar la variable de entorno

**Windows (recomendado — persiste entre sesiones):**

```powershell
[System.Environment]::SetEnvironmentVariable("GEMINI_API_KEY", "tu-api-key-aqui", "User")
```

Luego cerrá y volvé a abrir VSCode/terminal para que tome efecto.

**Windows (solo sesión actual):**

```powershell
$env:GEMINI_API_KEY = "tu-api-key-aqui"
```

**Linux / macOS:**

```bash
export GEMINI_API_KEY="tu-api-key-aqui"
```

Para que persista, agregalo a `~/.bashrc` o `~/.zshrc`.

### 3. Crear los archivos de configuración de AutoHeal

Estos archivos están en `.gitignore` porque contienen la API key. Debés crearlos manualmente.

**`edit/src/test/resources/autoheal.properties`**

```properties
autoheal.ai.provider=GOOGLE_GEMINI
autoheal.ai.model=gemini-2.5-flash
autoheal.ai.api-key=${GEMINI_API_KEY}
autoheal.ai.api-url=https://generativelanguage.googleapis.com/v1beta/models
autoheal.ai.timeout=60s
autoheal.ai.max-retries=2
autoheal.ai.max-tokens-dom=4096
autoheal.ai.max-tokens-visual=4096
autoheal.ai.visual-analysis-enabled=false
autoheal.cache.type=PERSISTENT_FILE
autoheal.cache.maximum-size=100
autoheal.cache.expire-after-write=7d
```

**`edit/src/test/resources/autoheal-default.properties`**

```properties
autoheal.ai.provider=GOOGLE_GEMINI
autoheal.ai.model=gemini-2.5-flash
autoheal.ai.api-key=${GEMINI_API_KEY}
autoheal.ai.api-url=https://generativelanguage.googleapis.com/v1beta/models
autoheal.ai.timeout=60s
autoheal.ai.max-retries=2
autoheal.ai.max-tokens-dom=4096
autoheal.ai.max-tokens-visual=4096
autoheal.ai.visual-analysis-enabled=false
autoheal.cache.maximum-size=10000
autoheal.cache.expire-after-write=24h
autoheal.performance.element-timeout=60s
autoheal.performance.thread-pool-size=8
autoheal.performance.enable-metrics=true
```

> **Nota:** La sintaxis `${GEMINI_API_KEY}` hace que AutoHeal lea el valor de la variable de entorno automáticamente.

---

## Ejecutar los tests

### Desde VSCode (recomendado para tests individuales)

Abrí el archivo Java del test, hacé clic en el ícono ▶ que aparece al lado del método `@Test`, o usá el panel **Testing** de la barra lateral.

### Ejecutar un test individual con Maven

```bash
# Desde la carpeta edit/
mvn test -Dtest=NombreDeLaClase
```

Ejemplos:

```bash
mvn test -Dtest=Laboratorio1
mvn test -Dtest=Laboratorio6_E3
mvn test -Dtest=CheckoutTest
mvn test -Dtest=CartTest
```

### Ejecutar una suite completa por XML

```bash
# Desde la carpeta edit/
mvn test -DsuiteXmlFile=../testng.xml -Psuite
```

Suites disponibles (están en la raíz del proyecto):

| Archivo | Qué ejecuta |
|---|---|
| `testng.xml` | AppTest, Laboratorio1, Laboratorio2, TestRegistroUsuario |
| `testng_M4_E3.xml` | Solo el método `lab1_E2` de Laboratorio1 |
| `testng_M4_E4.xml` | CrossBrowserTest en Chrome y Firefox |

### Ejecutar todos los tests del proyecto

```bash
# Desde la carpeta edit/
mvn test
```

---

## Estructura del proyecto

```
edit/
├── src/
│   └── test/
│       ├── java/com/automationexercise/
│       │   ├── Laboratorio1.java          # Labs de navegación básica
│       │   ├── Laboratorio2.java
│       │   ├── Laboratorio6_E3.java       # AutoHeal con Gemini AI (requiere API key)
│       │   ├── M6_E1/CheckoutTest.java    # Test de compra con listeners
│       │   ├── M6_E2/CartTest.java        # Tests de carrito con ExtentReports
│       │   ├── M6_E2/PurchaseTest.java    # Test de compra con reporte HTML
│       │   ├── paginas/                   # Page Objects
│       │   ├── pruebas/                   # Otros labs (cross-browser, registro)
│       │   └── utils/ExcelReader.java     # Lectura de datos desde Excel
│       └── resources/
│           ├── autoheal.properties        # Config de AutoHeal (NO en git — crearlo manualmente)
│           └── autoheal-default.properties # Config base de AutoHeal (NO en git — crearlo manualmente)
├── pom.xml
└── testng*.xml                            # Suites de TestNG
```

---

## Notas importantes

- Los tests abren una ventana real de Chrome. No cierres el navegador mientras se ejecutan.
- `Laboratorio6_E3` registra un usuario nuevo en el sitio cada vez que se ejecuta (con email único por timestamp).
- Los reportes de ExtentReports (módulo 6) se generan en la carpeta `target/`.
- Si el test falla por un anuncio que bloquea la pantalla, volvé a ejecutarlo — los anuncios son aleatorios.
