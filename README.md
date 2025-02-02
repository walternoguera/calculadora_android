# Calculadora básica en Kotlin (Android)

## Demostración en video sobre el uso de la App

[![Ver en YouTube](https://img.youtube.com/vi/_7qGH2y70C0/0.jpg)](https://youtu.be/_7qGH2y70C0)


## Descripción
Calculadora científica desarrollada en **Kotlin** para Android, con soporte para operaciones básicas y funciones trigonométricas. Usa `Exp4j` para evaluar expresiones matemáticas y admite **modo científico en landscape**.

## Características principales
- ✅ **Operaciones básicas**: suma, resta, multiplicación, división y porcentaje.
- ✅ **Funciones científicas**: `sin()`, `cos()`, `tan()`, `sqrt()`.
- ✅ **Conversión de grados a radianes**.
- ✅ **Modo landscape** para funciones avanzadas.
- ✅ **Cambio de signo (+/-) del último número ingresado**.
- ✅ **Guardado del estado tras rotación de pantalla**.

## Tecnologías utilizadas
- **Kotlin** – Lenguaje de programación.
- **Android SDK** – Desarrollo de interfaz.
- **Exp4j** – Evaluación de expresiones matemáticas.
- **Regex** – Manejo de funciones trigonométricas.

## Instalación y ejecución
```bash
git clone https://github.com/tuusuario/calculadora-kotlin.git
cd calculadora-kotlin
```
Abre el proyecto en **Android Studio**, sincroniza `Gradle` y ejecuta en un emulador o dispositivo.

## Estructura del Proyecto
```bash
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/calc/MainActivity.kt
│   │   │   ├── res/layout/activity_main.xml
│   ├── build.gradle
├── README.md
├── .gitignore
```

## Uso de `Exp4j`
Para evaluar expresiones matemáticas:
```kotlin
val resultado = ExpressionBuilder(operacionConRadianes).build().evaluate()
```
Si el resultado es un entero, se muestra sin decimales:
```kotlin
val resultadoFinal = if (resultado % 1 == 0.0) resultado.toInt().toString() else resultado.toString()
```

### Conversión de funciones trigonométricas a radianes
```kotlin
operacion = operacion
    .replace(Regex("sin\\(([^)]+)\\)")) { "sin(${Math.toRadians(it.groupValues[1].toDouble())})" }
    .replace(Regex("cos\\(([^)]+)\\)")) { "cos(${Math.toRadians(it.groupValues[1].toDouble())})" }
    .replace(Regex("tan\\(([^)]+)\\)")) { "tan(${Math.toRadians(it.groupValues[1].toDouble())})" }
```

## Autor
**Walter Morel Noguera** - [GitHub](https://github.com/walternoguera) | [Mi sitio Web](https://www.walternoguera.com)

