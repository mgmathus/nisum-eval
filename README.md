# Nisum Demo
el siguiente proyecto es una API con seguridad JWT

El proyecto necesita para poder ejecutarse Java 8 y Maven 3.6.3 o superior

#Pasos previos: 
En la raiz del proyecto ejecutar el siguiente comando para compilar proyecto

```
mvn install
```

Una vez completado el comando anterior se debe ejecutar

```
java -jar .\target\demo-0.0.1-SNAPSHOT.jar
```

La base de datos utilizada es H2 en memoria, en la siguiente ruta se encuentra el script inicial
```
demo\src\main\resources\db\migration\V1__create_seed_data.sql
```

Este script crea las tablas necesarias e inserta el usuario admin para poder realizar login y poder usar el servicio,
las contrasenas de cada usuario registrado se almacenan encriptadas utilizando bcrypt

las credenciales iniciales del usuario admin son:

```
{
    "email":"admin@dominio.cl",
    "password":"12345678"
}
```

El servidor podra ser accesible desde **`127.0.0.1:8081`**

Debe importar la collection en postman utilizando el siguiente recurso:

```
demo\src\main\resources\NISUM.postman_collection.json
```
