# RelojVisual

**RelojVisual** es una aplicación Android de relojes visuales personalizables, diseñada para transformar un teléfono o tablet en un reloj de escritorio, reloj visual o estación de hora para uso personal.

La app permite elegir entre diferentes estilos de reloj, modificar su apariencia, seleccionar zona horaria y conservar las preferencias del usuario automáticamente.

---

## Descripción general

RelojVisual es una app desarrollada con **Kotlin**, **Jetpack Compose**, **Material 3** y **DataStore Preferences**.

Su objetivo principal es mostrar la hora de una forma visual, estética y personalizable. No se limita a un reloj digital tradicional, sino que ofrece diferentes estilos como relojes digitales, analógicos, retro, tipo smartwatch, deportivos y mecánicos decorativos.

La aplicación puede utilizarse en celular o tablet, especialmente como reloj de escritorio gracias a sus opciones de pantalla completa, mantener pantalla encendida y ocultar controles.

---

## Objetivo del proyecto

El objetivo del proyecto es desarrollar una aplicación Android que permita visualizar la hora en diferentes estilos de reloj, ofreciendo una experiencia personalizable y adaptable al gusto del usuario.

La app está pensada para:

- Usar un celular o tablet como reloj visual.
- Cambiar entre diferentes tipos de relojes.
- Personalizar colores, fecha, segundos y formato de hora.
- Elegir entre zona horaria del dispositivo o zona horaria manual.
- Usar la app en modo escritorio o pantalla completa.
- Guardar automáticamente las preferencias del usuario.

---

## Características principales

- Galería de relojes visuales.
- Relojes digitales, analógicos y decorativos.
- Personalización básica de apariencia.
- Presets de color.
- Formato de hora de 12 o 24 horas.
- Opción para mostrar u ocultar segundos.
- Opción para mostrar u ocultar fecha.
- Opciones para relojes analógicos.
- Selección de zona horaria.
- Uso de zona horaria del dispositivo.
- Uso de zona horaria manual.
- Modo escritorio para tablet o celular.
- Mantener pantalla encendida.
- Modo pantalla completa.
- Ocultar o mostrar controles.
- Persistencia de configuración con DataStore.

---

## Tecnologías utilizadas

- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **DataStore Preferences**
- **ViewModel**
- **StateFlow**
- **Java Time API**
- **Canvas de Jetpack Compose**
- **Android Studio**

---

## Arquitectura general

El proyecto utiliza una arquitectura sencilla basada en separación de responsabilidades.

Flujo general:

```text
TimeProvider
    ↓
ClockViewModel
    ↓
ClockScreen
    ↓
ClockFace seleccionado
```

La estructura principal se divide en:

```text
com.example.reloj/
├── data/
│   └── ClockSettingsRepository.kt
│
├── domain/
│   └── TimeProvider.kt
│
└── ui/
    ├── components/
    │   ├── DigitalMinimalClockFace.kt
    │   ├── DigitalNeonClockFace.kt
    │   ├── AnalogClassicClockFace.kt
    │   ├── AnalogRomanClockFace.kt
    │   ├── FlipClockFace.kt
    │   ├── SmartWatchClockFace.kt
    │   ├── DiveWatchClockFace.kt
    │   └── MechanicalClockFace.kt
    │
    ├── theme/
    ├── ClockScreen.kt
    ├── ClockGalleryScreen.kt
    ├── ClockSettingsScreen.kt
    ├── ClockViewModel.kt
    └── ClockFaceType.kt
```

---

## Tipos de relojes disponibles

La aplicación incluye los siguientes estilos de reloj:

### Digital minimalista

Reloj digital limpio y sencillo, enfocado en mostrar la hora de forma clara.

### Digital neón

Reloj digital con estilo visual brillante, inspirado en colores neón.

### Analógico clásico

Reloj de manecillas tradicional con marcas de hora, minutos y segundero.

### Analógico romano

Reloj analógico elegante con números romanos.

### Flip clock retro

Reloj inspirado en los relojes antiguos de paneles o tarjetas mecánicas.

### Smart watch

Reloj con apariencia moderna tipo reloj inteligente o panel visual.

### Reloj de buceo

Reloj analógico deportivo con bisel, marcas grandes y estilo robusto.

### Mecánico decorativo

Reloj analógico con estética mecánica y engranes decorativos.

---

## Personalización

Desde la pantalla de ajustes, el usuario puede modificar diferentes aspectos visuales de la app.

Opciones generales:

- Mostrar u ocultar segundos.
- Mostrar u ocultar fecha.
- Cambiar entre formato 12 horas y 24 horas.
- Seleccionar preset de color.

Opciones para relojes analógicos:

- Mostrar u ocultar números.
- Mostrar u ocultar marcas de minutos.
- Mostrar u ocultar segundero.

Presets de color disponibles:

- Default.
- AMOLED.
- Neón.
- Cálido.
- Azul.

---

## Zona horaria

La app permite usar dos modos de zona horaria:

### Zona horaria del dispositivo

La aplicación toma la zona horaria configurada en el celular o tablet.

### Zona horaria manual

El usuario puede desactivar la zona horaria del dispositivo y elegir una zona horaria de una lista disponible.

Ejemplos de zonas horarias:

- America/Mexico_City
- America/New_York
- America/Los_Angeles
- America/Bogota
- America/Argentina/Buenos_Aires
- Europe/Madrid
- Europe/London
- Asia/Tokyo
- Asia/Seoul

---

## Modo escritorio / tablet

RelojVisual incluye funciones pensadas para usar el dispositivo como reloj de escritorio.

Opciones disponibles:

- Mantener pantalla encendida.
- Activar modo pantalla completa.
- Mostrar u ocultar controles.
- Tocar la pantalla para mostrar u ocultar botones.

Este modo permite dejar el dispositivo en una base o soporte y utilizarlo como reloj visual permanente.

---

## Persistencia de datos

La app utiliza **DataStore Preferences** para guardar automáticamente las preferencias del usuario.

Se guardan datos como:

- Reloj seleccionado.
- Mostrar u ocultar segundos.
- Mostrar u ocultar fecha.
- Formato 12/24 horas.
- Preset de color.
- Opciones de relojes analógicos.
- Zona horaria.
- Modo escritorio.
- Pantalla completa.
- Mostrar u ocultar controles.

Esto permite que al cerrar y abrir la aplicación, la configuración se conserve.

---

## Cómo ejecutar el proyecto

1. Clonar el repositorio.
2. Abrir el proyecto en Android Studio.
3. Esperar a que Gradle sincronice el proyecto.
4. Conectar un dispositivo Android o iniciar un emulador.
5. Ejecutar la app desde Android Studio.

Requisitos recomendados:

- Android Studio reciente.
- Kotlin configurado.
- SDK de Android instalado.
- Dispositivo o emulador con Android 8.0/API 26 o superior.

---

## Estado actual del proyecto

El proyecto se encuentra en versión **MVP 1.0**.

Funciones disponibles:

- Galería de relojes.
- Ocho estilos visuales de reloj.
- Personalización básica.
- Persistencia con DataStore.
- Zona horaria del dispositivo o manual.
- Modo escritorio/tablet.
- Interfaz desarrollada con Jetpack Compose y Material 3.

---

## Próximas mejoras

Posibles mejoras futuras:

- Agregar alarmas.
- Agregar animaciones al Flip Clock.
- Mejorar personalización avanzada por cada reloj.
- Agregar más presets visuales.
- Crear temas personalizados.
- Agregar sonidos opcionales.
- Crear versión release firmada.
- Mejorar icono y branding de la app.
- Preparar publicación para distribución externa.

---

## Capturas de pantalla

> Las capturas se agregarán conforme avance la documentación visual del proyecto.

- [Captura pendiente: pantalla principal]
- [Captura pendiente: galería de relojes]
- [Captura pendiente: pantalla de ajustes]
- [Captura pendiente: reloj digital]
- [Captura pendiente: reloj analógico]
- [Captura pendiente: modo escritorio]

---

## Créditos

Desarrollado por: **Santiesteban**

Proyecto creado como práctica de desarrollo Android con Kotlin, Jetpack Compose, Material 3 y DataStore.

---

## Versión

**MVP 1.0**

---

## Licencia

Licencia pendiente de definir.
