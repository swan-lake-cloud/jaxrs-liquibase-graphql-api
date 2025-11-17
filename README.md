## Creer un reseau, lancer postgresql et lancer pgAdmin avec Podman
```shell
podman network create postgres-network
podman run --name postgres-dev -e POSTGRES_PASSWORD=password -e POSTGRES_DB=testdb -p 5432:5432 --network postgres-network -d postgres
podman run --name pgadmin -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin -p 8081:80 --network postgres-network -v pgadmin-data:/var/lib/pgadmin -d dpage/pgadmin4
```

## Configurer pgAdmin a la main pour se brancher sur la base postgres-dev
Clic droit on Servers > Register
Name : postgres-dev
Host : postgres-dev
Port : 5432
Maintenance database : testdb
Username : postgres
Password : password
Click Save password