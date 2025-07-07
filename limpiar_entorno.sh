#!/bin/bash

echo "Limpiando entorno local del proyecto Android..."
rm -rf .idea .gradle .kotlin local.properties app/build build

echo "Entorno local limpio. Abre Android Studio y sincroniza nuevamente."
