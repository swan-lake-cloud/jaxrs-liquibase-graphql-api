podman stop postgres-dev
podman rm postgres-dev

podman run --name postgres-dev -e POSTGRES_PASSWORD=password -e POSTGRES_DB=testdb -p 5432:5432 -d postgres

podman exec -it postgres-dev psql -U postgres -c "\l"

Si la base n existe pas dans le conteneur :
podman exec -it postgres-dev psql -U postgres -c "CREATE DATABASE testdb;"

# Lance pgAdmin avec Podman
podman network create postgres-network
podman run --name postgres-dev -e POSTGRES_PASSWORD=password -e POSTGRES_DB=testdb -p 5432:5432 --network postgres-network -d postgres
podman stop pgadmin
podman rm pgadmin
podman run --name pgadmin -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin -p 8081:80 --network postgres-network -v pgadmin-data:/var/lib/pgadmin -d dpage/pgadmin4
Clic droit on Servers > Register
Name : postgres-dev
Host : postgres-dev
Port : 5432
Maintenance database : testdb
Username : postgres
Password : password
Click Save password